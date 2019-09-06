using System;
using System.ComponentModel;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using Microsoft.Web.Services2.Dime;
using Microsoft.Web.Services2.Attachments;
using Init.SIGePro.SegnaturaPindaro;
using System.Collections.Generic;
using Init.SIGePro.Exceptions.Protocollo;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Validation;
using log4net;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Pindaro.Proxies;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    /// <summary>
    /// Descrizione di riepilogo per PROTOCOLLO_PINDARO.
    /// </summary>
    public class PROTOCOLLO_PINDARO : ProtocolloBase
    {
        #region Costruttori
        public PROTOCOLLO_PINDARO()
        {
            pProxyProtPindaro = new ProxyProtPindaro();
        }
        #endregion

        #region Membri privati
        private ProxyProtPindaro pProxyProtPindaro = null;
        private string _CodiceEnte = "";
        private string _Operatore = "";
        private string _Password = "";
        private string _Url = "";

        ILog _log = LogManager.GetLogger(typeof(PROTOCOLLO_PINDARO));

        #endregion

        #region Metodi pubblici e privati della classe

        #region Metodo usato per eseguire la protocollazione
        public override DatiProtocolloRes Protocollazione(Data.DatiProtocolloIn protoIn)
        {
            
            DatiProtocolloRes protoRes = null;

            try
            {
                GetParametriFromVertPindaro();
                pProxyProtPindaro.Url = _Url;

                //Si chiama il web method per effettuare la login
                string _DST = Login();

                //Nel caso in cui ci sono degli allegati viene chiamato il web method per effettuare il loro inserimento
                if (protoIn.Allegati != null)
                {
                    for (int iCount = 0; iCount < protoIn.Allegati.Count; iCount++)
                    {
                        long lId = Inserimento(protoIn.Allegati[iCount].MimeType, protoIn.Allegati[iCount], _DST);
                        protoIn.Allegati[iCount].ID = lId;
                    }
                }

                //Viene creato il file segnatura.xml
                CreaSegnatura(protoIn);

#if WSE
                //Viene chiamato il metodo Protocollazione del WebService Pindaro
                Attachment attachment = new Attachment("text/xml", _protocolloLogs.Folder + ProtocolloLogsConstants.SegnaturaXmlFileName);
                pProxyProtPindaro.RequestSoapContext.Attachments.Add(attachment);

                _protocolloLogs.InfoFormat("Chiamata al web method Protocollazione, operatore: {0}, token: {1}", _Operatore, _DST);
                var response = pProxyProtPindaro.Protocollazione(_Operatore, _DST);
                
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);
                
                if (response.lngErrNumber != 0)
                    throw new ProtocolloException("Errore generato dal web method Protocollazione. Numero Errore: " + response.lngErrNumber.ToString() + ". " + response.strErrString);

                _protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");
                protoRes = CreaDatiProtocollo(response);
#endif
            }
            catch (IOException ex)
            {
                throw new ProtocolloException("Errore generato durante la protocollazione eseguita con il protocollo Pindaro. La directory specificata è in sola lettura. Metodo: Protocollazione, modulo: ProtocolloPindaro. " + ex.Message + "\r\n");
            }
            catch (UnauthorizedAccessException ex)
            {
                throw new ProtocolloException("Errore generato durante la protocollazione eseguita con il protocollo Pindaro. Il chiamante non dispone dell'autorizzazione necessaria. Metodo: Protocollazione, modulo: ProtocolloPindaro. " + ex.Message + "\r\n");
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("Errore generato durante la protocollazione eseguita con il protocollo Pindaro. Metodo: Protocollazione, modulo: ProtocolloPindaro", ex);
            }

            return protoRes;
        }

        #endregion

        #region Metodo usato per eseguire il login
        /// <summary>
        /// Metodo usato per effettuare il login
        /// </summary>
        /// <returns>Token</returns>
        private string Login()
        {
            string _DST = "";
            LoginRet pLgnRes;
            try
            {
                _protocolloLogs.InfoFormat("Chiamata al web method Login, codice ente: {0}, operatore: {1}, password: {2}", _CodiceEnte, _Operatore, _Password);
                pLgnRes = pProxyProtPindaro.Login(_CodiceEnte, _Operatore, _Password);

                if (pLgnRes.LngErrNumber != 0)
                    throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", pLgnRes.LngErrNumber.ToString(), pLgnRes.StrErrString));
                else
                    _DST = pLgnRes.strDST;

                if (_DST == "")
                    throw new Exception("IL TOKEN E' VUOTO");
            }
            catch (Exception ex)
            {
                throw new Exception("AUTENTICAZIONE AL WEB SERVICE FALLITA", ex);
            }

            return _DST;
        }
        #endregion

        #region Metodo usato per eseguire l'inserimento degli allegati
        /// <summary>
        /// Metodo usato per eseguire l'inserimento degli allegati al protocollo
        /// </summary>
        /// <param name="sType">Tipo dell'allegato</param>
        /// <param name="pProtAll">Istanza della classe ProtocolloAllegati</param>
        /// <param name="sToken">Token ricevuto dalla chiamata al metodo Login</param>
        /// <returns></returns>
        private long Inserimento(string sType, ProtocolloAllegati pProtAll, string sToken)
        {
            long _IdAllegato = -1;

#if WSE
            InserimentoRet pInsRes;
            try
            {
                //Viene creato il file allegato
                byte[] bytes = pProtAll.OGGETTO;

                //non dovrebbe essere necessario
                if (bytes != null)
                {
                    // nuova versione 
                    string sDescrizione = "";
                    //DimeAttachment attachment;
                    Attachment attachment;
                    int iPos = pProtAll.Descrizione.LastIndexOf("." + pProtAll.Extension);
                    if (iPos != -1)
                        sDescrizione = pProtAll.Descrizione.Substring(0, iPos);
                    //Usate per poter salvare il file
                    string pattern = @"[\\/:*?<>|]";
                    //string pattern=@"[\\/:*?<>|\" + "\"]"; questa istruzione può essere usata in alternativa alla precedente togliendo l'istruzione Replace successiva
                    if (sDescrizione != "")
                    {
                        pProtAll.Descrizione = Regex.Replace(pProtAll.Descrizione, pattern, "");
                        pProtAll.Descrizione = pProtAll.Descrizione.Replace("\"", "");
                        pProtAll.Descrizione = pProtAll.Descrizione.Replace("€", "Euro");
                        using (FileStream pFs = new FileStream(_protocolloLogs.Folder + pProtAll.Descrizione, FileMode.Create))
                        {
                            pFs.Write(bytes, 0, bytes.Length);
                        }
                        attachment = new Attachment(sType, _protocolloLogs.Folder + pProtAll.Descrizione);
                    }
                    else
                    {
                        pProtAll.NOMEFILE = Regex.Replace(pProtAll.NOMEFILE, pattern, "");
                        pProtAll.NOMEFILE = pProtAll.NOMEFILE.Replace("\"", "");
                        pProtAll.NOMEFILE = pProtAll.NOMEFILE.Replace("€", "Euro");
                        using (FileStream pFs = new FileStream(_protocolloLogs.Folder + pProtAll.NOMEFILE, FileMode.Create))
                        {
                            pFs.Write(bytes, 0, bytes.Length);
                        }
                        attachment = new Attachment(sType, _protocolloLogs.Folder + pProtAll.NOMEFILE);
                    }
                    // nuova versione 
                    _protocolloLogs.InfoFormat("Inserimento allegato, codice oggetto: {0}, nome file: {1}, descrizione file: {2}, percorso file: {3}", pProtAll.CODICEOGGETTO, pProtAll.NOMEFILE, pProtAll.Descrizione, (_protocolloLogs.Folder + pProtAll.NOMEFILE));
                    
                    pProxyProtPindaro.RequestSoapContext.Attachments.Add(attachment);
                    
                    _protocolloLogs.InfoFormat("Chiamata al web method Inserimento, operatore: {0}, token: {1}", _Operatore, sToken);

                    pInsRes = pProxyProtPindaro.Inserimento(_Operatore, sToken);

                    if (pInsRes.lngErrNumber != 0)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE: {1}", pInsRes.lngErrNumber.ToString(), pInsRes.strErrString));
                    else
                        _IdAllegato = pInsRes.lngDocID;

                    if (_IdAllegato == -1)
                        throw new Exception("ID ALLEGATO NON VALIDO");
                }
                else
                    throw new Exception("ALLEGATO CON IL CAMPO OGGETTO NULL");

                return _IdAllegato;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("GENERATO UN ERRORE DURANTE L'INSERIMENTO DELL'ALLEGATO, CODICE: {0}", pProtAll.CODICEOGGETTO), ex);
            }
#endif
            
        }
        #endregion

        #region Metodi usati per realizzare il file segnatura.xml


        /// <summary>
        /// Metodo usato per creare il file segnatura.xml
        /// </summary>
        /// <param name="pProt">Istanza della classe Protocollo</param>
        private void CreaSegnatura(Data.DatiProtocolloIn pProt)
        {
            try
            {
                Segnatura pSegnatura = new Segnatura();

                //Viene creato l'oggetto Segnatura da serializzare
                pSegnatura.Intestazione = new Intestazione();
                //Setto l'oggetto
                pSegnatura.Intestazione.Oggetto = pProt.Oggetto;
                //Setto il flusso
                pSegnatura.Intestazione.Identificatore = new Identificatore();
                pSegnatura.Intestazione.Identificatore.CodiceAmministrazione = _CodiceEnte;
                pSegnatura.Intestazione.Identificatore.CodiceAOO = "AOO";

                switch (pProt.Flusso)
                {
                    case "A":
                        pSegnatura.Intestazione.Identificatore.Flusso = "E";
                        break;
                    case "P":
                        pSegnatura.Intestazione.Identificatore.Flusso = "U";
                        break;
                    case "I":
                        pSegnatura.Intestazione.Identificatore.Flusso = pProt.Flusso;
                        break;
                }

                pSegnatura.Intestazione.Identificatore.Flusso = pProt.Flusso; //A Reggio Calabria funzionava pure così, ma la documentazione sembra richiedere lo switch
                pSegnatura.Intestazione.Identificatore.NumeroRegistrazione = string.Empty;
                pSegnatura.Intestazione.Identificatore.DataRegistrazione = string.Empty;
                
                //Setto la classifica
                pSegnatura.Intestazione.Classifica = new Classifica();
                pSegnatura.Intestazione.Classifica.CodiceTitolario = pProt.Classifica;
                pSegnatura.Intestazione.Classifica.CodiceAmministrazione = _CodiceEnte;
                pSegnatura.Intestazione.Classifica.CodiceAOO = "AOO";

                //Setto i mittenti
                CreaSegnaturaMittenti(pProt, pSegnatura);

                //Setto i destinatari
                CreaSegnaturaDestinatari(pProt, pSegnatura);

                //Setto gli allegati del protocollo
                CreaSegnaturaAllegati(pProt, pSegnatura);

                //Setto la sezione ApplicativoProtocollo
                CreaSegnaturaApplicativoProtocollo(pProt, pSegnatura);
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, pSegnatura, ProtocolloValidation.TipiValidazione.XSD, ProtocolloLogsConstants.SegnaturaXsdFileName, true);
                _log.Debug("Creato il file segnatura.xml");

                /*if (Debug == "true")
                {
                    //_protocolloSerializer.Serialize("Segnatura.xml", pSegnatura,TipiValidazione.XSD, "segnatura.xsd", true);
                    LogMessage("Creato il file segnatura.xml");
                }*/
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DELLA SEGNATURA", ex);
            }
        }

        /// <summary>
        /// Metodo usato per realizzare la sezione Mittenti
        /// </summary>
        /// <param name="pProt">Istanza della classe Protocollo</param>
        /// <param name="pSegnatura">Istanza della classe Segnatura</param>
        private void CreaSegnaturaMittenti(Data.DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            try
            {
                pSegnatura.Intestazione.Mittente = new Mittente();

                //Verifico le amministrazioni (interne ed esterne)
                if (pProt.Mittenti.Amministrazione.Count >= 1)
                {
                    pSegnatura.Intestazione.Mittente.Items = new object[2];

                    pSegnatura.Intestazione.Mittente.Items[1] = new AOO(); //affinchè il file xml sia validabile 
                    ((AOO)pSegnatura.Intestazione.Mittente.Items[1]).CodiceAOO = "AOO";

                    pSegnatura.Intestazione.Mittente.Items[0] = new Amministrazione();
                    if (!StringChecker.IsStringEmpty(pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE))
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Denominazione = pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
                    else
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Denominazione = string.Empty;
                    ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).CodiceAmministrazione = _CodiceEnte;
                    ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).IndirizzoTelematico = new IndirizzoTelematico();
                    ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).IndirizzoTelematico.Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].EMAIL))
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).IndirizzoTelematico.Text[0] = pProt.Mittenti.Amministrazione[0].EMAIL;


                    if (!StringChecker.IsStringEmpty(pProt.Mittenti.Amministrazione[0].PROT_UO))
                    {
                        if (pProt.Flusso == "I")
                        {
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items = new object[3];
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName = new ItemChoiceType[3];
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0] = new Persona();
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName[0] = ItemChoiceType.Persona;
                            //Occorre settare solo la proprietà Cognome per poter vedere il campo Corrispondente settato nel caso di una amministrazione interna come mittente e flusso interno
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).Cognome = pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).CodiceFiscale = pProt.Mittenti.Amministrazione[0].PARTITAIVA;
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).IndirizzoTelematico = new IndirizzoTelematico();
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).IndirizzoTelematico.Text = new string[1];
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).IndirizzoTelematico.Text[0] = pProt.Mittenti.Amministrazione[0].EMAIL;
                            if (string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PARTITAIVA))
                                ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).id = string.Empty;
                            else
                                ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).id = pProt.Mittenti.Amministrazione[0].PARTITAIVA;

                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[1] = pProt.Mittenti.Amministrazione[0].TELEFONO1;
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName[1] = ItemChoiceType.Telefono;

                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[2] = pProt.Mittenti.Amministrazione[0].FAX;
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName[2] = ItemChoiceType.Fax;
                        }
                        else
                        {
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items = new UnitaOrganizzativa[1];
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName = new ItemChoiceType[1];
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0] = new UnitaOrganizzativa();
                            ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName[0] = ItemChoiceType.UnitaOrganizzativa;
                            ((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).id = pProt.Mittenti.Amministrazione[0].PROT_UO;
                        }
                    }
                    else
                    {
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items = new object[3];
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName = new ItemChoiceType[3];
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0] = new Persona();
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName[0] = ItemChoiceType.Persona;
                        //Occorre settare solo la proprietà Cognome per poter vedere il campo Corrispondente settato nel caso di una amministrazione esterna come mittente
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).Cognome = pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).CodiceFiscale = pProt.Mittenti.Amministrazione[0].PARTITAIVA;
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).IndirizzoTelematico = new IndirizzoTelematico();
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).IndirizzoTelematico.Text = new string[1];
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).IndirizzoTelematico.Text[0] = pProt.Mittenti.Amministrazione[0].EMAIL;
                        if (string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PARTITAIVA))
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).id = string.Empty;
                        else
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[0]).id = pProt.Mittenti.Amministrazione[0].PARTITAIVA;

                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[1] = pProt.Mittenti.Amministrazione[0].TELEFONO1;
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName[1] = ItemChoiceType.Telefono;

                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).Items[2] = pProt.Mittenti.Amministrazione[0].FAX;
                        ((Amministrazione)pSegnatura.Intestazione.Mittente.Items[0]).ItemElementName[2] = ItemChoiceType.Fax;
                    }

                    //Perchè nel caso che il mittente sia un'amministrazione esterna ed una anagrafica devo prendere solo la prima
                    return;
                }

                //Verifico le anagrafiche
                if (pProt.Mittenti.Anagrafe.Count >= 1)
                {
                    pSegnatura.Intestazione.Mittente.Items = new Persona[1];

                    pSegnatura.Intestazione.Mittente.Items[0] = new Persona();
                    ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).Nome = pProt.Mittenti.Anagrafe[0].NOME;
                    ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).Cognome = pProt.Mittenti.Anagrafe[0].NOMINATIVO;

                    if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].CODICEFISCALE))
                    {
                        ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).CodiceFiscale = pProt.Mittenti.Anagrafe[0].CODICEFISCALE;
                        ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).id = pProt.Mittenti.Anagrafe[0].CODICEFISCALE;
                    }
                    else
                    {
                        ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).CodiceFiscale = pProt.Mittenti.Anagrafe[0].PARTITAIVA;
                        ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).id = pProt.Mittenti.Anagrafe[0].PARTITAIVA;
                    }
                    ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).Titolo = pProt.Mittenti.Anagrafe[0].TITOLO;
                    ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).IndirizzoTelematico = new IndirizzoTelematico();
                    ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).IndirizzoTelematico.Text = new string[1];
                    ((Persona)pSegnatura.Intestazione.Mittente.Items[0]).IndirizzoTelematico.Text[0] = pProt.Mittenti.Anagrafe[0].EMAIL;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEL MITTENTE DELLA SEGNATURA", ex);
            }
        }

        /// <summary>
        /// Metodo usato per realizzare la sezione Destinatari
        /// </summary>
        /// <param name="pProt">Istanza della classe Protocollo</param>
        /// <param name="pSegnatura">Istanza della classe Segnatura</param>
        private void CreaSegnaturaDestinatari(Data.DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            try
            {
                pSegnatura.Intestazione.Destinatario = new Destinatario();

                //Verifico le amministrazioni (interne ed esterne)
                if (pProt.Destinatari.Amministrazione.Count >= 1)
                {
                    pSegnatura.Intestazione.Destinatario.Telefono = new string[2];
                    pSegnatura.Intestazione.Destinatario.Telefono[0] = pProt.Destinatari.Amministrazione[0].TELEFONO1;
                    pSegnatura.Intestazione.Destinatario.Telefono[1] = pProt.Destinatari.Amministrazione[0].TELEFONO2;
                    pSegnatura.Intestazione.Destinatario.Fax = new string[1];
                    pSegnatura.Intestazione.Destinatario.Fax[0] = pProt.Destinatari.Amministrazione[0].FAX;
                    pSegnatura.Intestazione.Destinatario.IndirizzoTelematico = new IndirizzoTelematico();
                    pSegnatura.Intestazione.Destinatario.IndirizzoTelematico.Text = new string[1];
                    pSegnatura.Intestazione.Destinatario.IndirizzoTelematico.Text[0] = pProt.Destinatari.Amministrazione[0].EMAIL;

                    pSegnatura.Intestazione.Destinatario.Items = new Amministrazione[1];
                    pSegnatura.Intestazione.Destinatario.Items[0] = new Amministrazione();
                    if (!StringChecker.IsStringEmpty((pProt.Destinatari.Amministrazione[0]).AMMINISTRAZIONE))
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Denominazione = pProt.Destinatari.Amministrazione[0].AMMINISTRAZIONE;
                    else
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Denominazione = string.Empty;
                    ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).CodiceAmministrazione = _CodiceEnte;
                    ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).IndirizzoTelematico = new IndirizzoTelematico();
                    ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).IndirizzoTelematico.Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].EMAIL))
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).IndirizzoTelematico.Text[0] = pProt.Destinatari.Amministrazione[0].EMAIL;


                    if (!StringChecker.IsStringEmpty(pProt.Destinatari.Amministrazione[0].PROT_UO))
                    {
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items = new UnitaOrganizzativa[1];
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).ItemElementName = new ItemChoiceType[1];
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0] = new UnitaOrganizzativa();
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).ItemElementName[0] = ItemChoiceType.UnitaOrganizzativa;
                        ((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).id = pProt.Destinatari.Amministrazione[0].PROT_UO;
                    }
                    else
                    {
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items = new object[3];
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).ItemElementName = new ItemChoiceType[3];
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0] = new Persona();
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).ItemElementName[0] = ItemChoiceType.Persona;
                        //Occorre settare solo la proprietà Cognome per poter vedere il campo Corrispondente settato nel caso di una amministrazione esterna come destinatario
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).Cognome = pProt.Destinatari.Amministrazione[0].AMMINISTRAZIONE;
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).CodiceFiscale = pProt.Destinatari.Amministrazione[0].PARTITAIVA;
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).IndirizzoTelematico = new IndirizzoTelematico();
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).IndirizzoTelematico.Text = new string[1];
                        ((Persona)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).IndirizzoTelematico.Text[0] = pProt.Destinatari.Amministrazione[0].EMAIL;
                        if (string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].PARTITAIVA))
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).id = string.Empty;
                        else
                            ((Persona)((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[0]).id = pProt.Destinatari.Amministrazione[0].PARTITAIVA;

                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[1] = pProt.Destinatari.Amministrazione[0].TELEFONO1;
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).ItemElementName[1] = ItemChoiceType.Telefono;

                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).Items[2] = pProt.Destinatari.Amministrazione[0].FAX;
                        ((Amministrazione)pSegnatura.Intestazione.Destinatario.Items[0]).ItemElementName[2] = ItemChoiceType.Fax;
                    }

                    //Perchè nel caso che il destinatario sia un'amministrazione esterna ed una anagrafica devo prendere solo la prima
                    return;
                }

                //Verifico le anagrafiche
                if (pProt.Destinatari.Anagrafe.Count >= 1)
                {
                    pSegnatura.Intestazione.Destinatario.Telefono = new string[2];
                    pSegnatura.Intestazione.Destinatario.Telefono[0] = pProt.Destinatari.Anagrafe[0].TELEFONO;
                    pSegnatura.Intestazione.Destinatario.Telefono[1] = pProt.Destinatari.Anagrafe[0].TELEFONOCELLULARE;
                    pSegnatura.Intestazione.Destinatario.Fax = new string[1];
                    pSegnatura.Intestazione.Destinatario.Fax[0] = pProt.Destinatari.Anagrafe[0].FAX;
                    pSegnatura.Intestazione.Destinatario.IndirizzoTelematico = new IndirizzoTelematico();
                    pSegnatura.Intestazione.Destinatario.IndirizzoTelematico.Text = new string[1];
                    pSegnatura.Intestazione.Destinatario.IndirizzoTelematico.Text[0] = pProt.Destinatari.Anagrafe[0].EMAIL;

                    pSegnatura.Intestazione.Destinatario.Items = new Persona[1];
                    pSegnatura.Intestazione.Destinatario.Items[0] = new Persona();
                    ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).Nome = pProt.Destinatari.Anagrafe[0].NOME;
                    ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).Cognome = pProt.Destinatari.Anagrafe[0].NOMINATIVO;
                    if (!StringChecker.IsStringEmpty(pProt.Destinatari.Anagrafe[0].CODICEFISCALE))
                    {
                        ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).CodiceFiscale = pProt.Destinatari.Anagrafe[0].CODICEFISCALE;
                        ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).id = pProt.Destinatari.Anagrafe[0].CODICEFISCALE;
                    }
                    else
                    {
                        ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).CodiceFiscale = pProt.Destinatari.Anagrafe[0].PARTITAIVA;
                        ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).id = pProt.Destinatari.Anagrafe[0].PARTITAIVA;
                    }
                    ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).Titolo = pProt.Destinatari.Anagrafe[0].TITOLO;
                    ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).IndirizzoTelematico = new IndirizzoTelematico();
                    ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).IndirizzoTelematico.Text = new string[1];
                    ((Persona)pSegnatura.Intestazione.Destinatario.Items[0]).IndirizzoTelematico.Text[0] = pProt.Destinatari.Anagrafe[0].EMAIL;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEL DESTINATARIO DELLA SEGNATURA", ex);
            }
        }
        /// <summary>
        /// Metodo usato per realizzare la sezione Allegati
        /// </summary>
        /// <param name="pProt">Istanza della classe Protocollo</param>
        /// <param name="pSegnatura">Istanza della classe Segnatura</param>
        private void CreaSegnaturaAllegati(Data.DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            try
            {
                pSegnatura.Descrizione = new Descrizione();
                pSegnatura.Descrizione.Documento = new Documento();

                if (pProt.Allegati.Count >= 1)
                {
                    if (pProt.Allegati.Count > 1)
                        pSegnatura.Descrizione.Allegati = new Documento[pProt.Allegati.Count - 1];

                    for (int iCount = 0; iCount < pProt.Allegati.Count; iCount++)
                    {
                        if (iCount == 0)
                        {
                            //Setto il documento principale
                            bool bDescr = true;
                            int iPos = pProt.Allegati[iCount].Descrizione.LastIndexOf("." + pProt.Allegati[iCount].Extension);

                            if (iPos != -1)
                                pSegnatura.Descrizione.Documento.nome = pProt.Allegati[iCount].Descrizione;
                            else
                            {
                                pSegnatura.Descrizione.Documento.nome = pProt.Allegati[iCount].NOMEFILE;
                                iPos = pProt.Allegati[iCount].NOMEFILE.LastIndexOf("." + pProt.Allegati[iCount].Extension);
                                bDescr = false;
                            }

                            pSegnatura.Descrizione.Documento.id = pProt.Allegati[iCount].ID;
                            pSegnatura.Descrizione.Documento.DescrizioneDocumento = new DescrizioneDocumento();
                            pSegnatura.Descrizione.Documento.DescrizioneDocumento.Text = new string[1];
                            pSegnatura.Descrizione.Documento.DescrizioneDocumento.Text[0] = bDescr ? pProt.Allegati[iCount].Descrizione.Substring(0, iPos) : pProt.Allegati[iCount].NOMEFILE.Substring(0, iPos);
                            pSegnatura.Descrizione.Documento.TipoDocumento = new TipoDocumento();
                            pSegnatura.Descrizione.Documento.TipoDocumento.Text = new string[1];
                            pSegnatura.Descrizione.Documento.TipoDocumento.Text[0] = "Principale";
                        }
                        else
                        {
                            //Setto gli allegati del documento principale
                            pSegnatura.Descrizione.Allegati[iCount - 1] = new Documento();
                            //nuova versione
                            bool bDescr = true;
                            int iPos = pProt.Allegati[iCount].Descrizione.LastIndexOf("." + pProt.Allegati[iCount].Extension);

                            if (iPos != -1)
                                pSegnatura.Descrizione.Allegati[iCount - 1].nome = pProt.Allegati[iCount].Descrizione;
                            else
                            {
                                pSegnatura.Descrizione.Allegati[iCount - 1].nome = pProt.Allegati[iCount].NOMEFILE;
                                iPos = pProt.Allegati[iCount].NOMEFILE.LastIndexOf("." + pProt.Allegati[iCount].Extension);
                                bDescr = false;
                            }
                            //nuova versione

                            pSegnatura.Descrizione.Allegati[iCount - 1].id = pProt.Allegati[iCount].ID;
                            pSegnatura.Descrizione.Allegati[iCount - 1].DescrizioneDocumento = new DescrizioneDocumento();
                            pSegnatura.Descrizione.Allegati[iCount - 1].DescrizioneDocumento.Text = new string[1];
                            pSegnatura.Descrizione.Allegati[iCount - 1].DescrizioneDocumento.Text[0] = bDescr ? pProt.Allegati[iCount].Descrizione.Substring(0, iPos) : pProt.Allegati[iCount].NOMEFILE.Substring(0, iPos);
                            pSegnatura.Descrizione.Allegati[iCount - 1].TipoDocumento = new TipoDocumento();
                            pSegnatura.Descrizione.Allegati[iCount - 1].TipoDocumento.Text = new string[1];
                            pSegnatura.Descrizione.Allegati[iCount - 1].TipoDocumento.Text[0] = "Allegato";
                        }
                    }
                }
                else
                    pSegnatura.Descrizione.Documento.nome = string.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANE IL SETTAGGIO DEGLI ALLEGATI DELLA SEGNATURA", ex);
            }
        }

        private void CreaSegnaturaApplicativoProtocollo(Data.DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            try
            {
                pSegnatura.ApplicativoProtocollo = new ApplicativoProtocollo();
                pSegnatura.ApplicativoProtocollo.nome = _CodiceEnte;
                pSegnatura.ApplicativoProtocollo.Parametro = new Parametro[6];
                pSegnatura.ApplicativoProtocollo.Parametro[0] = new Parametro();
                pSegnatura.ApplicativoProtocollo.Parametro[0].nome = "TipoSpedizione";
                pSegnatura.ApplicativoProtocollo.Parametro[0].valore = pProt.TipoDocumento;


                pSegnatura.ApplicativoProtocollo.Parametro[1] = new Parametro();
                pSegnatura.ApplicativoProtocollo.Parametro[2] = new Parametro();
                pSegnatura.ApplicativoProtocollo.Parametro[3] = new Parametro();
                pSegnatura.ApplicativoProtocollo.Parametro[4] = new Parametro();
                pSegnatura.ApplicativoProtocollo.Parametro[5] = new Parametro();
                if (pProt.Flusso == "A" || pProt.Flusso == "I")
                {
                    pSegnatura.ApplicativoProtocollo.Parametro[1].nome = "MittenteIndirizzo";
                    pSegnatura.ApplicativoProtocollo.Parametro[2].nome = "MittenteLocalita";
                    pSegnatura.ApplicativoProtocollo.Parametro[3].nome = "MittenteProvincia";
                    pSegnatura.ApplicativoProtocollo.Parametro[4].nome = "MittenteCap";
                }
                else
                {
                    pSegnatura.ApplicativoProtocollo.Parametro[1].nome = "DestinatarioIndirizzo";
                    pSegnatura.ApplicativoProtocollo.Parametro[2].nome = "DestinatarioLocalita";
                    pSegnatura.ApplicativoProtocollo.Parametro[3].nome = "DestinatarioProvincia";
                    pSegnatura.ApplicativoProtocollo.Parametro[4].nome = "DestinatarioCap";
                }
                //Aggiungo il parametro OperatoreInserimento
                pSegnatura.ApplicativoProtocollo.Parametro[5].nome = "OperatoreInserimento";
                pSegnatura.ApplicativoProtocollo.Parametro[5].valore = Operatore;


                if (pProt.Flusso == "A" || pProt.Flusso == "I")
                {
                    if (pProt.Mittenti.Amministrazione.Count >= 1)
                    {
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Amministrazione[0].INDIRIZZO))
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = pProt.Mittenti.Amministrazione[0].INDIRIZZO;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Amministrazione[0].CITTA))
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = pProt.Mittenti.Amministrazione[0].CITTA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Amministrazione[0].PROVINCIA))
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = pProt.Mittenti.Amministrazione[0].PROVINCIA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Amministrazione[0].CAP))
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = pProt.Mittenti.Amministrazione[0].CAP;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = string.Empty;

                        return;
                    }

                    if (pProt.Mittenti.Anagrafe.Count >= 1)
                    {
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Anagrafe[0].INDIRIZZO))
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = pProt.Mittenti.Anagrafe[0].INDIRIZZO;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Anagrafe[0].CITTA))
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = pProt.Mittenti.Anagrafe[0].CITTA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Anagrafe[0].PROVINCIA))
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = pProt.Mittenti.Anagrafe[0].PROVINCIA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Mittenti.Anagrafe[0].CAP))
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = pProt.Mittenti.Anagrafe[0].CAP;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = string.Empty;
                    }
                }
                else
                {
                    if (pProt.Destinatari.Amministrazione.Count >= 1)
                    {
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Amministrazione[0].INDIRIZZO))
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = pProt.Destinatari.Amministrazione[0].INDIRIZZO;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Amministrazione[0].CITTA))
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = pProt.Destinatari.Amministrazione[0].CITTA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Amministrazione[0].PROVINCIA))
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = pProt.Destinatari.Amministrazione[0].PROVINCIA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Amministrazione[0].CAP))
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = pProt.Destinatari.Amministrazione[0].CAP;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = string.Empty;

                        return;
                    }

                    if (pProt.Destinatari.Anagrafe.Count >= 1)
                    {
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Anagrafe[0].INDIRIZZO))
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = pProt.Destinatari.Anagrafe[0].INDIRIZZO;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[1].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Anagrafe[0].CITTA))
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = pProt.Destinatari.Anagrafe[0].CITTA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[2].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Anagrafe[0].PROVINCIA))
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = pProt.Destinatari.Anagrafe[0].PROVINCIA;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[3].valore = string.Empty;
                        if (!StringChecker.IsStringEmpty(pProt.Destinatari.Anagrafe[0].CAP))
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = pProt.Destinatari.Anagrafe[0].CAP;
                        else
                            pSegnatura.ApplicativoProtocollo.Parametro[4].valore = string.Empty;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DELL'ELEMENTO APPLICATIVOPROTOCOLLO DELLA SEGNATURA", ex);
            }
        }
        #endregion

        #region Utility

        /// <summary>
        /// Metodo usato per leggere i parametri della verticalizzazione Protocollo Pindaro
        /// </summary>
        private void GetParametriFromVertPindaro()
        {
            try
            {
                VerticalizzazioneProtocolloPindaro protocolloPindaro;

                //string tSoftware = string.IsNullOrEmpty( Software ) ? GetIstanza().SOFTWARE : Software;

                protocolloPindaro = new VerticalizzazioneProtocolloPindaro(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (protocolloPindaro.Attiva)
                {

                    _protocolloLogs.DebugFormat(@"Valori parametri verticalizzazioni, 
                                            Url: {0},
                                            Operatore: {1},
                                            Password: {2},
                                            Codice Ente: {3}",
                    protocolloPindaro.Url,
                    protocolloPindaro.Operatore,
                    protocolloPindaro.Password,
                    protocolloPindaro.Codiceente);

                    _Operatore = protocolloPindaro.Operatore;
                    _Password = protocolloPindaro.Password;
                    _CodiceEnte = protocolloPindaro.Codiceente;
                    _Url = protocolloPindaro.Url;

                    _protocolloLogs.Debug("Fine recupero valori da verticalizzazioni");
                }
                else
                    throw new Exception("La verticalizzazione PROTOCOLLO_PINDARO non è attiva");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA LETTURA DELLA VERTICALIZZAZIONE PROTOCOLLO_PINDARO", ex);
            }
        }

        #endregion

        #region Metodo usato per settare la classe contenente le informazioni sulla protocollazione eseguita
        /// <summary>
        /// Metodo usato per realizzare la stringa in formato xml contenente le informazioni sulla protocollazione eseguita
        /// </summary>
        /// <param name="response">Istanza della classe restituita del web method Protocollazione</param>
        /// <returns>Stringa in formato xml contenente le informazioni sulla protocollazione eseguita</returns>
        private DatiProtocolloRes CreaDatiProtocollo(ProtocollazioneRet response)
        {
            try
            {
                var protoRes = new DatiProtocolloRes();
                
                protoRes.AnnoProtocollo = response.lngAnnoPG.ToString();
                _protocolloLogs.Debug("Data protocollazione " + response.DataPG);
                if (!String.IsNullOrEmpty(response.DataPG))
                    protoRes.DataProtocollo = response.DataPG.Substring(0, 10);

                protoRes.NumeroProtocollo = response.lngNumPG.ToString();

                if (ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + response.lngAnnoPG.ToString();

                _protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO", ex);
            }
        }

        #endregion

        #endregion
    }

}
namespace Init.SIGePro.SegnaturaPindaro
{
    #region Classi usate per realizzare il file segnatura.xml
    /// <remarks/>
    [XmlRoot(Namespace = "", IsNullable = false)]
    public class Segnatura
    {

        /// <remarks/>
        [XmlElement()]
        public Intestazione Intestazione;

        /// <remarks/>
        [XmlElement()]
        public Descrizione Descrizione;

        /// <remarks/>
        [XmlElement()]
        public ApplicativoProtocollo ApplicativoProtocollo;
    }

    /// <remarks/>
    public class Intestazione
    {
        /// <remarks/>
        [XmlElement()]
        public string Oggetto;

        /// <remarks/>
        [XmlElement()]
        public Identificatore Identificatore;

        /// <remarks/>
        [XmlElement()]
        public Mittente Mittente;

        /// <remarks/>
        [XmlElement()]
        public Destinatario Destinatario;

        /// <remarks/>
        [XmlElement()]
        public Classifica Classifica;

        /// <remarks/>
        [XmlElement()]
        public Fascicolo Fascicolo;
    }

    /// <remarks/>
    public class Identificatore
    {

        /// <remarks/>
        [XmlElement()]
        public string CodiceAmministrazione;

        /// <remarks/>
        [XmlElement()]
        public string CodiceAOO;

        /// <remarks/>
        [XmlElement()]
        public string NumeroRegistrazione;

        /// <remarks/>
        [XmlElement()]
        public string DataRegistrazione;

        /// <remarks/>
        [XmlElement()]
        public string Flusso;
    }

    /// <remarks/>
    public class Parametro
    {

        /// <remarks/>
        [XmlAttribute()]
        public string nome;

        /// <remarks/>
        [XmlAttribute()]
        public string valore;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class ApplicativoProtocollo
    {

        /// <remarks/>
        [XmlElement("Parametro")]
        public Parametro[] Parametro;

        /// <remarks/>
        [XmlAttribute()]
        public string nome;
    }

    /// <remarks/>
    public class TipoDocumento
    {

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class DescrizioneDocumento
    {

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Documento
    {

        /// <remarks/>
        [XmlElement()]
        public DescrizioneDocumento DescrizioneDocumento;

        /// <remarks/>
        [XmlElement()]
        public TipoDocumento TipoDocumento;

        /// <remarks/>
        [XmlAttribute()]
        public string nome;

        /// <remarks/>
        [XmlAttribute()]
        public long id;
    }

    /// <remarks/>
    public class Descrizione
    {

        /// <remarks/>
        [XmlElement()]
        public Documento Documento;

        /// <remarks/>
        [XmlArrayItem(IsNullable = false)]
        public Documento[] Allegati;
    }

    /// <remarks/>
    public class Fascicolo
    {

        /// <remarks/>
        [XmlAttribute()]
        public string numero;

        /// <remarks/>
        [XmlAttribute()]
        public string anno;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Classifica
    {

        /// <remarks/>
        [XmlElement()]
        public string CodiceAmministrazione;

        /// <remarks/>
        [XmlElement()]
        public string CodiceAOO;

        /// <remarks/>
        [XmlElement()]
        public string CodiceTitolario;
    }

    /// <remarks/>
    public class Destinatario
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        public object[] Items;

        /// <remarks/>
        [XmlElement()]
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [XmlElement("Telefono")]
        public string[] Telefono;

        /// <remarks/>
        [XmlElement("Fax")]
        public string[] Fax;

        /// <remarks/>
        [XmlElement()]
        public string IndirizzoPostale;
    }

    /// <remarks/>
    public class Amministrazione
    {

        /// <remarks/>
        [XmlElement()]
        public string Denominazione;

        /// <remarks/>
        [XmlElement()]
        public string CodiceAmministrazione;

        /// <remarks/>
        [XmlElement()]
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoPostale", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("UnitaOrganizzativa", typeof(UnitaOrganizzativa))]
        [System.Xml.Serialization.XmlElementAttribute("Fax", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("Telefono", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("Ruolo", typeof(string))]
        [System.Xml.Serialization.XmlChoiceIdentifierAttribute("ItemElementName")]
        public object[] Items;

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public ItemChoiceType[] ItemElementName;
    }


    /// <remarks/>
    [System.Xml.Serialization.XmlTypeAttribute(IncludeInSchema = false)]
    public enum ItemChoiceType
    {

        /// <remarks/>
        IndirizzoPostale,

        /// <remarks/>
        UnitaOrganizzativa,

        /// <remarks/>
        Fax,

        /// <remarks/>
        Persona,

        /// <remarks/>
        Telefono,

        /// <remarks/>
        Ruolo,
    }


    /// <remarks/>
    public class IndirizzoTelematico
    {

        /// <remarks/>
        [XmlAttribute(DataType = "NMTOKEN")]
        [DefaultValue("smtp")]
        public string tipo = "smtp";

        /// <remarks/>
        [XmlAttribute()]
        public string note;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class UnitaOrganizzativa
    {

        /// <remarks/>
        [XmlAttribute()]
        public string id;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Persona
    {

        /// <remarks/>
        [XmlElement()]
        public string Nome;

        /// <remarks/>
        [XmlElement()]
        public string Cognome;

        /// <remarks/>
        [XmlElement()]
        public string Titolo;

        /// <remarks/>
        [XmlElement()]
        public string CodiceFiscale;

        /// <remarks/>
        [XmlElement()]
        public string Identificativo;

        /// <remarks/>
        [XmlElement()]
        public string Denominazione;

        /// <remarks/>
        [XmlElement()]
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [XmlAttribute()]
        public string id;
    }



    /// <remarks/>
    public class AOO
    {

        /// <remarks/>
        [XmlElement()]
        public string CodiceAOO;

        /// <remarks/>
        [XmlElement()]
        public string Denominazione;
    }

    /// <remarks/>
    public class Mittente
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        public object[] Items;
    }

    /// <remarks/>
    public class Civico
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class CAP
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Comune
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;

        /// <remarks/>
        [XmlAttribute()]
        public string codiceISTAT;
    }

    /// <remarks/>
    public class Provincia
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Nazione
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Allegati
    {

        /// <remarks/>
        [XmlElement("Documento")]
        public Documento[] Documento;
    }
    #endregion
}







