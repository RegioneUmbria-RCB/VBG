using VBG.Shared.Infrastructure.ServiceModel;
using Init.Utils;
using log4net;
using ProxyDocsPa;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Core.DocsPa;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    internal class PROTOCOLLO_DOCSPA : ProtocolloBase
    {

        #region Membri privati
        private readonly DocsPaWSLiteSoapClient pProxyProtDocsPa = null;
        //private string _Url = string.Empty;
        private readonly string _Operatore = string.Empty;
        private readonly string _Password = string.Empty;
        private readonly string _CodFascicolo = string.Empty;
        private bool _TrasmissioneInterna = false;
        private readonly DocsPaWSLiteSoapClientServiceCreator _docsPaWSLiteSoapClientServiceCreator;
        private readonly ILog _log = LogManager.GetLogger(typeof(PROTOCOLLO_DOCSPA));

        #endregion


        public PROTOCOLLO_DOCSPA(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            var vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocspa>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

            if (vert.Attiva)
            {
                this._protocolloLogs.DebugFormat(@"Valori parametri verticalizzazioni: url: {0}, 
                                                                                     operatore: {1}, 
                                                                                     password: {2}, 
                                                                                     codice fascicolo: {3}",
                vert.Url,
                vert.Operatore,
                vert.Password,
                vert.Codfascicolo);

                this._Operatore = vert.Operatore;
                this._Password = vert.Password;
                //_Url = vert.Url;
                this._CodFascicolo = vert.Codfascicolo;

                this._protocolloLogs.Debug("Fine recupero valori da verticalizzazioni");

            }
            else
                throw new Exception("La verticalizzazione PROTOCOLLO_DOCSPA non è attiva");

            this._docsPaWSLiteSoapClientServiceCreator = new DocsPaWSLiteSoapClientServiceCreator(this._log, bindingFactory, vert);
        }

        #region Metodi pubblici e privati della classe

        #region Metodi per la protocollazione
        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            DatiProtocolloResponseType protoRes = null;
            try
            {
                //GetParametriFromVertDocsPa();
                //pProxyProtDocsPa.Url = _Url;

                this._protocolloLogs.Debug("#### Chiamata al metodo di Protocollazione ####");

                var numeroProtocollo = 0;
                var annoProtocollo = 0;
                var segnatura = string.Empty;
                var dataProtocollo = string.Empty;
                var errorMessage = string.Empty;

                //Verifico il flusso di protocollazione perchè questo sistema non supporta protocollazione interne
                if (protoIn.Flusso == "I")
                    throw new Exception("IL SISTEMA DI PROTOCOLLAZIONE NON SUPPORTA IL FLUSSO INTERNO!");

                var esitoProtocollo = this.ProtocollaEFascicola(protoIn, out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo, out errorMessage);

                if (esitoProtocollo)
                {
                    //Setto gli allegati
                    this.SetAllegati(protoIn, segnatura);

                    //Per assegnare il protocollo ad un ufficio nel caso di flusso in "Arrivo" occorre fare queste chiamate
                    if (this._TrasmissioneInterna)
                    {
                        var esitoTrasmissione = this.TrasmissioneInterna(protoIn.Destinatari.Amministrazione[0].PROT_RUOLO, segnatura);

                        if (!esitoTrasmissione)
                            throw new Exception("ERRORE GENERATO DAL WEB METHOD EXECUTETRASM.");
                    }

                    this._protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    protoRes = this.CreaDatiProtocollo(numeroProtocollo, annoProtocollo, segnatura, dataProtocollo);
                }
                else
                    throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD PROTOCOLLAZIONEESTESACONCLASS, ERRORE: {0}", errorMessage));
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE AVVENUTO IN FASE DI PROTOCOLLAZIONE", ex);
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
                    this._protocolloLogs.InfoFormat("Chiamata a web method ricercaSchedabySegnatura, segnatura: {0}, userid: {1}, password: {2}", segnatura, this._Operatore, this._Password);
                    schedaDoc = this.pProxyProtDocsPa.ricercaSchedabySegnatura(segnatura, this._Operatore, this._Password);
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE AVVENUTO DURANTE LA RICERCA DELLA SCHEDA DA UNA SEGNATURA, web method ricercaSchedabySegnatura", ex);
                }

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.SchedaDocSoapResponseFileName, schedaDoc);

                var aRegistri = new Registro[1];
                aRegistri[0] = schedaDoc.registro;

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.RegistroSoapResponseFileName, schedaDoc.registro);

                object[] listModelli = null;

                try
                {
                    this._protocolloLogs.InfoFormat("Chiamata a web method getModelliPerTrasm, userid: {0}, password: {1}, registro file: {2}, tipo oggetto: {3}", this._Operatore, this._Password, ProtocolloLogsConstants.RegistroSoapResponseFileName, "D");
                    listModelli = this.pProxyProtDocsPa.getModelliPerTrasm(this._Operatore, this._Password, aRegistri, string.Empty, string.Empty, string.Empty, "D");
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE AVVENUTO DURANTE IL RECUPERO DEI MODELLI, web method getModelliPerTrasm", ex);
                }

                ModelloTrasmissione modelloTrasm = null;
                foreach (var elem in listModelli)
                {
                    if (((ModelloTrasmissione)elem).CODICE == modelloTrasmissione)
                    {
                        modelloTrasm = (ModelloTrasmissione)elem;
                        break;
                    }
                }

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ModelloTrasmissioneSoapResponseFileName, modelloTrasm);

                this._protocolloLogs.InfoFormat("Chiamata a executeTrasm, scheda file: {0}, modello file: {1}, userid: {2}, password: {3}", ProtocolloLogsConstants.SchedaDocSoapResponseFileName, ProtocolloLogsConstants.ModelloTrasmissioneSoapResponseFileName, this._Operatore, this._Password);
                var response = this.pProxyProtDocsPa.executeTrasm(string.Empty, schedaDoc, modelloTrasm, this._Operatore, this._Password);

                this._protocolloLogs.InfoFormat("Chiamata a executeTrasm avvenuta correttamente, risposta: {0}", response);

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA TRASMISSIONE INTERNA", ex);
            }
        }

        private bool ProtocollaEFascicola(Shared.Data.DatiProtocolloIn pProt, out int numeroProtocollo, out int annoProtocollo, out string segnatura, out string dataProtocollo, out string errorMessage)
        {
            try
            {
                annoProtocollo = 0;
                numeroProtocollo = 0;
                dataProtocollo = string.Empty;
                segnatura = string.Empty;
                errorMessage = string.Empty;
                var note = string.Empty;

                //Setto il campo note con l'informazione sul tipo documento (non c'è un campo specifico)
                var protTipiDOcMgr = new ProtocolloTipiDocumentoMgr(this.DatiProtocollo.Db);
                note = "Tipo documento: " + protTipiDOcMgr.GetById(this.DatiProtocollo.IdComune, pProt.TipoDocumento, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune).Descrizione;

                //Setto mittenti e destinatari
                var corrispondenti = new List<CorrLite>();

                //Setto i mittenti
                this.SetMittenti(corrispondenti, pProt);

                //Setto i destinatari
                this.SetDestinatari(corrispondenti, pProt);

                bool returnValue;
                //Lo stesso metodo (protocollazioneEstesaConClass) può essere utilizzato sia per la classificazione che per la fascicolazione 
                //Questa soluzione è adottata quando protocollazioneEstesaConClass viene utilizzato per la fascicolazione
                //(da intendere con fascicolo faldone). Nella versione lite mancano i ws per la fascicolazione (creazione fascicolo)
                if (!string.IsNullOrEmpty(this._CodFascicolo) && !this.GestisciFascicolazione)
                {
                    this._protocolloLogs.InfoFormat("Chiamata a web method protocollazioneEstesaConClass, operatore: {0}, password: {1}, oggetto: {2}, note: {3}, flusso: {4}, corrispondenti: {5}, codice fascicolo: {6}", this._Operatore, this._Password, pProt.Oggetto, note, pProt.Flusso, String.Join(", ", corrispondenti), this._CodFascicolo);
                    returnValue = this.pProxyProtDocsPa.protocollazioneEstesaConClass(this._Operatore, this._Password, pProt.Oggetto, note, pProt.Flusso, corrispondenti.ToArray(), this._CodFascicolo, out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo, out errorMessage);
                }
                else
                {
                    this._protocolloLogs.InfoFormat("Chiamata a web method protocollazioneEstesa, operatore: {0}, password: {1}, oggetto: {2}, note: {3}, flusso: {4}, corrispondenti: {5}", this._Operatore, this._Password, pProt.Oggetto, note, pProt.Flusso, String.Join(", ", corrispondenti));
                    returnValue = this.pProxyProtDocsPa.protocollazioneEstesa(this._Operatore, this._Password, pProt.Oggetto, note, pProt.Flusso, corrispondenti.ToArray(), out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo);
                }
                //Questa soluzione è adottata quando protocollazioneEstesaConClass viene utilizzato per la classificazione
                //return pProxyProtDocsPa.protocollazioneEstesaConClass(_Operatore, _Password, pProt.Oggetto, note, pProt.Flusso, corrispondenti.ToArray(), pProt.Classifica, out numeroProtocollo, out annoProtocollo, out segnatura, out dataProtocollo, out errorMessage);

                this._protocolloLogs.InfoFormat("Protocollazione avvenuta con successo, valore di ritorno: {0}", returnValue);

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
                        this._TrasmissioneInterna = true;
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
                        foreach (var pAmministrazione in pProt.Destinatari.Amministrazione)
                        {
                            var corr = new CorrLite();
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
                    foreach (var pAnagrafe in pProt.Destinatari.Anagrafe)
                    {
                        var corr = new CorrLite();
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
                        var corr = new CorrLite();
                        corr.tipoCorrispondente = "M";
                        var sProtUo = pProt.Mittenti.Amministrazione[0].PROT_UO.Split(new Char[] { '/' });
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
                        var corr = new CorrLite();
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
                    var corr = new CorrLite();
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
                if (!pProt.HaAllegati())
                {
                    return;
                }


                var schedaDoc = this.pProxyProtDocsPa.ricercaSchedabySegnatura(segnatura, this._Operatore, this._Password);
                if (pProt.NumeroAllegatiPresenti > 1)
                    schedaDoc.allegati = new ProxyDocsPa.Allegato[pProt.NumeroAllegatiPresenti - 1];

                for (var iCount = 0; iCount < pProt.NumeroAllegatiPresenti; iCount++)
                {
                    var allegato = pProt.RecuperaAllegati().ToArray()[iCount];


                    var bytes = allegato.OGGETTO;
                    var fileDocumento = new FileDocumento();

                    //non dovrebbe essere necessario
                    if (bytes != null)
                    {
                        // nuova versione 
                        var iPos = allegato.Descrizione.LastIndexOf("." + allegato.Extension);
                        //string pattern=@"[\\/:*?<>|\" + "\"]"; questa istruzione può essere usata in alternativa alla precedente togliendo l'istruzione Replace successiva
                        if (iPos != -1)
                        {
                            allegato.Descrizione = this.SistemaDenominazioneAllegato(allegato.Descrizione);

                            using (var pFs = new FileStream(this._protocolloLogs.Folder + allegato.Descrizione, FileMode.Create))
                            {
                                pFs.Write(bytes, 0, bytes.Length);

                                fileDocumento.name = Path.GetFileName(this._protocolloLogs.Folder + allegato.Descrizione);
                                fileDocumento.fullName = fileDocumento.name;
                                fileDocumento.contentType = allegato.MimeType;
                                fileDocumento.length = (int)pFs.Length;
                                fileDocumento.estensioneFile = allegato.Extension;
                                fileDocumento.content = StreamUtils.StreamToBytes(pFs);
                            }
                        }
                        else
                        {
                            allegato.NOMEFILE = this.SistemaDenominazioneAllegato(allegato.NOMEFILE);
                            using (var pFs = new FileStream(this._protocolloLogs.Folder + allegato.NOMEFILE, FileMode.Create))
                            {
                                pFs.Write(bytes, 0, bytes.Length);

                                fileDocumento.name = Path.GetFileName(this._protocolloLogs.Folder + allegato.NOMEFILE);
                                fileDocumento.fullName = fileDocumento.name;
                                fileDocumento.contentType = allegato.MimeType;
                                fileDocumento.length = (int)pFs.Length;
                                fileDocumento.estensioneFile = allegato.Extension;
                                fileDocumento.content = StreamUtils.StreamToBytes(pFs);
                            }
                        }
                    }
                    else
                        throw new ProtocolloException("Errore generato dal web method Inserimento del protocollo DocsPa. Metodo: SetAllegati, modulo: ProtocolloDocsPa. C'è un allegato con il campo OGGETTO null.\r\n");

                    if (iCount == 0)
                    {
                        schedaDoc.documenti[iCount].descrizione = fileDocumento.name;
                        this.pProxyProtDocsPa.putfile(schedaDoc.documenti[iCount], fileDocumento, this._Operatore, this._Password);
                    }
                    else
                    {
                        schedaDoc.allegati[iCount - 1] = new ProxyDocsPa.Allegato();
                        //Setto il numero di pagine nella chiamata ad 1(non ho questa informazione e se passo string.empty
                        //mi restituisce un'eccezione)
                        this.pProxyProtDocsPa.aggiungiAllegato(this._Operatore, this._Password, schedaDoc, "1", fileDocumento.name, fileDocumento, schedaDoc.allegati[iCount - 1]);
                    }
                }

            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE IL SETTAGGIO DEGLI ALLEGATI", ex);
            }
        }

        private string SistemaDenominazioneAllegato(string denominazione)
        {
            var pattern = @"[\\/:*?<>|]";
            var nuovaDenominazione = denominazione;

            nuovaDenominazione = Regex.Replace(nuovaDenominazione, pattern, "");
            nuovaDenominazione = nuovaDenominazione.Replace("\"", "");
            nuovaDenominazione = nuovaDenominazione.Replace("€", "Euro");

            return nuovaDenominazione;
        }

        private DatiProtocolloResponseType CreaDatiProtocollo(int numeroProtocollo, int annoProtocollo, string segnatura, string dataProtocollo)
        {
            try
            {
                var protoRes = new DatiProtocolloResponseType();
                protoRes.IdProtocollo = segnatura;
                protoRes.AnnoProtocollo = annoProtocollo.ToString();
                protoRes.NumeroProtocollo = numeroProtocollo.ToString();
                protoRes.DataProtocollo = dataProtocollo;

                this._protocolloLogs.InfoFormat("Dati protocollo restituiti, id protocollo: {0}, numero: {1}, anno: {2}, data: {3}", protoRes.IdProtocollo, protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO", ex);
            }
        }

        #endregion

        #region Metodi per la fascicolazione di un protocollo

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                //GetParametriFromVertDocsPa();
                //pProxyProtDocsPa.Url = _Url;

                return this.Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA VERIFICA DELLA FASCICOLAZIONE", ex);
            }
        }

        private DatiProtocolloFascicolatoResponseType Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var datiProtFasc = new DatiProtocolloFascicolatoResponseType();

            SchedaDocumento schedaDocumento = null;
            schedaDocumento = this.LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);

            if (schedaDocumento != null)
            {
                if (string.IsNullOrEmpty(schedaDocumento.fascicolato))
                {
                    datiProtFasc.Fascicolato = EnumFascicolatoType.no;
                }
                else
                {
                    datiProtFasc.Fascicolato = EnumFascicolatoType.si;
                }
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolatoType.warning;
                datiProtFasc.NoteFascicolo = "Errore durante la verifica della fascicolazione del protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;
            }

            return datiProtFasc;
        }

        #endregion

        #region Metodi per l'annullamento di un protocollo

        public override DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                //GetParametriFromVertDocsPa();
                //pProxyProtDocsPa.Url = _Url;

                return this.Annullato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA VERIFICA DELL'ANNULLAMENTO DEL PROTOCOLLO", ex);
            }
        }

        private DatiProtocolloAnnullatoResponseType Annullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var datiProtAnn = new DatiProtocolloAnnullatoResponseType();

            SchedaDocumento schedaDocumento = null;
            schedaDocumento = this.LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);


            if (schedaDocumento != null)
            {
                if (schedaDocumento.protocollo != null)
                {
                    if (schedaDocumento.protocollo.protocolloAnnullato != null)
                    {
                        datiProtAnn.Annullato = EnumAnnullatoType.si;
                        datiProtAnn.MotivoAnnullamento = string.IsNullOrEmpty(schedaDocumento.protocollo.protocolloAnnullato.autorizzazione) ? string.Empty : schedaDocumento.protocollo.protocolloAnnullato.autorizzazione;
                    }
                    else
                    {
                        datiProtAnn.Annullato = EnumAnnullatoType.no;
                    }
                }
                else
                {
                    datiProtAnn.Annullato = EnumAnnullatoType.warning;
                    datiProtAnn.NoteAnnullamento = "Errore durante la verifica della nullabilità del protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;
                }
            }
            else
            {
                datiProtAnn.Annullato = EnumAnnullatoType.warning;
                datiProtAnn.NoteAnnullamento = "Non è presente nessun protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;
            }

            return datiProtAnn;
        }

        public override void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            try
            {
                //GetParametriFromVertDocsPa();
                //pProxyProtDocsPa.Url = _Url;

                this._protocolloLogs.InfoFormat("Chiamata a web method annullaProtocollo, Operatore: {0}, Password: {1}, id protocollo: {2}, motivo annullamento: {3}", this._Operatore, this._Password, idProtocollo, motivoAnnullamento);
                this.pProxyProtDocsPa.annullaProtocollo(this._Operatore, this._Password, idProtocollo, motivoAnnullamento);
                this._protocolloLogs.Info("Il protocollo è stato annullato con successo");

            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE L'ANNULLAMENTO DI UN PROTOCOLLO", ex);
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
                        var sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                        var sNumProtocollo = sNumProtSplit[0];

                        this._protocolloLogs.InfoFormat("Chiamata a web method ricercaSchedabyChiaveProto, numero protocollo: {0}, anno protocollo: {1}, operatore: {2}, password: {3}", sNumProtocollo, annoProtocollo, this._Operatore, this._Password);

                        docOut = this.pProxyProtDocsPa.ricercaSchedabyChiaveProto(sNumProtocollo, annoProtocollo, string.Empty, string.Empty, this._Operatore, this._Password);

                        this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.SchedaDocSoapResponseFileName, docOut);


                    }
                    else
                    {
                        this._protocolloLogs.InfoFormat("Chiamata a web method ricercaSchedabyChiaveProto, id protocollo: {0}, operatore: {1}, password: {2}", idProtocollo, this._Operatore, this._Password);
                        docOut = this.pProxyProtDocsPa.ricercaSchedabySegnatura(idProtocollo, this._Operatore, this._Password);

                        if (!string.IsNullOrEmpty(annoProtocollo) && !string.IsNullOrEmpty(numeroProtocollo))
                        {
                            var sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
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


        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            this._protocolloLogs.Debug("Inizio funzionalità LeggiProtocollo");
            DatiProtocolloLettoResponseType pProtocolloLetto = null;

            try
            {
                //GetParametriFromVertDocsPa();
                //pProxyProtDocsPa.Url = _Url;
                this.NumProtocollo = leggiProtocolloRequest.NumeroProtocollo;
                this.AnnoProtocollo = leggiProtocolloRequest.AnnoProtocollo;

                var schedaDoc = this.LeggiProtocolloDocumento(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);

                if (schedaDoc != null)
                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, schedaDoc);

                pProtocolloLetto = this.CreaDatiProtocolloLetto(schedaDoc);

                return new List<DatiProtocolloLettoResponseType>() { pProtocolloLetto };
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO CON ID: {0}, NUMERO: {1}, ANNO: {2}", leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo), ex);
            }
        }

        private DatiProtocolloLettoResponseType CreaDatiProtocolloLetto(SchedaDocumento pSchedaDocumento)
        {
            var pProtocolloLetto = new DatiProtocolloLettoResponseType();

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
                            pProtocolloLetto.Annullato = EnumAnnullatoType.si.ToString();
                            pProtocolloLetto.MotivoAnnullamento = string.IsNullOrEmpty(pSchedaDocumento.protocollo.protocolloAnnullato.autorizzazione) ? string.Empty : pSchedaDocumento.protocollo.protocolloAnnullato.autorizzazione;
                            pProtocolloLetto.DataAnnullamento = string.IsNullOrEmpty(pSchedaDocumento.protocollo.protocolloAnnullato.dataAnnullamento) ? string.Empty : pSchedaDocumento.protocollo.protocolloAnnullato.dataAnnullamento;
                        }
                        else
                        {
                            pProtocolloLetto.Annullato = EnumAnnullatoType.no.ToString();
                        }

                        //I servizi non hanno un metodo che restituisca in lettura informazioni sul fascicolo

                        //Gestione Mittenti/Destinatari (incluso mittente interno/in carico a)
                        if (!string.IsNullOrEmpty(pSchedaDocumento.tipoProto))
                        {
                            //Mittente e Destinatario
                            switch (pSchedaDocumento.tipoProto)
                            {
                                case "A":
                                    var protEntrata = (ProtocolloEntrata)pSchedaDocumento.protocollo;
                                    pProtocolloLetto.MittentiDestinatari = new MittDestOutType[1];

                                    pProtocolloLetto.MittentiDestinatari[0] = new MittDestOutType();
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
                                    var protUscita = (ProtocolloUscita)pSchedaDocumento.protocollo;

                                    if (protUscita.mittente != null)
                                    {
                                        pProtocolloLetto.MittenteInterno = ((UnitaOrganizzativa)protUscita.mittente).codiceRubrica;
                                        pProtocolloLetto.MittenteInterno_Descrizione = ((UnitaOrganizzativa)protUscita.mittente).descrizione;
                                    }

                                    pProtocolloLetto.MittentiDestinatari = new MittDestOutType[protUscita.destinatari.Length];
                                    for (var count = 0; count < pProtocolloLetto.MittentiDestinatari.Length; count++)
                                    {
                                        pProtocolloLetto.MittentiDestinatari[count] = new MittDestOutType();
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
                        var numAllegati = 0;
                        if ((pSchedaDocumento.documenti != null) && (pSchedaDocumento.documenti.Length > 0))
                        {
                            //Un record di documenti c'è sempre anche se non ho passato allegati
                            fd = this.pProxyProtDocsPa.getfile(pSchedaDocumento.protocollo.segnatura, this._Operatore, this._Password);
                            if (fd != null)
                                numAllegati += pSchedaDocumento.documenti.Length; //Dovrebbe essere al max 1
                        }
                        if ((pSchedaDocumento.allegati != null) && (pSchedaDocumento.allegati.Length > 0))
                            numAllegati += pSchedaDocumento.allegati.Length;

                        if (numAllegati > 0)
                        {
                            pProtocolloLetto.Allegati = new AllegatoResponseType[numAllegati];

                            var count = 0;
                            //Documento principale
                            if ((pSchedaDocumento.documenti != null) && (pSchedaDocumento.documenti.Length > 0))
                            {
                                if (fd != null)
                                {
                                    pProtocolloLetto.Allegati[count] = new AllegatoResponseType();
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
                                foreach (var all in pSchedaDocumento.allegati)
                                {
                                    fd = this.pProxyProtDocsPa.getFileAllegato(all, this._Operatore, this._Password);
                                    pProtocolloLetto.Allegati[count] = new AllegatoResponseType();
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
                        throw new ProtocolloException("Errore: il numero protocollo " + this.NumProtocollo + " ed anno " + this.AnnoProtocollo + " non esiste");
                    //pProtocolloLetto.Warning = "Errore: il numero protocollo " + NumProtocollo + " ed anno " + AnnoProtocollo + " non esiste";
                }
                else
                    throw new ProtocolloException("Errore durante la lettura del protocollo di numero " + this.NumProtocollo + " ed anno " + this.AnnoProtocollo);
                //pProtocolloLetto.Warning = "Errore durante la lettura del protocollo di numero " + NumProtocollo + " ed anno " + AnnoProtocollo;
            }
            else
                throw new ProtocolloException("Errore durante la lettura del protocollo di numero " + this.NumProtocollo + " ed anno " + this.AnnoProtocollo);
            //pProtocolloLetto.Warning = "Errore durante la lettura del protocollo di numero " + NumProtocollo + " ed anno " + AnnoProtocollo;

            return pProtocolloLetto;
        }
        #endregion

        #region Utility

        //private void GetParametriFromVertDocsPa()
        //{
        //    try
        //    {
        //        VerticalizzazioneProtocolloDocspa protocolloDocsPa;

        //        protocolloDocsPa = new VerticalizzazioneProtocolloDocspa(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

        //        if (protocolloDocsPa.Attiva)
        //        {
        //            _protocolloLogs.DebugFormat(@"Valori parametri verticalizzazioni: url: {0}, 
        //                                                                             operatore: {1}, 
        //                                                                             password: {2}, 
        //                                                                             codice fascicolo: {3}",
        //            protocolloDocsPa.Url,
        //            protocolloDocsPa.Operatore,
        //            protocolloDocsPa.Password,
        //            protocolloDocsPa.Codfascicolo);

        //            _Operatore = protocolloDocsPa.Operatore;
        //            _Password = protocolloDocsPa.Password;
        //            _Url = protocolloDocsPa.Url;
        //            _CodFascicolo = protocolloDocsPa.Codfascicolo;

        //            _protocolloLogs.Debug("Fine recupero valori da verticalizzazioni");

        //        }
        //        else
        //            throw new Exception("La verticalizzazione PROTOCOLLO_DOCSPA non è attiva");
        //    }
        //    catch (Exception ex)
        //    {
        //        throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI PARAMETRI DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCSPA", ex);
        //    }
        //}

        #endregion

        #endregion
    }
}
