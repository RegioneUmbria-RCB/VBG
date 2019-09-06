using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Exceptions.Protocollo;
using Init.SIGePro.Protocollo.ProxyDocsPa;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System.IO;
using System.Text.RegularExpressions;
using log4net;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    class PROTOCOLLO_DOCSPA : ProtocolloBase
    {

        #region Costruttori
        public PROTOCOLLO_DOCSPA()
        {
            pProxyProtDocsPa = new DocsPaWSLite();
        }
        #endregion

        #region Membri privati
        private DocsPaWSLite pProxyProtDocsPa = null;
        private string _Url = string.Empty;
        private string _Operatore = string.Empty;
        private string _Password = string.Empty;
        private string _CodFascicolo = string.Empty;
        private bool _TrasmissioneInterna = false;
        ILog _log = LogManager.GetLogger(typeof(PROTOCOLLO_DOCSPA));
        #endregion

        #region Metodi pubblici e privati della classe

        #region Metodi per la protocollazione
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            DatiProtocolloRes protoRes = null;
            try
            {
                GetParametriFromVertDocsPa();
                pProxyProtDocsPa.Url = _Url;

                _protocolloLogs.Debug("#### Chiamata al metodo di Protocollazione ####");

                int numeroProtocollo = 0;
                int annoProtocollo = 0;
                string segnatura = string.Empty;
                string dataProtocollo = string.Empty;
                string errorMessage = string.Empty;

                //Verifico il flusso di protocollazione perchè questo sistema non supporta protocollazione interne
                if (protoIn.Flusso == "I")
                    throw new Exception("IL SISTEMA DI PROTOCOLLAZIONE NON SUPPORTA IL FLUSSO INTERNO!");

                bool esitoProtocollo = ProtocollaEFascicola(protoIn, out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo, out errorMessage);

                if (esitoProtocollo)
                {
                    //Setto gli allegati
                    SetAllegati(protoIn, segnatura);

                    //Per assegnare il protocollo ad un ufficio nel caso di flusso in "Arrivo" occorre fare queste chiamate
                    if (_TrasmissioneInterna)
                    {
                        bool esitoTrasmissione = TrasmissioneInterna(protoIn.Destinatari.Amministrazione[0].PROT_RUOLO, segnatura);

                        if (!esitoTrasmissione)
                            throw new Exception("ERRORE GENERATO DAL WEB METHOD EXECUTETRASM.");
                    }

                    _protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    protoRes = CreaDatiProtocollo(numeroProtocollo, annoProtocollo, segnatura, dataProtocollo);
                }
                else
                    throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD PROTOCOLLAZIONEESTESACONCLASS, ERRORE: {0}", errorMessage));
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE AVVENUTO IN FASE DI PROTOCOLLAZIONE", ex);
            }

            return protoRes;
        }

        private bool TrasmissioneInterna(string modelloTrasmissione, string segnatura)
        {
            try
            {
                SchedaDocumento schedaDoc = null;
                try
                {
                    _protocolloLogs.InfoFormat("Chiamata a web method ricercaSchedabySegnatura, segnatura: {0}, userid: {1}, password: {2}", segnatura, _Operatore, _Password);
                    schedaDoc = pProxyProtDocsPa.ricercaSchedabySegnatura(segnatura, _Operatore, _Password);
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE AVVENUTO DURANTE LA RICERCA DELLA SCHEDA DA UNA SEGNATURA, web method ricercaSchedabySegnatura", ex);
                }

                _protocolloSerializer.Serialize(ProtocolloLogsConstants.SchedaDocSoapResponseFileName, schedaDoc);

                Registro[] aRegistri = new Registro[1];
                aRegistri[0] = schedaDoc.registro;

                _protocolloSerializer.Serialize(ProtocolloLogsConstants.RegistroSoapResponseFileName, schedaDoc.registro);

                object[] listModelli = null;

                try
                {
                    _protocolloLogs.InfoFormat("Chiamata a web method getModelliPerTrasm, userid: {0}, password: {1}, registro file: {2}, tipo oggetto: {3}", _Operatore, _Password, ProtocolloLogsConstants.RegistroSoapResponseFileName, "D");
                     listModelli = pProxyProtDocsPa.getModelliPerTrasm(_Operatore, _Password, aRegistri, string.Empty, string.Empty, string.Empty, "D");
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE AVVENUTO DURANTE IL RECUPERO DEI MODELLI, web method getModelliPerTrasm", ex);
                }

                ModelloTrasmissione modelloTrasm = null;
                foreach (object elem in listModelli)
                {
                    if (((ModelloTrasmissione)elem).CODICE == modelloTrasmissione)
                    {
                        modelloTrasm = (ModelloTrasmissione)elem;
                        break;
                    }
                }

                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ModelloTrasmissioneSoapResponseFileName, modelloTrasm);

                _protocolloLogs.InfoFormat("Chiamata a executeTrasm, scheda file: {0}, modello file: {1}, userid: {2}, password: {3}", ProtocolloLogsConstants.SchedaDocSoapResponseFileName, ProtocolloLogsConstants.ModelloTrasmissioneSoapResponseFileName, _Operatore, _Password);
                var response = pProxyProtDocsPa.executeTrasm(string.Empty, schedaDoc, modelloTrasm, _Operatore, _Password);

                _protocolloLogs.InfoFormat("Chiamata a executeTrasm avvenuta correttamente, risposta: {0}", response);
                
                return response;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA TRASMISSIONE INTERNA", ex);
            }
        }

        private bool ProtocollaEFascicola(Data.DatiProtocolloIn pProt, out int numeroProtocollo, out int annoProtocollo, out string segnatura, out string dataProtocollo, out string errorMessage)
        {
            try
            {
                annoProtocollo = 0;
                numeroProtocollo = 0;
                dataProtocollo = string.Empty;
                segnatura = string.Empty;
                errorMessage = string.Empty;
                string note = string.Empty;

                //Setto il campo note con l'informazione sul tipo documento (non c'è un campo specifico)
                ProtocolloTipiDocumentoMgr protTipiDOcMgr = new ProtocolloTipiDocumentoMgr(DatiProtocollo.Db);
                note = "Tipo documento: " + protTipiDOcMgr.GetById(DatiProtocollo.IdComune, pProt.TipoDocumento, DatiProtocollo.Software, DatiProtocollo.CodiceComune).Descrizione;

                //Setto mittenti e destinatari
                List<CorrLite> corrispondenti = new List<CorrLite>();

                //Setto i mittenti
                SetMittenti(corrispondenti, pProt);

                //Setto i destinatari
                SetDestinatari(corrispondenti, pProt);

                bool returnValue;
                //Lo stesso metodo (protocollazioneEstesaConClass) può essere utilizzato sia per la classificazione che per la fascicolazione 
                //Questa soluzione è adottata quando protocollazioneEstesaConClass viene utilizzato per la fascicolazione
                //(da intendere con fascicolo faldone). Nella versione lite mancano i ws per la fascicolazione (creazione fascicolo)
                if (!string.IsNullOrEmpty(_CodFascicolo) && !GestisciFascicolazione)
                {
                    _protocolloLogs.InfoFormat("Chiamata a web method protocollazioneEstesaConClass, operatore: {0}, password: {1}, oggetto: {2}, note: {3}, flusso: {4}, corrispondenti: {5}, codice fascicolo: {6}", _Operatore, _Password, pProt.Oggetto, note, pProt.Flusso, String.Join(", ", corrispondenti), _CodFascicolo);
                    returnValue = pProxyProtDocsPa.protocollazioneEstesaConClass(_Operatore, _Password, pProt.Oggetto, note, pProt.Flusso, corrispondenti.ToArray(), _CodFascicolo, out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo, out errorMessage);
                }
                else
                {
                    _protocolloLogs.InfoFormat("Chiamata a web method protocollazioneEstesa, operatore: {0}, password: {1}, oggetto: {2}, note: {3}, flusso: {4}, corrispondenti: {5}", _Operatore, _Password, pProt.Oggetto, note, pProt.Flusso, String.Join(", ", corrispondenti));
                    returnValue = pProxyProtDocsPa.protocollazioneEstesa(_Operatore, _Password, pProt.Oggetto, note, pProt.Flusso, corrispondenti.ToArray(), out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo);
                }
                //Questa soluzione è adottata quando protocollazioneEstesaConClass viene utilizzato per la classificazione
                //return pProxyProtDocsPa.protocollazioneEstesaConClass(_Operatore, _Password, pProt.Oggetto, note, pProt.Flusso, corrispondenti.ToArray(), pProt.Classifica, out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo, out errorMessage);

                _protocolloLogs.InfoFormat("Protocollazione avvenuta con successo, valore di ritorno: {0}", returnValue);

                return returnValue;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA PROTOCOLLAZIONE / FASCICOLAZIONE", ex);
            }
        }

        private void SetDestinatari(List<CorrLite> corrispondenti, DatiProtocolloIn pProt)
        {
            try
            {
                //Verifico le amministrazioni (interne ed esterne)
                if (pProt.Destinatari.Amministrazione.Count >= 1)
                {
                    if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].PROT_UO))
                    {
                        _TrasmissioneInterna = true;
                        //CorrLite corr = new CorrLite();
                        //corr.tipoCorrispondente = "D";
                        //corr.codice = pProt.Destinatari.Amministrazione[0].PROT_UO;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].AMMINISTRAZIONE))
                        //    corr.descrizione = pProt.Destinatari.Amministrazione[0].AMMINISTRAZIONE;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].CAP))
                        //    corr.cap = pProt.Destinatari.Amministrazione[0].CAP;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].CITTA))
                        //    corr.citta = pProt.Destinatari.Amministrazione[0].CITTA;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].PARTITAIVA))
                        //    corr.codiceFiscale = pProt.Destinatari.Amministrazione[0].PARTITAIVA;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].FAX))
                        //    corr.fax = pProt.Destinatari.Amministrazione[0].FAX;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].INDIRIZZO))
                        //    corr.indirizzo = pProt.Destinatari.Amministrazione[0].INDIRIZZO;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].TELEFONO1))
                        //    corr.telefono = pProt.Destinatari.Amministrazione[0].TELEFONO1;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].TELEFONO2))
                        //    corr.telefono2 = pProt.Destinatari.Amministrazione[0].TELEFONO2;
                        //if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].PROVINCIA))
                        //    corr.provincia = pProt.Destinatari.Amministrazione[0].PROVINCIA;

                        //corrispondenti.Add(corr);
                    }
                    else
                    {
                        //Ciclo per le amministrazioni esterne
                        foreach (Amministrazioni pAmministrazione in pProt.Destinatari.Amministrazione)
                        {
                            CorrLite corr = new CorrLite();
                            corr.tipoCorrispondente = "D";
                            //corr.codice = "SIG_" + pAmministrazione.CODICEAMMINISTRAZIONE;
                            if (!string.IsNullOrEmpty(pAmministrazione.AMMINISTRAZIONE))
                                corr.descrizione = pAmministrazione.AMMINISTRAZIONE;
                            if (!string.IsNullOrEmpty(pAmministrazione.CAP))
                                corr.cap = pAmministrazione.CAP;
                            if (!string.IsNullOrEmpty(pAmministrazione.CITTA))
                                corr.citta = pAmministrazione.CITTA;
                            if (!string.IsNullOrEmpty(pAmministrazione.PARTITAIVA))
                                corr.codiceFiscale = pAmministrazione.PARTITAIVA;
                            if (!string.IsNullOrEmpty(pAmministrazione.FAX))
                                corr.fax = pAmministrazione.FAX;
                            if (!string.IsNullOrEmpty(pAmministrazione.INDIRIZZO))
                                corr.indirizzo = pAmministrazione.INDIRIZZO;
                            if (!string.IsNullOrEmpty(pAmministrazione.TELEFONO1))
                                corr.telefono = pAmministrazione.TELEFONO1;
                            if (!string.IsNullOrEmpty(pAmministrazione.TELEFONO2))
                                corr.telefono2 = pAmministrazione.TELEFONO2;
                            if (!string.IsNullOrEmpty(pAmministrazione.PROVINCIA))
                                corr.provincia = pAmministrazione.PROVINCIA;

                            corrispondenti.Add(corr);
                        }
                    }

                    //Perchè nel caso che il destinatario sia un'amministrazione esterna ed una anagrafica devo prendere solo la prima
                    //Commento per gestire più di 1 destinatario
                    //return;
                }

                //Commento per gestire più di 1 destinatario
                //Verifico le anagrafiche
                if (pProt.Destinatari.Anagrafe.Count >= 1)
                {
                    //Ciclo per le amministrazioni esterne
                    foreach (Anagrafe pAnagrafe in pProt.Destinatari.Anagrafe)
                    {
                        CorrLite corr = new CorrLite();
                        corr.tipoCorrispondente = "D";
                        //corr.codice = "SIG_" + pAnagrafe.CODICEANAGRAFE;
                        if (!(string.IsNullOrEmpty(pAnagrafe.NOMINATIVO) && string.IsNullOrEmpty(pAnagrafe.NOME)))
                            corr.descrizione = (pAnagrafe.NOMINATIVO + " " + pAnagrafe.NOME).TrimEnd();
                        if (!string.IsNullOrEmpty(pAnagrafe.CAP))
                            corr.cap = pAnagrafe.CAP;
                        if (!string.IsNullOrEmpty(pAnagrafe.CITTA))
                            corr.citta = pAnagrafe.CITTA;
                        if (!string.IsNullOrEmpty(pAnagrafe.CODICEFISCALE))
                            corr.codiceFiscale = pAnagrafe.CODICEFISCALE;
                        if (!string.IsNullOrEmpty(pAnagrafe.FAX))
                            corr.fax = pAnagrafe.FAX;
                        if (!string.IsNullOrEmpty(pAnagrafe.INDIRIZZO))
                            corr.indirizzo = pAnagrafe.INDIRIZZO;
                        if (!string.IsNullOrEmpty(pAnagrafe.TELEFONO))
                            corr.telefono = pAnagrafe.TELEFONO;
                        if (!string.IsNullOrEmpty(pAnagrafe.TELEFONOCELLULARE))
                            corr.telefono2 = pAnagrafe.TELEFONOCELLULARE;
                        if (!string.IsNullOrEmpty(pAnagrafe.PROVINCIA))
                            corr.provincia = pAnagrafe.PROVINCIA;

                        corrispondenti.Add(corr);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI", ex);
            }
        }
        private void SetMittenti(List<CorrLite> corrispondenti, DatiProtocolloIn pProt)
        {
            try
            {
                //Verifico le amministrazioni (interne ed esterne)
                if (pProt.Mittenti.Amministrazione.Count >= 1)
                {
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PROT_UO))
                    {
                        CorrLite corr = new CorrLite();
                        corr.tipoCorrispondente = "M";
                        string[] sProtUo = pProt.Mittenti.Amministrazione[0].PROT_UO.Split(new Char[] { '/' });
                        corr.codice = sProtUo[0];
                        corr.descrizione = sProtUo[1];
                        //if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE))
                        //    corr.descrizione = pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].CAP))
                            corr.cap = pProt.Mittenti.Amministrazione[0].CAP;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].CITTA))
                            corr.citta = pProt.Mittenti.Amministrazione[0].CITTA;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PARTITAIVA))
                            corr.codiceFiscale = pProt.Mittenti.Amministrazione[0].PARTITAIVA;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].FAX))
                            corr.fax = pProt.Mittenti.Amministrazione[0].FAX;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].INDIRIZZO))
                            corr.indirizzo = pProt.Mittenti.Amministrazione[0].INDIRIZZO;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].TELEFONO1))
                            corr.telefono = pProt.Mittenti.Amministrazione[0].TELEFONO1;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].TELEFONO2))
                            corr.telefono2 = pProt.Mittenti.Amministrazione[0].TELEFONO2;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PROVINCIA))
                            corr.provincia = pProt.Mittenti.Amministrazione[0].PROVINCIA;

                        corrispondenti.Add(corr);
                    }
                    else
                    {
                        CorrLite corr = new CorrLite();
                        corr.tipoCorrispondente = "M";
                        //corr.codice = "SIG_" + pProt.Mittenti.Amministrazione[0].CODICEAMMINISTRAZIONE;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE))
                            corr.descrizione = pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].CAP))
                            corr.cap = pProt.Mittenti.Amministrazione[0].CAP;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].CITTA))
                            corr.citta = pProt.Mittenti.Amministrazione[0].CITTA;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PARTITAIVA))
                            corr.codiceFiscale = pProt.Mittenti.Amministrazione[0].PARTITAIVA;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].FAX))
                            corr.fax = pProt.Mittenti.Amministrazione[0].FAX;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].INDIRIZZO))
                            corr.indirizzo = pProt.Mittenti.Amministrazione[0].INDIRIZZO;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].TELEFONO1))
                            corr.telefono = pProt.Mittenti.Amministrazione[0].TELEFONO1;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].TELEFONO2))
                            corr.telefono2 = pProt.Mittenti.Amministrazione[0].TELEFONO2;
                        if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PROVINCIA))
                            corr.provincia = pProt.Mittenti.Amministrazione[0].PROVINCIA;

                        corrispondenti.Add(corr);
                    }

                    //Perchè nel caso che il mittente sia un'amministrazione esterna ed una anagrafica devo prendere solo la prima
                    return;
                }

                //Verifico le anagrafiche
                if (pProt.Mittenti.Anagrafe.Count >= 1)
                {
                    CorrLite corr = new CorrLite();
                    corr.tipoCorrispondente = "M";
                    //corr.codice = "SIG_" + pProt.Mittenti.Anagrafe[0].CODICEANAGRAFE;
                    if (!(string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].NOMINATIVO) && string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].NOME)))
                        corr.descrizione = (pProt.Mittenti.Anagrafe[0].NOMINATIVO + " " + pProt.Mittenti.Anagrafe[0].NOME).TrimEnd();
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].CAP))
                        corr.cap = pProt.Mittenti.Anagrafe[0].CAP;
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].CITTA))
                        corr.citta = pProt.Mittenti.Anagrafe[0].CITTA;
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].CODICEFISCALE))
                        corr.codiceFiscale = pProt.Mittenti.Anagrafe[0].CODICEFISCALE;
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].FAX))
                        corr.fax = pProt.Mittenti.Anagrafe[0].FAX;
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].INDIRIZZO))
                        corr.indirizzo = pProt.Mittenti.Anagrafe[0].INDIRIZZO;
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].TELEFONO))
                        corr.telefono = pProt.Mittenti.Anagrafe[0].TELEFONO;
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].TELEFONOCELLULARE))
                        corr.telefono2 = pProt.Mittenti.Anagrafe[0].TELEFONOCELLULARE;
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].PROVINCIA))
                        corr.provincia = pProt.Mittenti.Anagrafe[0].PROVINCIA;

                    corrispondenti.Add(corr);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEI MITTENTI", ex);
            }
        }

        private void SetAllegati(DatiProtocolloIn pProt, string segnatura)
        {
            try
            {
                if (pProt.Allegati.Count >= 1)
                {
                    SchedaDocumento schedaDoc = pProxyProtDocsPa.ricercaSchedabySegnatura(segnatura, _Operatore, _Password);
                    if (pProt.Allegati.Count > 1)
                        schedaDoc.allegati = new Init.SIGePro.Protocollo.ProxyDocsPa.Allegato[pProt.Allegati.Count - 1];

                    for (int iCount = 0; iCount < pProt.Allegati.Count; iCount++)
                    {
                        byte[] bytes = pProt.Allegati[iCount].OGGETTO;
                        FileDocumento fileDocumento = new FileDocumento();

                        //non dovrebbe essere necessario
                        if (bytes != null)
                        {
                            // nuova versione 
                            int iPos = pProt.Allegati[iCount].Descrizione.LastIndexOf("." + pProt.Allegati[iCount].Extension);
                            //Usate per poter salvare il file
                            string pattern = @"[\\/:*?<>|]";
                            //string pattern=@"[\\/:*?<>|\" + "\"]"; questa istruzione può essere usata in alternativa alla precedente togliendo l'istruzione Replace successiva
                            if (iPos != -1)
                            {
                                pProt.Allegati[iCount].Descrizione = Regex.Replace(pProt.Allegati[iCount].Descrizione, pattern, "");
                                pProt.Allegati[iCount].Descrizione = pProt.Allegati[iCount].Descrizione.Replace("\"", "");
                                pProt.Allegati[iCount].Descrizione = pProt.Allegati[iCount].Descrizione.Replace("€", "Euro");
                                using (FileStream pFs = new FileStream(_protocolloLogs.Folder + pProt.Allegati[iCount].Descrizione, FileMode.Create))
                                {
                                    pFs.Write(bytes, 0, bytes.Length);

                                    fileDocumento.name = System.IO.Path.GetFileName(_protocolloLogs.Folder + pProt.Allegati[iCount].Descrizione);
                                    fileDocumento.fullName = fileDocumento.name;
                                    fileDocumento.contentType = pProt.Allegati[iCount].MimeType;
                                    fileDocumento.length = (int)pFs.Length;
                                    fileDocumento.estensioneFile = pProt.Allegati[iCount].Extension;
                                    fileDocumento.content = Init.Utils.StreamUtils.StreamToBytes(pFs);
                                }
                            }
                            else
                            {
                                pProt.Allegati[iCount].NOMEFILE = Regex.Replace(pProt.Allegati[iCount].NOMEFILE, pattern, "");
                                pProt.Allegati[iCount].NOMEFILE = pProt.Allegati[iCount].NOMEFILE.Replace("\"", "");
                                pProt.Allegati[iCount].NOMEFILE = pProt.Allegati[iCount].NOMEFILE.Replace("€", "Euro");
                                using (FileStream pFs = new FileStream(_protocolloLogs.Folder + pProt.Allegati[iCount].NOMEFILE, FileMode.Create))
                                {
                                    pFs.Write(bytes, 0, bytes.Length);

                                    fileDocumento.name = System.IO.Path.GetFileName(_protocolloLogs.Folder + pProt.Allegati[iCount].NOMEFILE);
                                    fileDocumento.fullName = fileDocumento.name;
                                    fileDocumento.contentType = pProt.Allegati[iCount].MimeType;
                                    fileDocumento.length = (int)pFs.Length;
                                    fileDocumento.estensioneFile = pProt.Allegati[iCount].Extension;
                                    fileDocumento.content = Init.Utils.StreamUtils.StreamToBytes(pFs);
                                }
                            }
                        }
                        else
                            throw new ProtocolloException("Errore generato dal web method Inserimento del protocollo DocsPa. Metodo: SetAllegati, modulo: ProtocolloDocsPa. C'è un allegato con il campo OGGETTO null.\r\n");

                        if (iCount == 0)
                        {
                            schedaDoc.documenti[iCount].descrizione = fileDocumento.name;
                            pProxyProtDocsPa.putfile(schedaDoc.documenti[iCount], fileDocumento, _Operatore, _Password);
                        }
                        else
                        {
                            schedaDoc.allegati[iCount - 1] = new Init.SIGePro.Protocollo.ProxyDocsPa.Allegato();
                            //Setto il numero di pagine nella chiamata ad 1(non ho questa informazione e se passo string.empty
                            //mi restituisce un'eccezione)
                            pProxyProtDocsPa.aggiungiAllegato(_Operatore, _Password, schedaDoc, "1", fileDocumento.name, fileDocumento, schedaDoc.allegati[iCount - 1]);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE IL SETTAGGIO DEGLI ALLEGATI", ex);
            }
        }

        private DatiProtocolloRes CreaDatiProtocollo(int numeroProtocollo, int annoProtocollo, string segnatura, string dataProtocollo)
        {
            try
            {
                var protoRes = new DatiProtocolloRes();
                protoRes.IdProtocollo = segnatura;
                protoRes.AnnoProtocollo = annoProtocollo.ToString();
                protoRes.NumeroProtocollo = numeroProtocollo.ToString();
                protoRes.DataProtocollo = dataProtocollo;

                _protocolloLogs.InfoFormat("Dati protocollo restituiti, id protocollo: {0}, numero: {1}, anno: {2}, data: {3}", protoRes.IdProtocollo, protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO", ex);
            }
        }

        #endregion

        #region Metodi per la fascicolazione di un protocollo

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                GetParametriFromVertDocsPa();
                pProxyProtDocsPa.Url = _Url;

                return Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA VERIFICA DELLA FASCICOLAZIONE", ex);
            }
        }

        private DatiProtocolloFascicolato Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloFascicolato datiProtFasc = new DatiProtocolloFascicolato();

            SchedaDocumento schedaDocumento = null;
            schedaDocumento = LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);

            if (schedaDocumento != null)
            {
                if (string.IsNullOrEmpty(schedaDocumento.fascicolato))
                {
                    datiProtFasc.Fascicolato = EnumFascicolato.no;
                }
                else
                {
                    datiProtFasc.Fascicolato = EnumFascicolato.si;
                }
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolato.warning;
                datiProtFasc.NoteFascicolo = "Errore durante la verifica della fascicolazione del protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;
            }

            return datiProtFasc;
        }

        #endregion

        #region Metodi per l'annullamento di un protocollo

        public override DatiProtocolloAnnullato IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                GetParametriFromVertDocsPa();
                pProxyProtDocsPa.Url = _Url;

                return Annullato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA VERIFICA DELL'ANNULLAMENTO DEL PROTOCOLLO", ex);
            }
        }

        private DatiProtocolloAnnullato Annullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloAnnullato datiProtAnn = new DatiProtocolloAnnullato();

            SchedaDocumento schedaDocumento = null;
            schedaDocumento = LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);


            if (schedaDocumento != null)
            {
                if (schedaDocumento.protocollo != null)
                {
                    if (schedaDocumento.protocollo.protocolloAnnullato != null)
                    {
                        datiProtAnn.Annullato = EnumAnnullato.si;
                        datiProtAnn.MotivoAnnullamento = string.IsNullOrEmpty(schedaDocumento.protocollo.protocolloAnnullato.autorizzazione) ? string.Empty : schedaDocumento.protocollo.protocolloAnnullato.autorizzazione;
                    }
                    else
                    {
                        datiProtAnn.Annullato = EnumAnnullato.no;
                    }
                }
                else
                {
                    datiProtAnn.Annullato = EnumAnnullato.warning;
                    datiProtAnn.NoteAnnullamento = "Errore durante la verifica della nullabilità del protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;
                }
            }
            else
            {
                datiProtAnn.Annullato = EnumAnnullato.warning;
                datiProtAnn.NoteAnnullamento = "Non è presente nessun protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;
            }

            return datiProtAnn;
        }

        public override void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            try
            {
                GetParametriFromVertDocsPa();
                pProxyProtDocsPa.Url = _Url;

                _protocolloLogs.InfoFormat("Chiamata a web method annullaProtocollo, Operatore: {0}, Password: {1}, id protocollo: {2}, motivo annullamento: {3}", _Operatore, _Password, idProtocollo, motivoAnnullamento);
                pProxyProtDocsPa.annullaProtocollo(_Operatore, _Password, idProtocollo, motivoAnnullamento);
                _protocolloLogs.Info("Il protocollo è stato annullato con successo");

            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE L'ANNULLAMENTO DI UN PROTOCOLLO", ex);
            }
        }

        #endregion

        #region Metodi per la lettura di un protocollo

        private SchedaDocumento LeggiProtocolloDocumento(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                SchedaDocumento docOut = null;

                if (!string.IsNullOrEmpty(idProtocollo) || (!string.IsNullOrEmpty(numeroProtocollo) && !string.IsNullOrEmpty(annoProtocollo)))
                {
                    if (string.IsNullOrEmpty(idProtocollo))
                    {
                        string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                        string sNumProtocollo = sNumProtSplit[0];

                        _protocolloLogs.InfoFormat("Chiamata a web method ricercaSchedabyChiaveProto, numero protocollo: {0}, anno protocollo: {1}, operatore: {2}, password: {3}", sNumProtocollo, annoProtocollo, _Operatore, _Password);

                        docOut = pProxyProtDocsPa.ricercaSchedabyChiaveProto(sNumProtocollo, annoProtocollo, string.Empty, string.Empty, _Operatore, _Password);

                        _protocolloSerializer.Serialize(ProtocolloLogsConstants.SchedaDocSoapResponseFileName, docOut);

                        
                    }
                    else
                    {
                        _protocolloLogs.InfoFormat("Chiamata a web method ricercaSchedabyChiaveProto, id protocollo: {0}, operatore: {1}, password: {2}", idProtocollo, _Operatore, _Password);
                        docOut = pProxyProtDocsPa.ricercaSchedabySegnatura(idProtocollo, _Operatore, _Password);

                        if (!string.IsNullOrEmpty(annoProtocollo) && !string.IsNullOrEmpty(numeroProtocollo))
                        {
                            string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                            if ((docOut != null) && (docOut.protocollo != null) && (docOut.protocollo.numero != sNumProtSplit[0]))
                                throw new ProtocolloException("Il numero del protocollo riletto non coincide con quello passato!" + "Numero riletto/anno riletto: " + docOut.protocollo.numero + "/" + docOut.protocollo.anno + ", numero passato/anno passato: " + sNumProtSplit[0] + "/" + annoProtocollo);

                            if ((docOut != null) && (docOut.protocollo != null) && (docOut.protocollo.anno != annoProtocollo))
                                throw new ProtocolloException("L'anno del protocollo riletto non coincide con quello passato!" + "Numero riletto/anno riletto: " + docOut.protocollo.numero + "/" + docOut.protocollo.anno + ", numero passato/anno passato: " + sNumProtSplit[0] + "/" + annoProtocollo);
                        }
                        
                    }
                }
                else
                    throw new Exception(String.Format("Non è possibile rileggere il protocollo/documento, parametri non corretti, id protocollo: {0}, numero protocollo: {1}, anno protocollo: {2}", idProtocollo, numeroProtocollo, annoProtocollo));

                return docOut;
            }
            catch (Exception)
            {
                throw;
            }
        }


        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            _protocolloLogs.Debug("Inizio funzionalità LeggiProtocollo");
            DatiProtocolloLetto pProtocolloLetto = null;

            try
            {
                GetParametriFromVertDocsPa();
                pProxyProtDocsPa.Url = _Url;
                NumProtocollo = numeroProtocollo;
                AnnoProtocollo = annoProtocollo;

                var schedaDoc = LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);

                if (schedaDoc != null)
                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, schedaDoc);

                pProtocolloLetto = CreaDatiProtocolloLetto(schedaDoc);
                
                return pProtocolloLetto;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO CON ID: {0}, NUMERO: {1}, ANNO: {2}", idProtocollo, numeroProtocollo, annoProtocollo), ex);
            }
        }

        private DatiProtocolloLetto CreaDatiProtocolloLetto(SchedaDocumento pSchedaDocumento)
        {
            DatiProtocolloLetto pProtocolloLetto = new DatiProtocolloLetto();

            if (pSchedaDocumento != null)
            {
                if (pSchedaDocumento.protocollo != null)
                {
                    if (!string.IsNullOrEmpty(pSchedaDocumento.protocollo.segnatura))
                    {
                        //pProtocolloLetto.IdProtocollo = pSchedaDocumento.protocollo.segnatura;
                        pProtocolloLetto.AnnoProtocollo = pSchedaDocumento.protocollo.anno;
                        pProtocolloLetto.NumeroProtocollo = pSchedaDocumento.protocollo.numero;
                        pProtocolloLetto.DataProtocollo = pSchedaDocumento.protocollo.dataProtocollazione;

                        if (!string.IsNullOrEmpty(pSchedaDocumento.oggetto.descrizione))
                            pProtocolloLetto.Oggetto = pSchedaDocumento.oggetto.descrizione;
                        if (!string.IsNullOrEmpty(pSchedaDocumento.tipoProto))
                            pProtocolloLetto.Origine = pSchedaDocumento.tipoProto;
                        //I servizi non hanno un metodo che restituisca in lettura informazioni sulla classifica
                        if (!string.IsNullOrEmpty(pSchedaDocumento.noteDocumento[0].Testo))
                            pProtocolloLetto.TipoDocumento = pSchedaDocumento.noteDocumento[0].Testo;
                        if (!string.IsNullOrEmpty(pSchedaDocumento.noteDocumento[0].Testo))
                            pProtocolloLetto.TipoDocumento_Descrizione = pSchedaDocumento.noteDocumento[0].Testo;

                        //Gestione nullabilità
                        if (pSchedaDocumento.protocollo.protocolloAnnullato != null)
                        {
                            pProtocolloLetto.Annullato = EnumAnnullato.si.ToString();
                            pProtocolloLetto.MotivoAnnullamento = string.IsNullOrEmpty(pSchedaDocumento.protocollo.protocolloAnnullato.autorizzazione) ? string.Empty : pSchedaDocumento.protocollo.protocolloAnnullato.autorizzazione;
                            pProtocolloLetto.DataAnnullamento = string.IsNullOrEmpty(pSchedaDocumento.protocollo.protocolloAnnullato.dataAnnullamento) ? string.Empty : pSchedaDocumento.protocollo.protocolloAnnullato.dataAnnullamento;
                        }
                        else
                        {
                            pProtocolloLetto.Annullato = EnumAnnullato.no.ToString();
                        }

                        //I servizi non hanno un metodo che restituisca in lettura informazioni sul fascicolo

                        //Gestione Mittenti/Destinatari (incluso mittente interno/in carico a)
                        if (!string.IsNullOrEmpty(pSchedaDocumento.tipoProto))
                        {
                            //Mittente e Destinatario
                            switch (pSchedaDocumento.tipoProto)
                            {
                                case "A":
                                    ProtocolloEntrata protEntrata = (ProtocolloEntrata)pSchedaDocumento.protocollo;
                                    pProtocolloLetto.MittentiDestinatari = new MittDestOut[1];

                                    pProtocolloLetto.MittentiDestinatari[0] = new MittDestOut();
                                    if (string.IsNullOrEmpty(protEntrata.mittente.cognome) || string.IsNullOrEmpty(protEntrata.mittente.nome))
                                        pProtocolloLetto.MittentiDestinatari[0].CognomeNome = protEntrata.mittente.descrizione + " - (MITTENTE)";
                                    else
                                        pProtocolloLetto.MittentiDestinatari[0].CognomeNome = protEntrata.mittente.cognome + " " + protEntrata.mittente.nome + " - (MITTENTE)";

                                    pProtocolloLetto.MittentiDestinatari[0].IdSoggetto = protEntrata.mittente.codiceRubrica;

                                    //I servizi non hanno un metodo che restituisca l'ufficio a cui viene assegnato un protocollo in ingresso
                                    //if (protEntrata.ufficioReferente != null)
                                    //{
                                    //    pProtocolloLetto.InCaricoA = ((UnitaOrganizzativa)protEntrata.ufficioReferente).codice;
                                    //    pProtocolloLetto.InCaricoA_Descrizione = ((UnitaOrganizzativa)protEntrata.ufficioReferente).descrizione;
                                    //}
                                    break;
                                case "P":
                                    ProtocolloUscita protUscita = (ProtocolloUscita)pSchedaDocumento.protocollo;

                                    if (protUscita.mittente != null)
                                    {
                                        pProtocolloLetto.MittenteInterno = ((UnitaOrganizzativa)protUscita.mittente).codiceRubrica;
                                        pProtocolloLetto.MittenteInterno_Descrizione = ((UnitaOrganizzativa)protUscita.mittente).descrizione;
                                    }

                                    pProtocolloLetto.MittentiDestinatari = new MittDestOut[protUscita.destinatari.Length];
                                    for (int count = 0; count < pProtocolloLetto.MittentiDestinatari.Length; count++)
                                    {
                                        pProtocolloLetto.MittentiDestinatari[count] = new MittDestOut();
                                        if (string.IsNullOrEmpty(protUscita.destinatari[count].cognome) || string.IsNullOrEmpty(protUscita.destinatari[count].nome))
                                            pProtocolloLetto.MittentiDestinatari[count].CognomeNome = protUscita.destinatari[count].descrizione + " - (DESTINATARIO)";
                                        else
                                            pProtocolloLetto.MittentiDestinatari[count].CognomeNome = protUscita.destinatari[count].cognome + " " + protUscita.destinatari[count].nome + " - (DESTINATARIO)";

                                        pProtocolloLetto.MittentiDestinatari[count].IdSoggetto = protUscita.destinatari[count].codiceRubrica;
                                    }
                                    break;
                                case "I":
                                    break;
                            }
                        }

                        //Sezione Allegati
                        FileDocumento fd = null;
                        int numAllegati = 0;
                        if ((pSchedaDocumento.documenti != null) && (pSchedaDocumento.documenti.Length > 0))
                        {
                            //Un record di documenti c'è sempre anche se non ho passato allegati
                            fd = pProxyProtDocsPa.getfile(pSchedaDocumento.protocollo.segnatura, _Operatore, _Password);
                            if (fd != null)
                                numAllegati += pSchedaDocumento.documenti.Length; //Dovrebbe essere al max 1
                        }
                        if ((pSchedaDocumento.allegati != null) && (pSchedaDocumento.allegati.Length > 0))
                            numAllegati += pSchedaDocumento.allegati.Length;

                        if (numAllegati > 0)
                        {
                            pProtocolloLetto.Allegati = new AllOut[numAllegati];

                            int count = 0;
                            //Documento principale
                            if ((pSchedaDocumento.documenti != null) && (pSchedaDocumento.documenti.Length > 0))
                            {
                                if (fd != null)
                                {
                                    pProtocolloLetto.Allegati[count] = new AllOut();
                                    pProtocolloLetto.Allegati[count].ContentType = fd.contentType;
                                    pProtocolloLetto.Allegati[count].TipoFile = string.IsNullOrEmpty(fd.estensioneFile) ? fd.name.Substring(fd.name.LastIndexOf('.') + 1) : fd.estensioneFile;
                                    pProtocolloLetto.Allegati[count].Serial = fd.name.Remove(fd.name.LastIndexOf("." + pProtocolloLetto.Allegati[count].TipoFile));
                                    pProtocolloLetto.Allegati[count].Image = fd.content;
                                    pProtocolloLetto.Allegati[count].Commento = string.IsNullOrEmpty(pSchedaDocumento.documenti[0].descrizione) ? string.Empty : pSchedaDocumento.documenti[0].descrizione;

                                    count++;
                                }
                            }

                            //Allegati
                            if ((pSchedaDocumento.allegati != null) && (pSchedaDocumento.allegati.Length > 0))
                            {
                                foreach (Init.SIGePro.Protocollo.ProxyDocsPa.Allegato all in pSchedaDocumento.allegati)
                                {
                                    fd = pProxyProtDocsPa.getFileAllegato(all, _Operatore, _Password);
                                    pProtocolloLetto.Allegati[count] = new AllOut();
                                    pProtocolloLetto.Allegati[count].ContentType = fd.contentType;
                                    pProtocolloLetto.Allegati[count].TipoFile = string.IsNullOrEmpty(fd.estensioneFile) ? fd.name.Substring(fd.name.LastIndexOf('.') + 1) : fd.estensioneFile;
                                    pProtocolloLetto.Allegati[count].Serial = fd.name.Remove(fd.name.LastIndexOf("." + pProtocolloLetto.Allegati[count].TipoFile));
                                    pProtocolloLetto.Allegati[count].Image = fd.content;
                                    pProtocolloLetto.Allegati[count].Commento = string.IsNullOrEmpty(all.descrizione) ? string.Empty : all.descrizione;


                                    count++;
                                }
                            }
                        }
                    }
                    else
                        throw new ProtocolloException("Errore: il numero protocollo " + NumProtocollo + " ed anno " + AnnoProtocollo + " non esiste");
                    //pProtocolloLetto.Warning = "Errore: il numero protocollo " + NumProtocollo + " ed anno " + AnnoProtocollo + " non esiste";
                }
                else
                    throw new ProtocolloException("Errore durante la lettura del protocollo di numero " + NumProtocollo + " ed anno " + AnnoProtocollo);
                //pProtocolloLetto.Warning = "Errore durante la lettura del protocollo di numero " + NumProtocollo + " ed anno " + AnnoProtocollo;
            }
            else
                throw new ProtocolloException("Errore durante la lettura del protocollo di numero " + NumProtocollo + " ed anno " + AnnoProtocollo);
            //pProtocolloLetto.Warning = "Errore durante la lettura del protocollo di numero " + NumProtocollo + " ed anno " + AnnoProtocollo;

            return pProtocolloLetto;
        }
        #endregion

        #region Utility

        private void GetParametriFromVertDocsPa()
        {
            try
            {
                VerticalizzazioneProtocolloDocspa protocolloDocsPa;

                protocolloDocsPa = new VerticalizzazioneProtocolloDocspa(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (protocolloDocsPa.Attiva)
                {
                    _protocolloLogs.DebugFormat(@"Valori parametri verticalizzazioni: url: {0}, 
                                                                                     operatore: {1}, 
                                                                                     password: {2}, 
                                                                                     codice fascicolo: {3}",
                    protocolloDocsPa.Url,
                    protocolloDocsPa.Operatore,
                    protocolloDocsPa.Password,
                    protocolloDocsPa.Codfascicolo);

                    _Operatore = protocolloDocsPa.Operatore;
                    _Password = protocolloDocsPa.Password;
                    _Url = protocolloDocsPa.Url;
                    _CodFascicolo = protocolloDocsPa.Codfascicolo;

                    _protocolloLogs.Debug("Fine recupero valori da verticalizzazioni");

                }
                else
                    throw new Exception("La verticalizzazione PROTOCOLLO_DOCSPA non è attiva");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI PARAMETRI DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCSPA", ex);
            }
        }

        #endregion

        #endregion
    }
}
