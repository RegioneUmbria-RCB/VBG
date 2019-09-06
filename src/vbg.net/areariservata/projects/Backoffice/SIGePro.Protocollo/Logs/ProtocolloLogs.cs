using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using System.IO;
using System.Xml;
using System.Xml.Serialization;
using Init.SIGePro.Protocollo.Validation;
using System.Configuration;
using System.Web;
using Init.SIGePro.Protocollo.Manager;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs.Services;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.Logs
{
    /// <summary>
    /// Questa classe contiene solamente le costanti riferite ai nomi files delle richieste e risposte ai web service di protocollo
    /// che saranno loggati durante la serializzazione (vedi classe ProtocolloSerializer).
    /// </summary>
    public class ProtocolloLogsConstants
    {
        /// <summary>
        /// Richiesta inviata da Vbg Java.
        /// </summary>
        public const string RequestFileName = "SoapRequest.xml";
        /// <summary>
        /// E' una classe intermedia valorizzata con tutti i dati recuperati da VBG (verticalizzazioni....) che poi sarà adattata alla soap request del protocollo, viene serializzata sul file indicato nel valore di questa costante.
        /// </summary>
        public const string DatiProtocolloInFileName = "DatiProtocolloIn.xml";
        /// <summary>
        /// Richiesta inviata al web service di protocollazione per eseguire l'aggiornamento di un protocollo.
        /// </summary>
        public const string UpdateProtocolloRequestFileName = "UpdateProtocolloSoapRequest.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la richiesta di aggiornamento del protocollo.
        /// </summary>
        public const string UpdateProtocolloResponseFileName = "UpdateProtocolloSoapResponse.xml";
        /// <summary>
        /// Richiesta di aggiornamento del protocollo
        /// </summary>
        public const string AggiornaProtocolloRequestFileName = "AggiornaProtocolloSoapRequest.xml";
        /// <summary>
        /// Richiesta inviata al web service di protocollazione per eseguire la protocollazione.
        /// </summary>
        public const string ProtocollazioneRequestFileName = "ProtocollazioneSoapRequest.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la richiesta di esecuzione della protocollazione.
        /// </summary>
        public const string ProtocollazioneResponseFileName = "ProtocollazioneSoapResponse.xml";
        /// <summary>
        /// Risposta inviata a Vbg Java.
        /// </summary>
        public const string ResponseFileName = "SoapResponse.xml";
        /// <summary>
        /// Inserimento di una bozza di protocollo interno del protocollo apsystems
        /// </summary>
        public const string InserimentoBozzaResponseFileName = "InserimentoBozzaSoapResponse.xml";
        /// <summary>
        /// Invio di una bozza di protocollo interno del protocollo apsystems
        /// </summary>
        public const string InvioBozzaResponseFileName = "InvioBozzaSoapResponse.xml";
        /// <summary>
        /// Richiesta inviata al web service di protocollazione alla richiesta di lettura di un protocollo.
        /// </summary>
        public const string LeggiProtocolloRequestFileName = "LeggiProtocolloSoapRequest.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la richiesta di lettura di un protocollo.
        /// </summary>
        public const string LeggiProtocolloResponseFileName = "LeggiProtocolloSoapResponse.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la richiesta del crea copie.
        /// </summary>
        public const string CreaCopieRequestFileName = "CreaCopieSoapRequest.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la richiesta del crea copie.
        /// </summary>
        public const string CreaCopieResponseFileName = "CreaCopieSoapResponse.xml";
        /// <summary>
        /// Risposta inviata dal web service dopo la chiamata per la ricerca di un gruppo in docer.
        /// </summary>
        public const string GetGruppiDocErResponse = "GetGruppiSoapResponse.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la valorizzazione dei dati da restituire a VBG.
        /// </summary>
        public const string CreaCopieReturnFileName = "CreaCopieSoapReturn.xml";
        /// <summary>
        /// Richiesta inviata al web service di protocollazione per eseguire l'inserimento di un documento (metodo InsertDocumento del ws di Iride), la classe da serializzare è la stessa della costante ProtocollazioneRequestFileName.
        /// </summary>
        public const string InserisciDocumentoRequestFileName = "InserisciDocumentoSoapRequest.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo l'esecuzione dell'inserimento di un documento (metodo InsertDocumento del ws di Iride), la classe da serializzare è la stessa della costante ProtocollazioneResponseFileName.
        /// </summary>
        public const string InserisciDocumentoResponseFileName = "InserisciDocumentoSoapResponse.xml";
        /// <summary>
        /// Xml di risposta alla richiesta di visualizzazione di un allegato.
        /// </summary>
        public const string AllegatoResponseFileName = "AllegatoSoapResponse.xml";
        /// <summary>
        /// Xml request alla richiesta di inserimento di un allegato.
        /// </summary>
        public const string LeggiRelatedDocumentsResponse = "LeggiDocumentiAllegati.xml";
        /// <summary>
        /// Xml request alla richiesta di inserimento di un allegato.
        /// </summary>
        public const string AllegatoRequestFileName = "AllegatoSoapRequest.xml";
        /// <summary>
        /// Richiesta inviata al web service di fascicolazione.
        /// </summary>
        public const string ListaFascicoliResponseFileName = "ListaFascicoliSoapResponse.xml";
        /// <summary>
        /// Richiesta inviata al web service di fascicolazione.
        /// </summary>
        public const string CreaFascicoloRequestFileName = "CreaFascicoloSoapRequest.xml";
        /// <summary>
        /// Risposta ottenuta dal web service di fascicolazione.
        /// </summary>
        public const string CreaFascicoloResponseFileName = "CreaFascicoloSoapResponse.xml";
        /// <summary>
        /// Risposta ottenuta dal web service di fascicolazione dopo una richiesta di lettura di un fascicolo.
        /// </summary>
        public const string LeggiFascicoloResponseFileName = "LeggiFascicoloSoapResponse.xml";
        /// <summary>
        /// Richiesta inviata al web service di riprotocollazione dopo una richiesta di apertura pratica.
        /// </summary>
        public const string RiprotocollazioneRequestFileName = "RiprotocollazioneSoapRequest.xml";
        /// <summary>
        /// Risposta ottenuta dal web service di riprotocollazione dopo una richiesta di apertura pratica.
        /// </summary>
        public const string RiprotocollazioneResponseFileName = "RiprotocollazioneSoapResponse.xml";
        /// <summary>
        /// Richiesta inviata da vbg java per la fascicolazione.
        /// </summary>
        public const string FascicolazioneSoapRequestFileName = "FascicolazioneSoapRequest.xml";
        /// <summary>
        /// Richiesta inviata al web service di verifica abilitazione utente di fascicolazione
        /// </summary>
        public const string VerificaAbilitazioniFascicolazioneRequestFileName = "VerificaAbilitazioniFascicolazioneSegnaturaRequest.xml";
        /// <summary>
        /// Richiesta di interrogazione dei fascicoli
        /// </summary>
        public const string InterrogaFascicoliRequestFileName = "InterrogaFascicoliRequest.xml";
        /// <summary>
        /// Risposta da interrogazione dei fascicoli
        /// </summary>
        public const string InterrogaFascicoliResponseFileName = "InterrogaFascicoliResponse.xml";
        /// <summary>
        /// Richiesta inviata al web service di fascicolazione
        /// </summary>
        public const string FascicolazioneRequestFileName = "FascicolazioneSegnaturaRequest.xml";
        /// <summary>
        /// Risposta restituita dal web service di fascicolazione
        /// </summary>
        public const string FascicolazioneResponseFileName = "FascicolazioneResponse.xml";
        /// <summary>
        /// Risposta ottenuta dal web service di fascicolazione.
        /// </summary>
        public const string FascicolazioneSoapResponseFileName = "FascicolazioneSoapResponse.xml";
        /// <summary>
        /// Richiesta inviata al web service per l'annullamento del protocollo.
        /// </summary>
        public const string AnnullaProtocolloSoapRequestFileName = "AnnullaProtocolloSoapRequest.xml";
        /// <summary>
        /// Risposta ottenuta dal web service per l'annullamento del protocollo.
        /// </summary>
        public const string AnnullaProtocolloSoapResponseFileName = "AnnullaProtocolloSoapResponse.xml";
        /// <summary>
        /// Richiesta inviata al web service Java per ottenere Mail e testo tipo.
        /// </summary>
        public const string MailTipoProtocolloSoapRequestFileName = "MailTipoSoapRequest.xml";
        /// <summary>
        /// Risposta ottenuta dal web service Java per ottenere Mail e testo tipo.
        /// </summary>
        public const string MailTipoProtocolloSoapResponseFileName = "MailTipoSoapResponse.xml";
        /// <summary>
        /// Segnatura della request da inviare al servizio di pec del protocollo per inviare una pec.
        /// </summary>
        public const string SegnaturaPecRequestFileName = "SegnaturaPecRequest.xml";
        /// <summary>
        /// Segnatura della request da inviare al servizio di pec del protocollo per inviare una pec.
        /// </summary>
        public const string SegnaturaPecResponseFileName = "SegnaturaPecResponse.xml";
        /// <summary>
        /// Request dei ruoli operatore Docer per il set del documento
        /// </summary>
        public const string RuoliMetadatiRequestSetAclDocument = "RuoliMetadatiSetAclDocumentRequest.xml";
        /// <summary>
        /// Request dei ruoli operatore Docer per il set del fascicolo
        /// </summary>
        public const string RuoliMetadatiRequestSetAclFascicolo = "RuoliMetadatiSetAclFascicoloRequest.xml";
        /// <summary>
        /// Risposta ottenuta dal web service per ottenere la lista di tipi documenti.
        /// </summary>
        public const string TipiDocumentoSoapResponseFileName = "TipiDocumentoSoapResponse.xml";
        /// <summary>
        /// Nome del file xml segnatura di test per verificare la validazione.
        /// </summary>
        public const string SegnaturaXmlTestFileName = "SegnaturaTest.xml";
        /// <summary>
        /// Nome del file xml segnatura utilizzato dai protocolli che richiedono questo tipo di dato, ad esempio DocArea.
        /// </summary>
        public const string SegnaturaXmlFileName = "Segnatura.xml";
        /// <summary>
        /// Nome del file xml segnatura riguardante la protocollazione utilizzato dal protocollo Sigedo.
        /// </summary>
        public const string SegnaturaProtocollazioneXmlFileName = "SegnaturaProtocollazione.xml";
        /// <summary>
        /// Nome del file xml segnatura riguardante la presa in carico utilizzato dal protocollo Sigedo.
        /// </summary>
        public const string SegnaturaPresaIncaricoXmlFileName = "SegnaturaPresaInCarico.xml";
        /// <summary>
        /// Nome del file xml segnatura riguardante l'eseguito utilizzato dal protocollo Sigedo.
        /// </summary>
        public const string SegnaturaEseguitoXmlFileName = "SegnaturaEseguito.xml";
        /// <summary>
        /// Nome dello schema xsd utilizzato dai protocolli che richiedono questo tipo di dato, ad esempio DocArea.
        /// </summary>
        public const string SegnaturaXsdFileName = "Segnatura.xsd";
        /// <summary>
        /// Nome del file xml segnatura fittizia utilizzato dai protocolli che richiedono questo tipo di dato, ad esempio DocArea.
        /// </summary>
        [Obsolete("Usare proprietà ProfileFileName")]
        public const string SegnaturaFittiziaXmlFileName = "SegnaturaAllegata.xml";
        /// <summary>
        /// Usato da DocsPa, è la risposta alla richiesta della scheda di documento.
        /// </summary>
        public const string SchedaDocSoapResponseFileName = "SchedaDocumentoSoapResponse.xml";
        /// <summary>
        /// Usato da DocsPa, è il registro indicato nela risposta alla richiesta della scheda di documento.
        /// </summary>
        public const string RegistroSoapResponseFileName = "RegistroSoapResponse.xml";
        /// <summary>
        /// Usato da DocsPa, è il registro indicato nela risposta alla richiesta della scheda di documento.
        /// </summary>
        public const string ModelloTrasmissioneSoapResponseFileName = "ModelloTrasmissioneSoapResponse.xml";
        /// <summary>
        /// Serializzazione della classe ProtocolloMittDest
        /// </summary>
        public const string MittentiDestinatariDbRequestFileName = "MittentiDestinatariDbRequest.xml";
        /// <summary>
        /// Serializzazione della classe ProtocolloVista (SIDOP)
        /// </summary>
        public const string VistaDbRequestFileName = "VistaDbRequest.xml";
        /// <summary>
        /// Serializzazione della classe ProtocolloOutStoredProcedure (SIDOP)
        /// </summary>
        public const string ProtocolloOutputDbRequestFileName = "ProtocolloStoredProcedureDbResponse.xml";
        /// <summary>
        /// Metadati inviati al gestore documentale di DocEr relativi al documento principale.
        /// </summary>
        public const string MetadatiDocsRequestPrimarioFileName = "MetadatiDocsRequestPrimario.xml";
        /// <summary>
        /// Metadati inviati al gestore documentale di DocEr relativi al documento principale.
        /// </summary>
        public const string MetadatiDocsRequestAllegatiFileName = "MetadatiDocsRequestAllegati.xml";
        /// <summary>
        /// Nome dello schema xsd utilizzato dai protocolli DocEr che richiedono questo tipo di dato..
        /// </summary>
        public const string SegnaturaDocErXsdFileName = "SegnaturaDocEr.xsd";
        /// <summary>
        /// Indica il file di segnatura smistamento action per la presa in carico di Sigedo.
        /// </summary>
        public const string SegnaturaSmistamentoFileName = "SegnaturaSmistamento.xml";
        /// <summary>
        /// Nome del file xml segnatura fittizia utilizzato dai protocolli che richiedono questo tipo di dato, ad esempio DocArea, Sigedo, DocPro....
        /// </summary>
        public const string ProfileFileName = "Profile.xml";
        /// <summary>
        /// Nome del file di richiesta alla chiamata ad un web method che restituisca il titolario (classifiche)....
        /// </summary>
        public const string ListaClassificheRequest = "ListaClassificheRequest.xml";
        /// <summary>
        /// Nome del file di risposta alla chiamata ad un web method che restituisca il titolario (classifiche)....
        /// </summary>
        public const string ListaClassificheResponse = "ListaClassificheResponse.xml";
        /// <summary>
        /// Nome del file di richiesta alla chiamata ad un web method che effettui l'assegnazione del protocollo....
        /// </summary>
        public const string AssegnazioneRequest = "AssegnazioneRequest.xml";
        /// <summary>
        /// Nome del file di risposta alla chiamata ad un web method che effettui l'assegnazione del protocollo....
        /// </summary>
        public const string AssegnazioneResponse = "AssegnazioneResponse.xml";
        /// <summary>
        /// Request inviata alla request del metodo metti alla firma.
        /// </summary>
        public const string MettiAllaFirmaSoapRequest = "MettiAllaFirmaSoapRequest.xml";
        /// <summary>
        /// Response restituita dal metodo metti alla firma.
        /// </summary>
        public const string MettiAllaFirmaSoapResponse = "MettiAllaFirmaSoapResponse.xml";
        /// <summary>
        /// Metadati inviati al servizio PaDoc per la protocollazione.
        /// </summary>
        public const string MetadatiProtocolloInputFileName = "MetadatiProtocolloInput.xml";
        /// <summary>
        /// Richiesta per la lettura delle anagrafiche
        /// </summary>
        public const string LeggiAnagraficheRequest = "LeggiAnagraficheRequest.xml";
        /// <summary>
        /// Risposta per la lettura delle anagrafiche
        /// </summary>
        public const string LeggiAnagraficheResponse = "LeggiAnagraficheResponse.xml";
        /// <summary>
        /// Risposta per la lettura delle anagrafiche
        /// </summary>
        public const string LeggiAllegatoRequest = "LeggiAllegatoRequest.xml";
        /// <summary>
        /// Richiesta per l'inserimento di un'anagrafica
        /// </summary>
        public const string InsertAnagraficaRequest = "InsertAnagraficaRequest.xml";
        /// <summary>
        /// Risposta dopo l'inserimento di un'anagrafica
        /// </summary>
        public const string InsertAnagraficaResponse = "InsertAnagraficaResponse.xml";
        /// <summary>
        /// Richiesta per l'aggiornamento di un'anagrafica
        /// </summary>
        public const string UpdateAnagraficaRequest = "UpdateAnagraficaRequest.xml";
        /// <summary>
        /// Risposta dopo l'aggiornamento di un'anagrafica
        /// </summary>
        public const string UpdateAnagraficaResponse = "UpdateAnagraficaResponse.xml";
        /// <summary>
        /// Destinatari Aggiuntivi (DataManagement)
        /// </summary>
        public const string DestinatariAggiuntivi = "SegnaturaDestinatariAggiuntivi.xml";
        /// <summary>
        /// PiuInfo
        /// </summary>
        public const string PiuInfo = "PiuInfo.xml";
        /// <summary>
        /// CollegaDocumentoIn, usato su J-Iride per il collegamento del documento
        /// </summary>
        public const string CollegaDocumentoRequest = "CollegaDocumentoRequest.xml";
        /// <summary>
        /// Request relativo all'inserimento di un nuovo documento nella creazione della copia
        /// </summary>
        public const string InserisciDocumentoCopiaRequest = "InserisciDocumentoCopiaRequest.xml";
        /// <summary>
        /// Request relativo alla modifica di un fascicolo in un protocollo
        /// </summary>
        public const string CambiaFascicoloRequest = "CambiaFascicoloRequest.xml";
    }

    public class ProtocolloLogs : ILog
    {
        #region Membri privati

        ILog _log;
        ILogPathResolverService _logPathResolverService;

        private string _folder = String.Empty;

        /*private string IdComune { get { return _datiProtocollazione.IdComune; } }
        private string CodiceIstanza { get { return _datiProtocollazione.CodiceIstanza; } }
        private string CodiceMovimento { get { return _datiProtocollazione.CodiceMovimento; } }
        private string Software { get { return _datiProtocollazione.Software; } }
        private string CodiceOperatore { get { return _datiProtocollazione.CodiceOperatore; } }*/

        private string DatiIstanza
        {
            get
            {
                return String.Format("Codice Istanza: {0}, Codice Movimento: {1}, IdComune: {2}, Software: {3}, Operatore: {4}, Cartella Temp: {5} ",
                    _datiProtocollazione.CodiceIstanza,
                    _datiProtocollazione.CodiceMovimento,
                    _datiProtocollazione.IdComune,
                    _datiProtocollazione.Software,
                    _datiProtocollazione.CodiceResponsabile.GetValueOrDefault(-1),
                    Folder);
            }
        }

        private ResolveDatiProtocollazioneService _datiProtocollazione;

        #endregion

        #region Proprietà

        public ProtocolloWarnings Warnings
        {
            get;
            set;
        }

        public string Folder
        {
            get { return _logPathResolverService.LogPath; }
        }

        #endregion

        #region Costruttori

        /*public ProtocolloLogs(ProtocolloBase protocolloBase)
        {
            _log = LogManager.GetLogger(protocolloBase.GetType());

            Warnings = new ProtocolloWarnings();

            _resolveDatiIstanzaService = new ResolveDatiIstanzaProtocolloBaseService(protocolloBase);
            _logPathResolverService = new LogPathFromHttpContextResolverService(this._resolveDatiIstanzaService);
        }*/

        internal ProtocolloLogs(ResolveDatiProtocollazioneService datiProtocollazione, Type tipo)
        {
            _log = LogManager.GetLogger(tipo);
            _datiProtocollazione = datiProtocollazione;
            Warnings = new ProtocolloWarnings();
            _logPathResolverService = new LogPathFromHttpContextResolverService(datiProtocollazione);
        }


        /*public ProtocolloLogs(ProtocolloMgr protocolloMgr)
        {
            _log = LogManager.GetLogger(protocolloMgr.GetType());

            Warnings = new ProtocolloWarnings();

            _resolveDatiIstanzaService = new ResolveDatiIstanzaProtocolloMgrService(protocolloMgr);
            _logPathResolverService = new LogPathFromHttpContextResolverService(this._resolveDatiIstanzaService);
        }*/

        #endregion

        #region Metodi

        public void DeleteFolder()
        {
            //DebugFormat("ELIMINAZIONE CARTELLA {0}", Folder);
        }

        /// <summary>
        /// Logga l'errore con log4net e ritorna l'eccezione da sollevare.
        /// </summary>
        /// <param name="errorMessage">Messaggio di errore</param>
        /// <param name="ex">Eccezione del blocco catch</param>
        /// <returns>Eccezione da sollevare.</returns>
        public Exception LogErrorException(string errorMessage, Exception ex)
        {
            Error(errorMessage, ex);
            return ex;
        }

        #endregion

        #region ILog Members

        public void Debug(object message, Exception exception)
        {
            _log.Debug(message, exception);
        }

        public void Debug(object message)
        {
            _log.Debug(String.Concat(DatiIstanza, message));
        }

        public void DebugFormat(IFormatProvider provider, string format, params object[] args)
        {
            _log.DebugFormat(provider, String.Concat(DatiIstanza, format), args);
        }

        public void DebugFormat(string format, object arg0, object arg1, object arg2)
        {
            _log.DebugFormat(String.Concat(DatiIstanza, format), arg0, arg1, arg2);
        }

        public void DebugFormat(string format, object arg0, object arg1)
        {
            _log.DebugFormat(String.Concat(DatiIstanza, format), arg0, arg1);
        }

        public void DebugFormat(string format, object arg0)
        {
            _log.DebugFormat(String.Concat(DatiIstanza, format), arg0);
        }

        public void DebugFormat(string format, params object[] args)
        {
            _log.DebugFormat(String.Concat(DatiIstanza, format), args);
        }

        public void Error(object message, Exception exception)
        {
            _log.Error(String.Concat(DatiIstanza, message), exception);
        }

        public void Error(object message)
        {
            _log.Error(String.Concat(DatiIstanza, message));
        }

        public void ErrorFormat(IFormatProvider provider, string format, params object[] args)
        {
            _log.ErrorFormat(provider, String.Concat(DatiIstanza, format), args);
        }

        public void ErrorFormat(string format, object arg0, object arg1, object arg2)
        {
            _log.ErrorFormat(String.Concat(DatiIstanza, format), arg0, arg1, arg2);
        }

        public void ErrorFormat(string format, object arg0, object arg1)
        {
            _log.ErrorFormat(String.Concat(DatiIstanza, format), arg0, arg1);
        }

        public void ErrorFormat(string format, object arg0)
        {
            _log.ErrorFormat(String.Concat(DatiIstanza, format), arg0);
        }

        public void ErrorFormat(string format, params object[] args)
        {
            _log.ErrorFormat(String.Concat(DatiIstanza, format), args);
        }

        public void Fatal(object message, Exception exception)
        {
            _log.Fatal(String.Concat(DatiIstanza, message), exception);
        }

        public void Fatal(object message)
        {
            _log.Fatal(String.Concat(DatiIstanza, message));
        }

        public void FatalFormat(IFormatProvider provider, string format, params object[] args)
        {
            _log.FatalFormat(provider, String.Concat(DatiIstanza, format), args);
        }

        public void FatalFormat(string format, object arg0, object arg1, object arg2)
        {
            _log.FatalFormat(String.Concat(DatiIstanza, format), arg0, arg1, arg2);
        }

        public void FatalFormat(string format, object arg0, object arg1)
        {
            _log.FatalFormat(String.Concat(DatiIstanza, format), arg0, arg1);
        }

        public void FatalFormat(string format, object arg0)
        {
            _log.FatalFormat(String.Concat(DatiIstanza, format), arg0);
        }

        public void FatalFormat(string format, params object[] args)
        {
            _log.FatalFormat(String.Concat(DatiIstanza, format), args);
        }

        public void Info(object message, Exception exception)
        {
            _log.Info(String.Concat(DatiIstanza, message), exception);
        }

        public void Info(object message)
        {
            _log.Info(String.Concat(DatiIstanza, message));
        }

        public void InfoFormat(IFormatProvider provider, string format, params object[] args)
        {
            _log.InfoFormat(provider, String.Concat(DatiIstanza, format), args);
        }

        public void InfoFormat(string format, object arg0, object arg1, object arg2)
        {
            _log.InfoFormat(String.Concat(DatiIstanza, format), arg0, arg1, arg2);
        }

        public void InfoFormat(string format, object arg0, object arg1)
        {
            _log.InfoFormat(String.Concat(DatiIstanza, format), arg0, arg1);
        }

        public void InfoFormat(string format, object arg0)
        {
            _log.InfoFormat(String.Concat(DatiIstanza, format), arg0);
        }

        public void InfoFormat(string format, params object[] args)
        {
            _log.InfoFormat(String.Concat(DatiIstanza, format), args);
        }

        public bool IsDebugEnabled
        {
            get { return _log.IsDebugEnabled; }
        }

        public bool IsErrorEnabled
        {
            get { return _log.IsErrorEnabled; }
        }

        public bool IsFatalEnabled
        {
            get { return _log.IsFatalEnabled; }
        }

        public bool IsInfoEnabled
        {
            get { return _log.IsInfoEnabled; }
        }

        public bool IsWarnEnabled
        {
            get { return _log.IsWarnEnabled; }
        }

        public void Warn(object message, Exception exception)
        {
            this.Warnings.Add(String.Concat(message.ToString(), exception));
            _log.Warn(String.Concat(DatiIstanza, message), exception);
        }

        public void Warn(object message)
        {
            this.Warnings.Add(message.ToString());
            _log.Warn(String.Concat(DatiIstanza, message));
        }

        public void WarnFormat(IFormatProvider provider, string format, params object[] args)
        {
            this.Warnings.Add(String.Format(format, args));
            _log.WarnFormat(provider, String.Concat(DatiIstanza, format), args);
        }

        public void WarnFormat(string format, object arg0, object arg1, object arg2)
        {
            this.Warnings.Add(String.Format(format, arg0, arg1, arg2));
            _log.WarnFormat(String.Concat(DatiIstanza, format), arg0, arg1, arg2);
        }

        public void WarnFormat(string format, object arg0, object arg1)
        {
            this.Warnings.Add(String.Format(format, arg0, arg1));
            _log.WarnFormat(String.Concat(DatiIstanza, format), arg0, arg1);
        }

        public void WarnFormat(string format, object arg0)
        {
            this.Warnings.Add(String.Format(format, arg0));
            _log.WarnFormat(String.Concat(DatiIstanza, format), arg0);
        }

        public void WarnFormat(string format, params object[] args)
        {
            this.Warnings.Add(String.Format(format, args));
            _log.WarnFormat(String.Concat(DatiIstanza, format), args);
        }

        #endregion

        #region ILoggerWrapper Members

        public log4net.Core.ILogger Logger
        {
            get { return _log.Logger; }
        }

        #endregion
    }
}
