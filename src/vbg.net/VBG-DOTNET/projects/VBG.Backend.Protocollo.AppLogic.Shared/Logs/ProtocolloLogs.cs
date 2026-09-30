using log4net;
using log4net.Appender;
using log4net.Layout;
using log4net.Repository.Hierarchy;
using System.Configuration;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs
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
        /// Richiesta inviata al web service di protocollazione alla richiesta di lettura di un protocollo senza il binario degli allegati.
        /// </summary>
        public const string LeggiDocumentoPlusRequestFileName = "LeggiDocumentoPlusRequest.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la richiesta di lettura di un protocollo senza il binario degli allegati.
        /// </summary>
        public const string LeggiDocumentoPlusResponseFileName = "LeggiDocumentoPlusResponse.xml";
        /// <summary>
        /// Risposta inviata dal web service di protocollazione dopo la richiesta di lettura di un protocollo.
        /// </summary>
        public const string LeggiProtocolloStoricoResponseFileName = "LeggiProtocolloStoricoSoapResponse.xml";

        /// <summary>
        /// Richiesta inviata al web service di protocollazione alla richiesta di lettura di un protocollo.
        /// </summary>
        public const string LeggiProtocolloStoricoRequestFileName = "LeggiProtocolloStoricoSoapRequest.xml";

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
        public const string ListaProtocolliRequestFileName = "ListaProtocolliRequest.xml";
        /// <summary>
        /// Richiesta inviata al web service di fascicolazione.
        /// </summary>
        public const string ListaProtocolliResponseFileName = "ListaProtocolliSoapResponse.xml";
        /// <summary>
        /// Richiesta inviata al web service di fascicolazione.
        /// </summary>
        public const string ListaFascicoliRequestFileName = "ListaFascicoliRequest.xml";
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
        public const string LeggiFascicoloRequestFileName = "LeggiFascicoloRequestFileName.xml";
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
        /// Risposta per la lettura delle anagrafiche
        /// </summary>
        public const string LeggiAllegatoStoricoRequest = "LeggiAllegatoStoricoRequest.xml";
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
        /// <summary>
        /// Request relativo alla login
        /// </summary>
        public const string LoginRequest = "LoginRequest.xml";
        /// <summary>
        /// Response relativo alla login
        /// </summary>
        public const string LoginResponse = "LoginResponse.xml";
    }



    public class ProtocolloLogs : ILog
    {
        private readonly ILog _log;
        private readonly string _logFileName;
        private readonly ILogPathResolverService _externalLogPathResolverService;
        private readonly ProtocolloLogsOptions _logOptions;
        private readonly ResolveDatiProtocollazioneService _datiProtocollazione;
        private static readonly List<string> _loggersName = new List<string>();
        private static readonly object _lock = new object();

        public ProtocolloWarnings Warnings { get; set; }

        public string Folder => this._externalLogPathResolverService.LogPath;

        public log4net.Core.ILogger Logger => this._log.Logger;

        public bool IsDebugEnabled => this._log.IsDebugEnabled;
        public bool IsErrorEnabled => this._log.IsErrorEnabled;
        public bool IsFatalEnabled => this._log.IsFatalEnabled;
        public bool IsInfoEnabled => this._log.IsInfoEnabled;
        public bool IsWarnEnabled => this._log.IsWarnEnabled;

        public ProtocolloLogs(ResolveDatiProtocollazioneService datiProtocollazione, Type tipo, ILogPathResolverService logPathResolverService, ProtocolloLogsOptions logOptions)
        {
            this._datiProtocollazione = datiProtocollazione;
            this._externalLogPathResolverService = logPathResolverService;
            this._logOptions = logOptions;
            this.Warnings = new ProtocolloWarnings();

            // Usa un identificativo dinamico (segregando i log per CodiceComune)
            var dynamicId = _datiProtocollazione?.CodiceComune ?? "Default";
            this._logFileName = $"Protocollo_{dynamicId}.log.txt";

            this._log = this.CreateDynamicLogger(dynamicId, this._logFileName);
        }

        public void DeleteTempFolder()
        {
                try
                {
                    if (_logOptions.MantieniLog == "1")
                    {
                        _log.Debug($"cartella {Folder} NON eliminata per l'impostazione di appsettings.json o Web.Config");
                        return;
                    }

                    this._externalLogPathResolverService.EliminaLogPath();
                    _log.Debug($"cartella {Folder} eliminata");
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore durante l'eliminazione della cartella {this.Folder}: {ex.Message}", ex);
                }
        }

        private ILog CreateDynamicLogger(string loggerName, string logFilePath)
        {
            // valori di default che poi vengono sovrascritti da quelli di _logOptions (presi da appsettings.json)
            var maxSizeRollBackups = 20;
            var maximumFileSize = "10MB";
            var conversionPattern = "%date [%thread] %-5level %logger - %message%newline";

            var minLevelSetting =
                _logOptions.MinLevel
                ?? "INFO";

            var minLog4NetLevel = Common.ResolveLogLevel(minLevelSetting);


            if (!int.TryParse(_logOptions.MaxSizeRollBackups, out maxSizeRollBackups))
            {
                maxSizeRollBackups = 20; // Valore di default
            }

            if (!string.IsNullOrEmpty(_logOptions.MaximumFileSize))
            {
                maximumFileSize = _logOptions.MaximumFileSize;
            }

            if (!string.IsNullOrEmpty(_logOptions.ConversionPattern))
            {
                conversionPattern = _logOptions.ConversionPattern;
            }


            var layout = new PatternLayout
            {
                ConversionPattern = conversionPattern
            };

            layout.ActivateOptions();

            var appender = new RollingFileAppender
            {
                Name = $"Appender_{loggerName}",
                File = Path.Combine("Logs", logFilePath),
                AppendToFile = true,
                RollingStyle = RollingFileAppender.RollingMode.Size,
                MaxSizeRollBackups = maxSizeRollBackups,
                MaximumFileSize = maximumFileSize,
                StaticLogFileName = true,
                Layout = layout,
                LockingModel = new FileAppender.MinimalLock(),
                Threshold = minLog4NetLevel
            };

            appender.ActivateOptions();

            if (_loggersName.Contains(loggerName))
            {
                // Se il logger esiste già, non lo ricreare
                return LogManager.GetLogger(loggerName);
            }

            lock (_lock)
            {
                if (_loggersName.Contains(loggerName))
                {
                    // Se il logger esiste già, non lo ricreare
                    return LogManager.GetLogger(loggerName);
                }

                var hierarchy = (Hierarchy)LogManager.GetRepository();
                var logger = (log4net.Repository.Hierarchy.Logger)hierarchy.GetLogger(loggerName);

                logger.AddAppender(appender);
                logger.Level = minLog4NetLevel;
                logger.Repository.Configured = true;
                logger.Additivity = false;

                _loggersName.Add(loggerName);

                return LogManager.GetLogger(loggerName);
            }
        }

        private string Concat(object message)
        {
            return $"{this.Folder} {message}";
        }

        // ⬇️ Tutti i metodi di ILog restano uguali, ma ora usano _log interno

        public void Debug(object message) => this._log.Debug(this.Concat(message));
        public void Debug(object message, Exception exception) => this._log.Debug(this.Concat(message), exception);

        public void Error(object message) => this._log.Error(this.Concat(message));
        public void Error(object message, Exception exception) => this._log.Error(this.Concat(message), exception);

        public void Fatal(object message) => this._log.Fatal(this.Concat(message));
        public void Fatal(object message, Exception exception) => this._log.Fatal(this.Concat(message), exception);

        public void Info(object message) => this._log.Info(this.Concat(message));
        public void Info(object message, Exception exception) => this._log.Info(this.Concat(message), exception);

        public void Warn(object message)
        {
            this.Warnings.Add(String.Concat(this.Folder, message));
            this._log.Warn(this.Concat(message));
        }

        public void Warn(object message, Exception exception)
        {
            this.Warnings.Add(String.Concat(this.Folder, message));
            this._log.Warn(this.Concat(message), exception);
        }


        public Exception LogErrorException(string errorMessage, Exception ex)
        {
            this.Error(errorMessage, ex);
            return ex;
        }

        public void DebugFormat(IFormatProvider provider, string format, params object[] args)
        {
            this._log.DebugFormat(provider, this.Concat(format), args);
        }

        public void DebugFormat(string format, object arg0, object arg1, object arg2)
        {
            this._log.DebugFormat(this.Concat(format), arg0, arg1, arg2);
        }

        public void DebugFormat(string format, object arg0, object arg1)
        {
            this._log.DebugFormat(this.Concat(format), arg0, arg1);
        }

        public void DebugFormat(string format, object arg0)
        {
            this._log.DebugFormat(this.Concat(format), arg0);
        }

        public void DebugFormat(string format, params object[] args)
        {
            this._log.DebugFormat(this.Concat(format), args);
        }

        public void ErrorFormat(IFormatProvider provider, string format, params object[] args)
        {
            this._log.ErrorFormat(provider, this.Concat(format), args);
        }

        public void ErrorFormat(string format, object arg0, object arg1, object arg2)
        {
            this._log.ErrorFormat(this.Concat(format), arg0, arg1, arg2);
        }

        public void ErrorFormat(string format, object arg0, object arg1)
        {
            this._log.ErrorFormat(this.Concat(format), arg0, arg1);
        }

        public void ErrorFormat(string format, object arg0)
        {
            this._log.ErrorFormat(this.Concat(format), arg0);
        }

        public void ErrorFormat(string format, params object[] args)
        {
            this._log.ErrorFormat(this.Concat(format), args);
        }

        public void FatalFormat(IFormatProvider provider, string format, params object[] args)
        {
            this._log.FatalFormat(provider, this.Concat(format), args);
        }

        public void FatalFormat(string format, object arg0, object arg1, object arg2)
        {
            this._log.FatalFormat(this.Concat(format), arg0, arg1, arg2);
        }

        public void FatalFormat(string format, object arg0, object arg1)
        {
            this._log.FatalFormat(this.Concat(format), arg0, arg1);
        }

        public void FatalFormat(string format, object arg0)
        {
            this._log.FatalFormat(this.Concat(format), arg0);
        }

        public void FatalFormat(string format, params object[] args)
        {
            this._log.FatalFormat(this.Concat(format), args);
        }


        public void InfoFormat(IFormatProvider provider, string format, params object[] args)
        {
            this._log.InfoFormat(provider, this.Concat(format), args);
        }

        public void InfoFormat(string format, object arg0, object arg1, object arg2)
        {
            this._log.InfoFormat(this.Concat(format), arg0, arg1, arg2);
        }

        public void InfoFormat(string format, object arg0, object arg1)
        {
            this._log.InfoFormat(this.Concat(format), arg0, arg1);
        }

        public void InfoFormat(string format, object arg0)
        {
            this._log.InfoFormat(this.Concat(format), arg0);
        }

        public void InfoFormat(string format, params object[] args)
        {
            this._log.InfoFormat(this.Concat(format), args);
        }



        public void WarnFormat(IFormatProvider provider, string format, params object[] args)
        {
            this.Warnings.Add(String.Format(format, args));
            this._log.WarnFormat(provider, this.Concat(format), args);
        }

        public void WarnFormat(string format, object arg0, object arg1, object arg2)
        {
            this.Warnings.Add(String.Format(format, arg0, arg1, arg2));
            this._log.WarnFormat(this.Concat(format), arg0, arg1, arg2);
        }

        public void WarnFormat(string format, object arg0, object arg1)
        {
            this.Warnings.Add(String.Format(format, arg0, arg1));
            this._log.WarnFormat(this.Concat(format), arg0, arg1);
        }

        public void WarnFormat(string format, object arg0)
        {
            this.Warnings.Add(String.Format(format, arg0));
            this._log.WarnFormat(this.Concat(format), arg0);
        }

        public void WarnFormat(string format, params object[] args)
        {
            this.Warnings.Add(String.Format(format, args));
            this._log.WarnFormat(this.Concat(format), args);
        }
    }
}
