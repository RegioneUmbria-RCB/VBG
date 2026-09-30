using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.FileConverter;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Services.MailTipo;
using VBG.Backend.Protocollo.AppLogic.Shared.Services.OperatoreProtocollo;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public static class Constants
    {
        public const string CHIAVE_TIPODOCUMENTO = "TIPO_DOCUMENTO";
        public const string RIEPILOGO_DOMANDA = "RiepilogoDomanda";

    }
    public partial class ProtocolloMgr : IProtocolloMgr, IDisposable
    {
        private AuthenticationInfo _authInfo;
        private ResolveDatiProtocollazioneService _datiProtocollazione;
        private ProtocolloLogs _log;
        private ProtocolloSerializer _protocolloSerializer;
        private DatiProtocolloIn _protoIn;
        private Fascicolo _fascicolo;
        private DatiRequestType _richiestaDiProtocollazione;
        private VerticalizzazioneProtocolloAttivo _verticalizzazioneProtocolloAttivo;

        // Istanza dell'oggetto protocollo corrente creata via reflection
        private ProtocolloBase _protocolloAttivo;

        private readonly List<string> _estensioniNonConvertibili = new List<string> { ".PDF", ".P7M", ".PDF.P7M" };
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        // Istanza dell'oggetto protocollo da utilizzare per legere i dati di protocollazione storici
        private readonly IProtocolloStorico _protocolloStorico;
        private readonly IBindingFactory _bindingFactory;
        private ProtocollazioneRepository _protocollazioneRepository;

        public ProtocolloMgr(IVerticalizzazioniFactory verticalizzazioniFactory, IProtocolloStorico protocolloStorico, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._protocolloStorico = protocolloStorico;
            this._bindingFactory = bindingFactory;
        }

        public void Initialize(AuthenticationInfo authInfo, string software, string codiceComune = "", AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze? istanza = null, Movimenti? movimento = null, PecInbox? datiPec = null)
        {
            if (String.IsNullOrEmpty(software))
                throw new ArgumentNullException(nameof(software));

            if (authInfo == null)
                throw new ArgumentNullException(nameof(authInfo));

            if (String.IsNullOrEmpty(authInfo.IdComune))
                throw new ArgumentNullException("authInfo.IdComune");

            this._authInfo = authInfo;

            var dataBase = authInfo.CreateDatabase();

            var idComune = authInfo.IdComune;
            var idComuneAlias = authInfo.Alias;
            var codOperatore = authInfo.CodiceResponsabile;
            var token = authInfo.Token;



            this._datiProtocollazione = new ResolveDatiProtocollazioneService(idComune, idComuneAlias, software, dataBase, istanza, movimento, codOperatore, ambito, token, codiceComune, datiPec);

            var factory = ProtocolloFactoryProvider.Factory
                ?? throw new InvalidOperationException("ProtocolloFactory non inizializzata");

            this._log = factory.CreateLogs(this._datiProtocollazione, this.GetType());
            this._protocolloSerializer = factory.CreateSerializer(this._log);

            this._protocollazioneRepository = new ProtocollazioneRepository(dataBase, idComune, software, codiceComune, this._log);

            this.InizializzaVerticalizzazioneProtocolloAttivo(idComuneAlias, software, codiceComune);

            this.InizializzaOperatoreSuVerticalizzazioneProtocolloAttivo();

            var attivazioneProtoService = new AttivazioneProtocolloService(this._log, this._verticalizzazioniFactory, this._bindingFactory);
            this._protocolloAttivo = attivazioneProtoService.AttivaProtocollo(this._verticalizzazioneProtocolloAttivo, this._datiProtocollazione);

            this._protocolloAttivo.TempPath = factory.GetTempFolder();
            this._protocolloAttivo.ProxyAddress = this._verticalizzazioneProtocolloAttivo.ProxyAddress;
        }

        private void InizializzaVerticalizzazioneProtocolloAttivo(string idComuneAlias, string software, string codiceComune)
        {
            this._verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(idComuneAlias, software, codiceComune);
        }


        private void InizializzaOperatoreSuVerticalizzazioneProtocolloAttivo()
        {
            this._log.DebugFormat("CODICE OPERATORE VERTICALIZZAZIONE: {0}, CODICE OPERATORE FO: {1}, CODICE OPERATORE PASSATO: {2}", this._verticalizzazioneProtocolloAttivo.Operatore, this._verticalizzazioneProtocolloAttivo.Codoperatorefo, this._datiProtocollazione.CodiceResponsabileUtenteLoggato);

            if (!this._datiProtocollazione.CodiceResponsabileUtenteLoggato.HasValue)
            {
                this._log.Debug("PROTOCOLLAZIONE DA ONLINE");

                var operatoreOnline = new OperatoreProtocolloOnline(this._verticalizzazioneProtocolloAttivo.Codoperatorefo, this._datiProtocollazione.Istanza);
                this._datiProtocollazione.CodiceResponsabileUtenteLoggato = Convert.ToInt32(operatoreOnline.CodiceOperatore);
                this._log.DebugFormat("CODICE OPERATORE: {0}", this._datiProtocollazione.CodiceResponsabileUtenteLoggato.Value.ToString());
            }

            var operatoreProto = OperatoreProtocolloFactory.Create(this._datiProtocollazione.Db, this._verticalizzazioneProtocolloAttivo.Operatore, this._datiProtocollazione.CodiceResponsabileUtenteLoggato, this._datiProtocollazione.IdComune);
            this._verticalizzazioneProtocolloAttivo.Operatore = operatoreProto.CodiceOperatore;

            this._log.DebugFormat("OPERATORE EFFETTIVO CON CUI EFFETTUARE LA PROTOCOLLAZIONE: {0}", this._verticalizzazioneProtocolloAttivo.Operatore);
        }


        private void SetProtocollo(TipoProvenienza provenienza, Source tipoInserimento)
        {
            this._protoIn = new DatiProtocolloIn();


            this.ValorizzaAmbito();
            this.ValorizzaDatiProtocolloAttivo(provenienza, tipoInserimento);
            this.ValorizzaTipoDocumento(tipoInserimento);
            this.ValorizzaTipoSmistamento();
            this.ValorizzaOggettoECorpoMail();
            this.ValorizzaOggettoProtocollo();
            this.ValorizzaFlussoProtocollo();

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA && (this._protoIn.Flusso == "P"))
            {
                throw new ProtocolloException("NON È POSSIBILE PROTOCOLLARE UN'ISTANZA CON UN FLUSSO IN USCITA!");
            }

            this.ValorizzaClassifica();
            this.ValorizzaNumeroEDataProtocolloMitt();
            this.SetAllegati(tipoInserimento);
            this.SetMittenti();
            this.SetDestinatari();

            this.ValorizzaDataRicezioneSpedizioneProtocolloAttivo();

            this.ValorizzaMetadati();

            this._log.Debug("Fine settaggio della classe DatiProtocolloIn");
        }

        private void ValorizzaDataRicezioneSpedizioneProtocolloAttivo()
        {
            if (this._verticalizzazioneProtocolloAttivo.ValorizDataRicSpediz)
            {
                if (this._datiProtocollazione.Movimento != null)
                {
                    this._protocolloAttivo.ValorizzaDataRicezioneSpedizione = (this._datiProtocollazione.Movimento.CREATO_DA_STC == 1);
                }
                else
                {
                    this._protocolloAttivo.ValorizzaDataRicezioneSpedizione = (this._datiProtocollazione.Istanza != null);
                }
            }
        }

        private void ValorizzaMetadati()
        {
            if (this._richiestaDiProtocollazione.Metadati?.Any() ?? false)
            {
                this._protoIn.AggiungiMetadati(this._richiestaDiProtocollazione.Metadati);
            }
        }

        private void ValorizzaNumeroEDataProtocolloMitt()
        {
            if (!String.IsNullOrEmpty(this._richiestaDiProtocollazione.NumProtMitt))
            {
                this._protoIn.NumProtMitt = this._richiestaDiProtocollazione.NumProtMitt;
            }

            if (!String.IsNullOrEmpty(this._richiestaDiProtocollazione.DataProtMitt))
            {
                this._protoIn.DataProtMitt = this._richiestaDiProtocollazione.DataProtMitt;
            }
        }

        /// <summary>
        /// Utilizza l'ambito di protocolloazione per settare la data registrazione
        /// </summary>
        private void ValorizzaAmbito()
        {
            var datiProtoAmbito = AmbitoFactory.Create(this._datiProtocollazione);

            this._log.DebugFormat("La variabile datiProtoAmbito è stata valorizzata. L'ambito è {0}", this._datiProtocollazione.TipoAmbito);

            this._protoIn.DataRegistrazione = datiProtoAmbito.DataRegistrazione;
        }

        private void ValorizzaClassifica()
        {
            try
            {
                this._log.DebugFormat("Recupero dei dati relativi alla classifica: {0}", this._richiestaDiProtocollazione.Classifica);
                var classifica = this._richiestaDiProtocollazione.Classifica;

                if (!String.IsNullOrEmpty(classifica))
                {
                    this._protoIn.Classifica = classifica;
                    return;
                }

                if (this._datiProtocollazione.Istanza != null)
                {
                    this._protoIn.Classifica = this._protocollazioneRepository.RepositoryGetClassificaProtocolloByCodiceIntervento(this._datiProtocollazione.CodiceInterventoProc.Value);

                    if (String.IsNullOrEmpty(this._protoIn.Classifica))
                    {
                        this._protoIn.Classifica = this._verticalizzazioneProtocolloAttivo.ClassificadefaultBo;
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.Error("ERRORE DURANTE LA VALORIZZAZIONE DELLA CLASSIFICA", ex);

                throw;
            }
            finally
            {
                this._log.DebugFormat("Fine recupero dei dati relativi alla classifica: {0}", this._protoIn.Classifica);
            }

            if (String.IsNullOrEmpty(this._protoIn.Classifica))
            {
                throw new Exception("NON È STATA SETTATA LA CLASSIFICA DI PROTOCOLLAZIONE NELL'ALBERO DEI PROCEDIMENTI");
            }
        }

        private void ValorizzaFlussoProtocollo()
        {
            try
            {
                this._log.DebugFormat("Valorizzazione dei dati relativi al flusso del protocollo");

                if (!String.IsNullOrEmpty(this._richiestaDiProtocollazione.Flusso))
                {
                    this._protoIn.Flusso = this._richiestaDiProtocollazione.Flusso;
                    return;
                }

                if (this._datiProtocollazione.Movimento != null && this._richiestaDiProtocollazione.Mittenti == null)
                {
                    if (this._datiProtocollazione.Movimento.CREATO_DA_STC == 1)
                    {
                        this._protoIn.Flusso = "A";
                    }

                    if (!String.IsNullOrEmpty(this._datiProtocollazione.Movimento.TIPOMOVIMENTO))
                    {
                        var tipiMovimento = this._protocollazioneRepository.RepositoryGetTipoMovimentoById(this._datiProtocollazione.Movimento.TIPOMOVIMENTO);

                        if (tipiMovimento?.FLAG_STC == 1)
                        {
                            this._log.Info($"VERIFICA DELLA PRESENZA DEL PROTOCOLLO NELL'ISTANZA, MOVIMENTO: {tipiMovimento.Tipomovimento}, MOVIMENTO IMPOSTATO NEI PARAMETRI DELLA REGOLA PROTOCOLLO_ATTIVO: {this._verticalizzazioneProtocolloAttivo.TipoMovRicevuta}");
                            if (this._verticalizzazioneProtocolloAttivo.TipoMovRicevuta == tipiMovimento.Tipomovimento && this._datiProtocollazione.Istanza != null && String.IsNullOrEmpty(this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO))
                            {
                                throw new ProtocolloException("NON E' POSSIBILE PROTOCOLLARE LA RICEVUTA IN QUANTO L'ISTANZA NON E' STATA PROTOCOLLATA");
                            }

                            this._log.InfoFormat("ESTRAPOLAZIONE DEI TIPI MOVIMENTI STC MAPPING RELATIVAMENTE AL TIPO MOVIMENTO {0}", tipiMovimento.Tipomovimento);
                            var mappingProtocollo = this._protocollazioneRepository.RepositoryGetDatiProtocolloByTipoMovimento(tipiMovimento.Tipomovimento);

                            if (mappingProtocollo != null)
                            {
                                this._log.InfoFormat("MAPPING RESTITUITO, FLUSSO: {0}, TIPODOCUMENTO: {1}, CODICE MAIL TIPO PER l'OGGETTO: {2}", mappingProtocollo.Flusso, mappingProtocollo.TipoDocumento, mappingProtocollo.OggettoMailTipo);

                                this._protoIn.Flusso = mappingProtocollo.Flusso; ///IN GENERE IMPOSTATO A "P" (PARTENZA), IN TEORIA POTREBBE ESSERE ANCHE INTERNO.
                                this._protoIn.TipoDocumento = mappingProtocollo.TipoDocumento;

                                if (mappingProtocollo.OggettoMailTipo.HasValue)
                                {
                                    this._log.Info("RECUPERO DELLA MAIL TIPO DA INSERIRE NELL'OGGETTO");
                                    var param = new ParametriInterventoProtocolloProtocolloService(mappingProtocollo.OggettoMailTipo.Value);

                                    var wsMailTipo = new MailTipoMovimentoService(param, this._datiProtocollazione, this._protocolloSerializer, this._bindingFactory, this._log);
                                    var testoTipo = wsMailTipo.GetMailTipo();

                                    this._log.InfoFormat("MAIL TIPO {0}: OGGETTO [{1}] CORPO [{2}]", mappingProtocollo.OggettoMailTipo.Value, testoTipo.Oggetto, testoTipo.Corpo);

                                    this._protoIn.Mail.Oggetto = String.IsNullOrEmpty(testoTipo.Oggetto) ? this._protoIn.Mail.Oggetto : testoTipo.Oggetto;
                                    this._protoIn.Mail.Corpo = String.IsNullOrEmpty(testoTipo.Corpo) ? this._protoIn.Mail.Corpo : testoTipo.Corpo;
                                }

                                if (mappingProtocollo.AmministrazioneMittente.HasValue)
                                {
                                    var amministrazione = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(mappingProtocollo.AmministrazioneMittente.Value);

                                    amministrazione.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                                    amministrazione.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                                    this._richiestaDiProtocollazione.Mittenti = new DatiMittentiXmlType
                                    {
                                        Amministrazione = [amministrazione]
                                    };
                                }
                            }
                        }
                    }
                }

                if (String.IsNullOrEmpty(this._protoIn.Flusso))
                {
                    if (String.IsNullOrEmpty(this._verticalizzazioneProtocolloAttivo.Flussodefault))
                    {
                        throw new ProtocolloException("LA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO NON HA SETTATO IL CAMPO FLUSSODEFAULT");
                    }

                    this._protoIn.Flusso = this._verticalizzazioneProtocolloAttivo.Flussodefault;

                    if (this._richiestaDiProtocollazione.Mittenti?.Amministrazione?.Any() ?? false)
                    {
                        var amministrazione = this._richiestaDiProtocollazione.Mittenti.Amministrazione.First();

                        if (!String.IsNullOrEmpty(amministrazione.PROT_UO))
                        {
                            this._protoIn.Flusso = ProtocolloConstants.COD_INTERNO;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.Error("ERRORE DURANTE LA VALORIZZAZIONE DEL FLUSSO DEL PROTOCOLLO", ex);
                throw;
            }
            finally
            {
                this._log.Debug("Fine valorizzazione dei dati relativi al flusso del protocollo");
            }
        }

        private void ValorizzaOggettoProtocollo()
        {
            this._log.DebugFormat("Valorizzazione dei dati relativi all'oggetto del protocollo, valore oggetto: {0}", this._richiestaDiProtocollazione.Oggetto);
            this._protoIn.Oggetto = this._richiestaDiProtocollazione.Oggetto;

            // Se l'oggetto del protocollo non è stato valorizzato, lo prendo dall'oggetto della mail
            if (string.IsNullOrEmpty(this._protoIn.Oggetto))
            {
                this._protoIn.Oggetto = this._protoIn.Mail.Oggetto;
            }

            if (this._verticalizzazioneProtocolloAttivo.OggettoUppercase == "1")
            {
                this._protoIn.Oggetto = this._protoIn.Oggetto.ToUpper();
            }

            if (!String.IsNullOrEmpty(this._verticalizzazioneProtocolloAttivo.NumCaratteriOggetto))
            {
                var parseOggetto = Int32.TryParse(this._verticalizzazioneProtocolloAttivo.NumCaratteriOggetto, out var resultParse);

                if (parseOggetto && this._protoIn.Oggetto.Length > resultParse)
                    throw new Exception("LA LUNGHEZZA DELL'OGGETTO SUPERA LA QUOTA MASSIMA CONSENTITA DAL PARAMETRO NUM_CARATTERI_OGGETTO DELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO");
            }

            this._log.Debug("Fine valorizzazione dei dati relativi all'oggetto del protocollo");
        }

        private void ValorizzaOggettoECorpoMail()
        {
            this._log.Debug("Inizio ValorizzaOggettoECorpoMail");

            // Se specificati Oggetto e Corpo della mail nella chiamata, vengono utilizzati direttamente.
            // Altrimenti il corpo si recupera sempre in base al MailTipo mentre l'oggetto mail si prova ad impostare prima con l'oggetto del protocollo e successivamente con il MailTipo
            if (!string.IsNullOrEmpty(this._richiestaDiProtocollazione.Mail?.Oggetto) && !string.IsNullOrEmpty(this._richiestaDiProtocollazione.Mail?.Corpo))
            {
                this._protoIn.Mail.Oggetto = this._richiestaDiProtocollazione.Mail!.Oggetto;
                this._protoIn.Mail.Corpo = this._richiestaDiProtocollazione.Mail!.Corpo;

                return;
            }

            var mail = this.GetOggettoECorpoByMailTipo(this._datiProtocollazione, this._log, this._protocolloSerializer, false);

            this._protoIn.Mail.Oggetto = String.IsNullOrEmpty(this._richiestaDiProtocollazione.Oggetto) ? mail.oggetto : this._richiestaDiProtocollazione.Oggetto;
            this._protoIn.Mail.Corpo = mail.corpo;
        }

        private void ValorizzaTipoSmistamento()
        {
            this._log.DebugFormat("Tipo Smistamento: {0}, parametro TipoSmistamentoDefault della verticalizzazione Protocollo_Attivo: {1}", this._richiestaDiProtocollazione.TipoSmistamento, this._verticalizzazioneProtocolloAttivo.Tiposmistamentodefault);
            this._protoIn.TipoSmistamento = !String.IsNullOrEmpty(this._richiestaDiProtocollazione.TipoSmistamento) ? this._richiestaDiProtocollazione.TipoSmistamento : this._verticalizzazioneProtocolloAttivo.Tiposmistamentodefault;
        }

        private void ValorizzaTipoDocumento(Source tipoInserimento)
        {
            try
            {
                this._log.DebugFormat("Valorizzazione dei dati relativi al TipoDocumento, valore TipoDocumento: {0}", this._richiestaDiProtocollazione.TipoDocumento);

                if (!String.IsNullOrEmpty(this._richiestaDiProtocollazione.TipoDocumento))
                {
                    this._protoIn.TipoDocumento = this._richiestaDiProtocollazione.TipoDocumento;
                    return;
                }

                var tipoDocumento = "";

                // Rimuovere se si vuole isolare il servizio di protocollazione
                if (this._datiProtocollazione.Istanza != null)
                {
                    tipoDocumento = this._protocollazioneRepository.RepositoryGetCodiceTipoDocumentoFromAlberoProcProtocollo(this._datiProtocollazione.CodiceInterventoProc.Value);
                }

                if (!String.IsNullOrEmpty(tipoDocumento))
                {
                    this._protoIn.TipoDocumento = tipoDocumento;
                    return;
                }

                var tipoDocumentoDefault = tipoInserimento == Source.ON_LINE ? this._verticalizzazioneProtocolloAttivo.Tipodocumentodefault : this._verticalizzazioneProtocolloAttivo.Tipodocumentodefaultbo;

                if (!String.IsNullOrEmpty(tipoDocumentoDefault))
                {
                    this._protoIn.TipoDocumento = tipoDocumentoDefault;
                }
                else
                {
                    this._log.Warn("TIPO DOCUMENTO DEFAULT BACKOFFICE NON VALORIZZATO");
                }
            }
            catch (Exception ex)
            {
                this._log.Error("ERRORE DURANTE LA VALORIZZAZIONE DEI DATI RELATIVI AL TIPO DOCUMENTO", ex);
                throw;
            }
            finally
            {
                this._log.Debug("Fine valorizzazione dei dati relativi al TipoDocumento");
            }
        }

        private void ValorizzaDatiProtocolloAttivo(TipoProvenienza provenienza, Source tipoInserimento)
        {
            this._log.Debug("Valorizzazione dei dati di base relativi alla classe ProtocolloBase");

            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
            this._protocolloAttivo.Provenienza = provenienza;
            this._protocolloAttivo.AggiungiAnno = this.GetParamVertBool(this._verticalizzazioneProtocolloAttivo.Aggiungianno, "AGGIUNGIANNO");
            this._protocolloAttivo.GestisciFascicolazione = this.GetParamVertBool(this._verticalizzazioneProtocolloAttivo.GestisciFascicolazione, "GESTISCIFASCICOLAZIONE");
            this._protocolloAttivo.TipoInserimento = tipoInserimento;
            this._protocolloAttivo.EncodingCharset = this._verticalizzazioneProtocolloAttivo.Encoding;
        }

        private void SetMettiAllaFirma()
        {
            this._protoIn = new DatiProtocolloIn();

            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
            this._protocolloAttivo.Provenienza = TipoProvenienza.BACKOFFICE;

            this.ValorizzaTipoDocumentoMettiAllaFirma();

            this.ValorizzaOggettoECorpoMailMettiAllaFirma();

            this.ValorizzaOggettoMettiAllaFirma();

            this.ValorizzaFlussoMettiAllaFirma();

            this.ValorizzaClassificaMettiAllaFirma();

            this.ValorizzaNumeroEDataProtocolloMittMettiAllaFirma();

            this.SetAllegati();

            this.SetMittenti();

            this.SetDestinatari();
        }

        private void ValorizzaOggettoMettiAllaFirma()
        {
            this._protoIn.Oggetto = this._richiestaDiProtocollazione.Oggetto;

            // Se l'oggetto del protocollo non è stato valorizzato, lo prendo dall'oggetto della mail
            if (string.IsNullOrEmpty(this._protoIn.Oggetto))
            {
                this._protoIn.Oggetto = this._protoIn.Mail.Oggetto;
            }

            if (this._verticalizzazioneProtocolloAttivo.OggettoUppercase == "1")
            {
                this._protoIn.Oggetto = this._protoIn.Oggetto.ToUpper();
            }
        }

        private void ValorizzaNumeroEDataProtocolloMittMettiAllaFirma()
        {
            if (!String.IsNullOrEmpty(this._richiestaDiProtocollazione.NumProtMitt))
            {
                this._protoIn.NumProtMitt = this._richiestaDiProtocollazione.NumProtMitt;
            }

            if (!String.IsNullOrEmpty(this._richiestaDiProtocollazione.DataProtMitt))
            {
                this._protoIn.DataProtMitt = this._richiestaDiProtocollazione.DataProtMitt;
            }
        }

        private void ValorizzaClassificaMettiAllaFirma()
        {
            if (!String.IsNullOrEmpty(this._richiestaDiProtocollazione.Classifica))
            {
                this._protoIn.Classifica = this._richiestaDiProtocollazione.Classifica;
            }
            else
            {
                this._protoIn.Classifica = this._protocollazioneRepository.RepositoryGetClassificaProtocolloByCodiceIntervento(this._datiProtocollazione.CodiceInterventoProc.Value);
            }

            if (String.IsNullOrEmpty(this._protoIn.Classifica))
            {
                throw new ProtocolloException("NON È STATA SETTATA LA CLASSIFICA DI PROTOCOLLAZIONE NELL'ALBERO DEI PROCEDIMENTI");
            }
        }

        private void ValorizzaFlussoMettiAllaFirma()
        {
            if (!string.IsNullOrEmpty(this._richiestaDiProtocollazione.Flusso))
            {
                this._protoIn.Flusso = this._richiestaDiProtocollazione.Flusso;

                return;
            }

            if (string.IsNullOrEmpty(this._verticalizzazioneProtocolloAttivo.Flussodefault))
            {
                throw new ProtocolloException("La verticalizzazione Protocollo_Attivo non ha settato il campo FlussoDefault");
            }

            this._protoIn.Flusso = this._verticalizzazioneProtocolloAttivo.Flussodefault;
        }

        private void ValorizzaOggettoECorpoMailMettiAllaFirma()
        {
            // Se specificati Oggetto e Corpo della mail nella chiamata, vengono utilizzati direttamente.
            // Altrimenti il corpo si recupera sempre in base al MailTipo mentre l'oggetto mail si prova ad impostare prima con l'oggetto del protocollo e successivamente con il MailTipo
            if (!string.IsNullOrEmpty(this._richiestaDiProtocollazione.Mail?.Oggetto) && !string.IsNullOrEmpty(this._richiestaDiProtocollazione.Mail?.Corpo))
            {
                this._protoIn.Mail.Oggetto = this._richiestaDiProtocollazione.Mail.Oggetto;
                this._protoIn.Mail.Corpo = this._richiestaDiProtocollazione.Mail.Corpo;
            }
            else
            {
                var mail = this.GetOggettoECorpoByMailTipo(this._datiProtocollazione, this._log, this._protocolloSerializer, false);

                this._protoIn.Mail.Oggetto = String.IsNullOrEmpty(this._richiestaDiProtocollazione.Oggetto) ? mail.oggetto : this._richiestaDiProtocollazione.Oggetto;
                this._protoIn.Mail.Corpo = mail.corpo;
            }
        }

        private void ValorizzaTipoDocumentoMettiAllaFirma()
        {
            if (!string.IsNullOrEmpty(this._richiestaDiProtocollazione.TipoDocumento))
            {
                this._protoIn.TipoDocumento = this._richiestaDiProtocollazione.TipoDocumento;
            }
            else
            {
                var tipoDocumentoBo = this._protocollazioneRepository.RepositoryGetCodiceTipoDocumentoFromAlberoProcProtocollo(this._datiProtocollazione.CodiceInterventoProc.Value);

                if (!String.IsNullOrEmpty(tipoDocumentoBo))
                {
                    this._protoIn.TipoDocumento = tipoDocumentoBo;
                }
                else
                {
                    if (String.IsNullOrEmpty(this._verticalizzazioneProtocolloAttivo.Tipodocumentodefaultbo))
                    {
                        throw new ProtocolloException("LA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO NON HA SETTATO IL CAMPO TIPODOCUMENTODEFAULTBO");
                    }

                    this._protoIn.TipoDocumento = this._verticalizzazioneProtocolloAttivo.Tipodocumentodefaultbo;
                }
            }
        }

        private List<ProtocolloAllegati> LeggiAllegatiDaNlaPec()
        {
            var nlaPec = new ProtocolloMgr.NlaPec(this._datiProtocollazione.Token, this._datiProtocollazione.Software, this._datiProtocollazione.IdComune);
            var identificativo = nlaPec.GetIdentificativo(Convert.ToInt32(this._datiProtocollazione.CodiceIstanza), this._datiProtocollazione.Db);

            if (!String.IsNullOrEmpty(identificativo))
            {
                return nlaPec.GetAllegatiEml(identificativo, DateTime.Now, DateTime.Now);
            }

            return [];
        }


        private void SetAllegati(Source tipoInserimento = Source.NONPROTOCOLLARE)
        {
            try
            {
                this._log.Debug("Recupero dei dati relativi agli allegati");

                this.AggiungiAllegatiDaNLAPecAProtoIn();
                this.AggiungiAllegatiDaIstanzaOMovimentoARichiestaDiProtocollazione(tipoInserimento);

                this._log.DebugFormat("NUMERO ALLEGATI INSERITI: {0}", this._richiestaDiProtocollazione.Allegati?.Length ?? -1);

                this._richiestaDiProtocollazione.Allegati ??= [];

                // Aggiunge gli allegati della rihiesta di protocollazione alla classe ProtoIn
                foreach (var allegato in this._richiestaDiProtocollazione.Allegati)
                {
                    this._log.DebugFormat("CHIAMATA AL WEB SERVICE CHE RESTITUISCE GLI OGGETTI, CODICE ALLEGATO: {0}", allegato.Cod);

                    var codiceOggetto = Convert.ToInt32(allegato.Cod);
                    var oggetto = this._protocollazioneRepository.RepositoryGetOggettoById(codiceOggetto);
                    var percorso = this._protocollazioneRepository.RepositoryGetPercorsoFile(codiceOggetto);

                    if (oggetto == null)
                    {
                        throw new ProtocolloException($"IL CODICEOGGETTO {allegato.Cod} PER L'IDCOMUNE {this._datiProtocollazione.IdComune} NON È PRESENTE NELLA TABELLA OGGETTI");
                    }

                    var nomeAllegato = new NomeFileAllegato(this._datiProtocollazione.IdComune, this._datiProtocollazione.CodiceComune, oggetto, allegato.Descrizione, this._verticalizzazioneProtocolloAttivo.NomeFileMaxLength);

                    var protoAllegati = new ProtocolloAllegati
                    {
                        MimeType = this._protocollazioneRepository.RepositoryGetContentType(oggetto),
                        Extension = nomeAllegato.GetEstensione(),
                        NOMEFILE = nomeAllegato.GetNomeCompleto(this._verticalizzazioneProtocolloAttivo.NomefileOrigine, this._protoIn.RecuperaAllegati().ToList(), this._verticalizzazioneProtocolloAttivo.CreaCopiaFile),
                        Descrizione = nomeAllegato.GetDescrizioneFileCopia(allegato.Descrizione, this._protoIn.RecuperaAllegati().ToList(), this._verticalizzazioneProtocolloAttivo.CreaCopiaDescrFile, 0, this._verticalizzazioneProtocolloAttivo.DescrFileMaxLength),
                        CODICEOGGETTO = oggetto.CODICEOGGETTO,
                        IDCOMUNE = oggetto.IDCOMUNE,
                        OGGETTO = oggetto.OGGETTO,
                        Percorso = percorso,
                        InviaTramitePec = allegato.InviaTramitePec ?? true
                    };

                    protoAllegati.RimuoviCaratteriNonValidiDaNomeFile(this._verticalizzazioneProtocolloAttivo.ListaCaratteriDaEliminare);

                    this.AggiungiAllegato(protoAllegati);
                }

                this.AggiungiAllegatoDomandaSTC();

                this.ImpostaRiepilogoComeAllegatoPrincipale();

                if (this._verticalizzazioneProtocolloAttivo.AllegatiObbligatori && !this._protoIn.HaAllegati())
                {
                    throw new InvalidOperationException("E' OBBLIGATORIA LA PRESENZA DI ALMENO UN DOCUMENTO");
                }
            }
            catch (Exception ex)
            {
                this._log.Error("ERRORE GENERATO DURANTE LA VALORIZZAZIONE DEGLI ALLEGATI", ex);

                throw;
            }
            finally
            {
                this._log.Debug("Fine valorizzazione dei dati relativi agli allegati");
            }
        }

        private void AggiungiAllegatiDaIstanzaOMovimentoARichiestaDiProtocollazione(Source tipoInserimento)
        {
            if (this._richiestaDiProtocollazione.Allegati != null)
            {
                return;
            }

            if (this._protoIn.HaAllegati())
            {
                return;
            }

            var noAllegatiFO = this.GetParamVertBool(this._verticalizzazioneProtocolloAttivo.Noallegatifo, "NOALLEGATIFO");
            var noAllegati = this.GetParamVertBool(this._verticalizzazioneProtocolloAttivo.Noallegati, "NOALLEGATI");

            this._richiestaDiProtocollazione.Allegati = [];

            var verificaRecuperoAllegati = (
            (
                tipoInserimento == Source.INSERIMENTO_NORMALE ||
                tipoInserimento == Source.INSERIMENTO_RAPIDO ||
                tipoInserimento == Source.PROT_IST_MOV_AUT_BO ||
                tipoInserimento == Source.NONPROTOCOLLARE
            ) && !noAllegati
            ) || (
                tipoInserimento == Source.ON_LINE &&
                !noAllegatiFO
            );

            this._log.DebugFormat("SOURCE: {0}, NOALLEGATIFO: {1}, NOALLEGATI: {2}", tipoInserimento, noAllegatiFO, noAllegati);
            this._log.DebugFormat("VERIFICA RECUPERO ALLEGATI: {0}", verificaRecuperoAllegati);

            if (!verificaRecuperoAllegati)
            {
                return;
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                this._richiestaDiProtocollazione.Allegati = this._protocollazioneRepository.RisolviDocumentiIstanza(Convert.ToInt32(this._datiProtocollazione.CodiceIstanza));
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                this._richiestaDiProtocollazione.Allegati = this._protocollazioneRepository.RepositoryGetAllegatiMovimento(this._datiProtocollazione.CodiceMovimento);

                this._log.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su MovimentiAllegati, numero Allegati: {0}", this._richiestaDiProtocollazione.Allegati.Length);
            }
        }

        private void AggiungiAllegatiDaNLAPecAProtoIn()
        {
            if (this._protocolloAttivo.Provenienza == TipoProvenienza.ONLINE &&
                this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA &&
                this._verticalizzazioneProtocolloAttivo.InviaEmlPecArrivo)
            {
                var allegati = this.LeggiAllegatiDaNlaPec();

                this.AggiungiAllegati(allegati);
            }
        }


        /// <summary>
        /// Aggiunge l'xml della domanda stc come allegato se il parametro "AllegaXmlDomandaStc" della verticalizzazione è settato a true e l'ambito è DA_ISTANZA
        /// </summary>
        private void AggiungiAllegatoDomandaSTC()
        {
            this._log.Debug($"VERIFICA SE DEVE ESSERE ALLEGATO ANCHE IL FILE XML DELLA DOMANDA: PARAMETRO ALLEGA_XMLDOMANDASTC = {this._verticalizzazioneProtocolloAttivo.AllegaXmlDomandaStc}, AMBITO = {this._datiProtocollazione.TipoAmbito}");

            if (!this._verticalizzazioneProtocolloAttivo.AllegaXmlDomandaStc)
            {
                return;
            }

            if (this._datiProtocollazione.TipoAmbito != AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                return;
            }

            this._log.Info($"RICERCA DELLA DOMANDA STC DELL'ISTANZA NUMERO: {this._datiProtocollazione.NumeroIstanza}, CODICE: {this._datiProtocollazione.CodiceIstanza}");

            var domande = this._protocollazioneRepository.RepositoryGetDomandeStcByCodiceIstanza(Convert.ToInt32(this._datiProtocollazione.CodiceIstanza));

            var numeroDomande = domande.Count();

            this._log.Info($"LA RICERCA DELLA DOMANDA STC DELL'ISTANZA NUMERO: {this._datiProtocollazione.NumeroIstanza}, CODICE: {this._datiProtocollazione.CodiceIstanza}, HA PRODOTTO {numeroDomande} RISULTATO/I");

            if (numeroDomande == 0)
            {
                this._log.Info($"LA DOMANDA STC DELL'ISTANZA NUMERO: {this._datiProtocollazione.NumeroIstanza} NON E' STATA TROVATA");
                return;
            }

            if (numeroDomande > 1)
            {
                this._log.Info($"SONO STATE TROVATE PIU' DOMANDE RELATIVE ALL'ISTANZA NUMERO {this._datiProtocollazione.NumeroIstanza} L'OGGETTO DELLA DOMANDA NON SARA' INSERITO");
                return;
            }

            var domanda = domande.First();

            if (domanda.CodiceOggetto == null)
            {
                this._log.Info($"CODICE OGGETTO DELLA DOMANDA {domanda.Id} NON PRESENTE");
            }

            this._log.Info($"ID DOMANDA: {domanda.Id}, CODICE OGGETTO DEL FILE XML DA SCARICARE: {domanda.CodiceOggetto}");
            var oggetto = this._protocollazioneRepository.RepositoryGetOggettoById(domanda.CodiceOggetto!.Value);

            this.AggiungiAllegato(
                new ProtocolloAllegati
                {
                    CODICEOGGETTO = domanda.CodiceOggetto!.Value.ToString(),
                    MimeType = this._protocollazioneRepository.RepositoryGetContentType(oggetto),
                    Extension = Path.GetExtension(oggetto.NOMEFILE),
                    NOMEFILE = oggetto.NOMEFILE,
                    Descrizione = oggetto.NOMEFILE,
                    IDCOMUNE = oggetto.IDCOMUNE,
                    OGGETTO = oggetto.OGGETTO,
                });

            this._log.Info("ALLEGATO FILE XML DELLA DOMANDA STC INSERITO CON SUCCESSO TRA I FILE DA INVIARE AL PROTOCOLLO");
        }

        private bool EscludiFile(string ext)
        {
            var estensioniNonAmmesse = this._verticalizzazioneProtocolloAttivo.EstensioniNonAmmesse;
            this._log.Info($"LISTA FILE DA ESCLUDERE: {estensioniNonAmmesse}, estensione del file: {ext}");
            if (!String.IsNullOrEmpty(estensioniNonAmmesse))
            {
                return estensioniNonAmmesse.Split(';').ToList().Contains(ext);
            }

            return false;
        }

        /// <summary>
        /// Questa funzionalità cerca nella tabella oggetti_metadati se è presente il file riepilogo domanda, in caso positivo lo sposta come primo elemento 
        /// facendolo diventare principale.
        /// </summary>
        protected void ImpostaRiepilogoComeAllegatoPrincipale()
        {
            if (this._protoIn.HaAllegati() && this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                var codiceIstanza = Convert.ToInt32(this._datiProtocollazione.CodiceIstanza);
                var codiceOggettoRiepilogoDomanda = this._protocollazioneRepository.RepositoryGetCodiceOggettoRiepilogoDaMetadati(codiceIstanza, this._verticalizzazioneProtocolloAttivo.MetadatoRiepilogoDomanda);

                this._protoIn.ImpostaAllegatoPrincipaleDaCodiceOggetto(codiceOggettoRiepilogoDomanda);
            }
        }

        /// <summary>
        /// Metodo usato per settare la proprietà Mittenti
        /// </summary>
        private void SetMittenti()
        {
            try
            {
                this._log.Debug("Recupero dei dati relativi ai mittenti");

                this._protoIn.Mittenti = new ListaMittDest
                {
                    Amministrazione = new List<ProtocolloAmministrazioni>(),
                    Anagrafe = new List<ProtocolloAnagrafe>()
                };

                var codiciGiaPresenti = new List<string>();

                switch (this._protoIn.Flusso)
                {
                    case "P":   // Flusso in partenza
                    case "I":   // Flusso interno
                        this.SetMittentiPerFlussoInPartenzaOInterno(codiciGiaPresenti);

                        break;

                    case "A":  // Flusso in arrivo
                        this.SetMittentiPerFlussoInArrivo(codiciGiaPresenti);

                        break;

                    default:
                        break;
                }
            }
            catch (Exception ex)
            {
                this._log.Error($"ERRORE GENERATO DURANTE IL SETTAGGIO DEI MITTENTI NELL'OGGETTO, {ex.Message}", ex);

                throw;
            }
            finally
            {
                this._log.Debug("Fine recupero dei dati relativi ai mittenti");
            }
        }

        private void SetMittentiPerFlussoInArrivo(List<string> codiciGiaPresenti)
        {
            if (this._richiestaDiProtocollazione.Mittenti == null)
            {
                if (this._datiProtocollazione.Istanza == null)
                {
                    throw new ProtocolloException("ISTANZA NON VALORIZZATA");
                }

                if (String.IsNullOrEmpty(this._datiProtocollazione.Istanza.CODICERICHIEDENTE))
                {
                    throw new ProtocolloException($"SetMittentiPerFlussoInArrivo: RICHIEDENTE NON TROVATO per l'istanza {this._datiProtocollazione.Istanza.CODICEISTANZA}");
                }

                var richiedente = this._protocollazioneRepository.RepositoryGetAnagrafeById(Convert.ToInt32(this._datiProtocollazione.Istanza.CODICERICHIEDENTE));
                richiedente?.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                richiedente?.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                var azienda = this._protocollazioneRepository.RepositoryGetAnagrafeById(this._datiProtocollazione.Istanza.CODICETITOLARELEGALE);
                azienda?.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                azienda?.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                var tecnico = this._protocollazioneRepository.RepositoryGetAnagrafeById(this._datiProtocollazione.Istanza.CODICEPROFESSIONISTA);
                tecnico?.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                tecnico?.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                var qualificheService = new QualificaDittaIndividualeService(this._authInfo, this._verticalizzazioneProtocolloAttivo, this._datiProtocollazione.Istanza.FKCODICESOGGETTO);

                this._richiestaDiProtocollazione.Mittenti = new DatiMittentiXmlType
                {
                    Anagrafe = new MittentiDestinatariFactory(this._log, qualificheService, richiedente!, azienda, tecnico).GetSoggetti((TipoMittenteEnum)this._verticalizzazioneProtocolloAttivo.TipoMittDestAuto)
                };
            }

            if (this._richiestaDiProtocollazione.Mittenti.Anagrafe != null)
            {
                codiciGiaPresenti.Clear();

                foreach (var datiAnagrafici in this._richiestaDiProtocollazione.Mittenti.Anagrafe)
                {
                    if (codiciGiaPresenti.Contains(datiAnagrafici.CODICEANAGRAFE))
                    {
                        continue;
                    }

                    codiciGiaPresenti.Add(datiAnagrafici.CODICEANAGRAFE);

                    this._protoIn.Mittenti.Anagrafe.Add(datiAnagrafici);

                    this._protocolloAttivo.Anagrafiche ??= new List<IAnagraficaAmministrazione>();
                    this._protocolloAttivo.Anagrafiche.Add(new AnagraficaService(datiAnagrafici));

                    /// TODO: Questo codice va spostato fuori, nel momento in cui si recuperano i mittenti
                    /// Fare attenzione al recupero della pec. Ci sono due campi: Pec e PecAnagrafica. La PecAnagrafica è quella presente nell'anagrafica, mentre la Pec 
                    /// è quella che viene passata come parametro nella richiesta di protocollazione. 
                    /// Se la Pec non è valorizzata, allora si prende quella dell'anagrafica. In caso contrario, si prende quella passata come parametro.
                    /*
                    var anag = this._protocollazioneRepository.RepositoryGetAnagrafeById(Convert.ToInt32(datiAnagrafici.Cod));

                    if (anag == null)
                    {
                        throw new ProtocolloException(String.Format("IL CODICEANAGRAFE {0} PER L'IDCOMUNE {1} NON E' PRESENTE NELL'ANAGRAFICA", datiAnagrafici.Cod, this._datiProtocollazione.IdComune));
                    }

                    var protoAnag = new ProtocolloAnagrafe
                    {
                        CAP = anag.CAP,
                        CITTA = anag.CITTA,
                        CODCOMNASCITA = anag.CODCOMNASCITA,
                        CODICEANAGRAFE = anag.CODICEANAGRAFE,
                        CODICEFISCALE = anag.CODICEFISCALE,
                        COMUNERESIDENZA = anag.COMUNERESIDENZA,
                        DATANASCITA = anag.DATANASCITA,
                        DATANOMINATIVO = anag.DATANOMINATIVO,
                        EMAIL = anag.EMAIL,

                        PecProtocollazione = String.IsNullOrEmpty(datiAnagrafici.Email) ? anag.PecProtocollazione : datiAnagrafici.Email,
                        PecAnagrafica = anag.PecProtocollazione,

                        FAX = anag.FAX,
                        INDIRIZZO = anag.INDIRIZZO,
                        NOME = anag.NOME,
                        NOMINATIVO = anag.NOMINATIVO,
                        PARTITAIVA = anag.PARTITAIVA,
                        PROVINCIA = anag.PROVINCIA,
                        SESSO = anag.SESSO,
                        TELEFONO = anag.TELEFONO,
                        TELEFONOCELLULARE = anag.TELEFONOCELLULARE,
                        TIPOANAGRAFE = anag.TIPOANAGRAFE,
                        TITOLO = anag.TITOLO,
                    };

                    if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA ||
                        this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                    {
                        if (String.IsNullOrEmpty(datiAnagrafici.Email))
                        {
                            var gestionePec = (ProtocolloAnagrafe.TipoGestionePecEnum)Enum.Parse(typeof(ProtocolloAnagrafe.TipoGestionePecEnum), this._verticalizzazioneProtocolloAttivo.GestionePec);
                            protoAnag.SetPec(this._datiProtocollazione.Istanza, gestionePec);
                        }
                    }

                    if (!String.IsNullOrEmpty(anag.CODCOMNASCITA))
                    {
                        var comune = this._protocollazioneRepository.RepositoryGetComuneById(anag.CODCOMNASCITA);

                        protoAnag.CodiceIstatComNasc = comune.CodiceIstat;
                        protoAnag.CodiceStatoEsteroNasc = comune.CodiceStatoEstero;
                    }

                    if (!String.IsNullOrEmpty(anag.COMUNERESIDENZA))
                    {
                        var comune = this._protocollazioneRepository.RepositoryGetComuneById(anag.COMUNERESIDENZA);

                        protoAnag.CodiceIstatComRes = comune.CodiceIstat; 
                        protoAnag.CodiceStatoEsteroRes = comune.CodiceStatoEstero;
                        protoAnag.ComuneResidenza = comune;
                    }

                    if (!codiciGiaPresenti.Contains(protoAnag.CODICEANAGRAFE))
                    {
                        protoAnag.Mezzo = datiAnagrafici.Mezzo;
                        protoAnag.ModalitaTrasmissione = datiAnagrafici.ModalitaTrasmissione;

                        this._protoIn.Mittenti.Anagrafe.Add(protoAnag);

                        codiciGiaPresenti.Add(protoAnag.CODICEANAGRAFE);
                    }

                    this._protocolloAttivo.Anagrafiche ??= new List<IAnagraficaAmministrazione>();

                    this._protocolloAttivo.Anagrafiche.Add(new AnagraficaService(protoAnag));
                    */
                }
            }

            if (this._richiestaDiProtocollazione.Mittenti.Amministrazione != null)
            {
                codiciGiaPresenti.Clear();

                foreach (var datiAmministrazione in this._richiestaDiProtocollazione.Mittenti.Amministrazione)
                {
                    if (codiciGiaPresenti.Contains(datiAmministrazione.CODICEAMMINISTRAZIONE))
                    {
                        continue;
                    }

                    codiciGiaPresenti.Add(datiAmministrazione.CODICEAMMINISTRAZIONE);

                    this._protoIn.Mittenti.Amministrazione.Add(datiAmministrazione);

                    this._protocolloAttivo.Anagrafiche ??= new List<IAnagraficaAmministrazione>();
                    this._protocolloAttivo.Anagrafiche.Add(new AmministrazioneService(datiAmministrazione));

                    // TODO: Spostare le chiamate a monte, probabilmente nella chiamata a protocollazione
                    /*
                    var amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(datiAmministrazione.Cod));

                    if (amm == null)
                    {
                        throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON E' PRESENTE NELL'ANAGRAFICA DELL'AMMINISTRAZIONE", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));
                    }

                    if (amm.HaUnitaOrganizzativaORuoloSettati)
                    {
                        throw new Exception("UN PROTOCOLLO IN ARRIVO NON PUÒ AVERE COME MITTENTE UNA AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO SETTATI! USARE IL FLUSSO INTERNO!");
                    }

                    if (!codiciGiaPresenti.Contains(amm.CODICEAMMINISTRAZIONE))
                    {
                        amm.Mezzo = datiAmministrazione.Mezzo;
                        amm.ModalitaTrasmissione = datiAmministrazione.ModalitaTrasmissione;

                        if (!String.IsNullOrEmpty(datiAmministrazione.Email))
                        {
                            amm.PEC = datiAmministrazione.Email;
                        }

                        this._protoIn.Mittenti.Amministrazione.Add(amm);

                        codiciGiaPresenti.Add(amm.CODICEAMMINISTRAZIONE);
                    }

                    this._protocolloAttivo.Anagrafiche ??= new List<IAnagraficaAmministrazione>();

                    this._protocolloAttivo.Anagrafiche.Add(new AmministrazioneService(amm));
                    */
                }
            }

            if ((this._protoIn.Mittenti.Amministrazione.Count == 0) && (this._protoIn.Mittenti.Anagrafe.Count == 0))
            {
                throw new ProtocolloException("UN PROTOCOLLO IN ARRIVO DEVE AVERE COME MITTENTE ALMENO UN'AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO NON SETTATI O ALMENO UN'ANAGRAFICA!");
            }
        }

        private void SetMittentiPerFlussoInPartenzaOInterno(List<string> codiciGiaPresenti)
        {
            if (this._richiestaDiProtocollazione.Mittenti == null)
            {
                var codiceAmministrazione = this._protocollazioneRepository.RepositoryGetCodiceAmministrazioneFromAlberoProcProtocollo(
                    this._datiProtocollazione.CodiceInterventoProc,
                    this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault
                );

                if (!String.IsNullOrEmpty(codiceAmministrazione))
                {
                    var amministrazione = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(codiceAmministrazione));
                    amministrazione.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                    amministrazione.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                    this._richiestaDiProtocollazione.Mittenti = new DatiMittentiXmlType
                    {
                        Amministrazione = [amministrazione]
                    };
                }
            }

            if (!(this._richiestaDiProtocollazione.Mittenti?.Amministrazione?.Any(amm => amm.HaUnitaOrganizzativaORuoloSettati) ?? false))
            {
                throw new ProtocolloException("UN PROTOCOLLO IN PARTENZA OPPURE INTERNO DEVE AVERE COME MITTENTE UN'AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO SETTATI!");
            }

            foreach (var amministrazione in this._richiestaDiProtocollazione.Mittenti!.Amministrazione!)
            {
                if (codiciGiaPresenti.Contains(amministrazione.CODICEAMMINISTRAZIONE))
                {
                    continue;
                }

                if (!amministrazione.HaUnitaOrganizzativaORuoloSettati)
                {
                    throw new ProtocolloException(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON HA VALORIZZATO NE' L'UNITÀ ORGANIZZATIVA NE' IL RUOLO", amministrazione.CODICEAMMINISTRAZIONE, this._datiProtocollazione.IdComune));
                }

                if (this._protoIn.Mittenti.Amministrazione.Any())
                {
                    throw new ProtocolloException("UN PROTOCOLLO IN PARTENZA OPPURE INTERNO NON PUÒ AVERE COME MITTENTE PIÙ DI UNA AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO SETTATI!");
                }
                //if ((String.IsNullOrEmpty(datiAnagAmm.PROT_UO)) && (String.IsNullOrEmpty(datiAnagAmm.PROT_RUOLO)))
                //{
                //    throw new ProtocolloException(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON HA VALORIZZATO NE' L'UNITÀ ORGANIZZATIVA NE' IL RUOLO", datiAnagAmm.Cod, this._datiProtocollazione.IdComune));
                //}

                codiciGiaPresenti.Add(amministrazione.CODICEAMMINISTRAZIONE);
                this._protoIn.Mittenti.Amministrazione.Add(amministrazione);

                /*
                var amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(datiAnagAmm.Cod)) ?? throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE  {0}, CODICE COMUNE: {1} NON È PRESENTE NELL'ANAGRAFICA DELL'AMMINISTRAZIONE", datiAnagAmm.Cod, this._datiProtocollazione.CodiceComune));

                if (amm.HaUnitaOrganizzativaORuoloSettati)
                {
                    if (!codiciGiaPresenti.Contains(amm.CODICEAMMINISTRAZIONE))
                    {
                        amm.Mezzo = datiAnagAmm.Mezzo;
                        amm.ModalitaTrasmissione = datiAnagAmm.ModalitaTrasmissione;

                        this._protoIn.Mittenti.Amministrazione.Add(amm);

                        codiciGiaPresenti.Add(amm.CODICEAMMINISTRAZIONE);
                    }

                    if (this._protoIn.Mittenti.Amministrazione.Count > 1)
                    {
                        throw new ProtocolloException("UN PROTOCOLLO IN PARTENZA OPPURE INTERNO NON PUÒ AVERE COME MITTENTE PIÙ DI UNA AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO SETTATI!");
                    }
                }

                if ((String.IsNullOrEmpty(amm.PROT_UO)) && (String.IsNullOrEmpty(amm.PROT_RUOLO)))
                {
                    throw new ProtocolloException(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON HA VALORIZZATO NE' L'UNITÀ ORGANIZZATIVA NE' IL RUOLO", datiAnagAmm.Cod, this._datiProtocollazione.IdComune));
                }
                */
            }
        }

        /// <summary>
        /// Metodo usato per settare la proprietà Destinatari
        /// </summary>
        private void SetDestinatari()
        {
            try
            {
                this._log.Debug("Recupero dei dati relativi ai destinatari");

                this._protoIn.Destinatari = new ListaMittDest
                {
                    Amministrazione = [],
                    Anagrafe = []
                };

                switch (this._protoIn.Flusso)
                {
                    case "A":
                    case "I":
                        this.SetDestinatariPerFlussoInArrivoOInterno();

                        break;

                    case "P":
                        this.SetDestinatariPerFlussoInPartenza();

                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex)
            {
                this._log.Error($"ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI, {ex.Message}", ex);

                throw new ProtocolloException($"ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI, {ex.Message}", ex);
            }
            finally
            {
                this._log.Debug("Fine recupero dei dati relativi ai destinatari");
            }
        }

        private void SetDestinatariPerFlussoInPartenza()
        {
            var codiciGiaTrovati = new List<string>();

            //Verifico se i mittenti sono settati dal file xml

            if (this._richiestaDiProtocollazione.Destinatari == null)
            {
                if (this._datiProtocollazione.Istanza == null)
                {
                    throw new ProtocolloException("ISTANZA NON VALORIZZATA");
                }

                if (String.IsNullOrEmpty(this._datiProtocollazione.Istanza.CODICERICHIEDENTE))
                {
                    throw new ProtocolloException($"SetDestinatariPerFlussoInPartenza: RICHIEDENTE NON TROVATO per l'istanza {this._datiProtocollazione.Istanza.CODICEISTANZA}");
                }

                var richiedente = this._protocollazioneRepository.RepositoryGetAnagrafeById(Convert.ToInt32(this._datiProtocollazione.Istanza.CODICERICHIEDENTE));
                richiedente?.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                richiedente?.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                var azienda = this._protocollazioneRepository.RepositoryGetAnagrafeById(this._datiProtocollazione.Istanza.CODICETITOLARELEGALE);
                azienda?.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                azienda?.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                var tecnico = this._protocollazioneRepository.RepositoryGetAnagrafeById(this._datiProtocollazione.Istanza.CODICEPROFESSIONISTA);
                tecnico?.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                tecnico?.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;

                var qualificheService = new QualificaDittaIndividualeService(this._authInfo, this._verticalizzazioneProtocolloAttivo, this._datiProtocollazione.Istanza.FKCODICESOGGETTO);

                this._richiestaDiProtocollazione.Destinatari = new DatiDestinatariXmlType
                {
                    Anagrafe = new MittentiDestinatariFactory(this._log, qualificheService, richiedente!, azienda, tecnico).GetSoggetti((TipoMittenteEnum)this._verticalizzazioneProtocolloAttivo.TipoMittDestAuto)
                };
            }

            //Verifico le anagrafiche per i destinatari
            if (this._richiestaDiProtocollazione.Destinatari.Anagrafe != null)
            {


                foreach (var datiAnagrafe in this._richiestaDiProtocollazione.Destinatari.Anagrafe)
                {
                    if (codiciGiaTrovati.Contains(datiAnagrafe.CODICEANAGRAFE))
                    {
                        continue;
                    }

                    codiciGiaTrovati.Add(datiAnagrafe.CODICEANAGRAFE);

                    this._protoIn.Destinatari.Anagrafe.Add(datiAnagrafe);
                    this._protocolloAttivo.Anagrafiche ??= new List<IAnagraficaAmministrazione>();
                    this._protocolloAttivo.Anagrafiche.Add(new AnagraficaService(datiAnagrafe));

                    /*
                    if (codiciGiaTrovati.Contains(datiAnagrafe.Cod))
                    {
                        continue;
                    }

                    var a = this._protocollazioneRepository.RepositoryGetAnagrafeById(Convert.ToInt32(datiAnagrafe.Cod));

                    if (a == null)
                    {
                        throw new Exception(String.Format("IL CODICEANAGRAFE {0} PER L'IDCOMUNE {1} NON È PRESENTE IN ANAGRAFICA", datiAnagrafe.Cod, this._datiProtocollazione.IdComune));
                    }

                    var protoAnag = new ProtocolloAnagrafe
                    {
                        CAP = a.CAP,
                        CITTA = a.CITTA,
                        CODCOMNASCITA = a.CODCOMNASCITA,
                        CODICEANAGRAFE = a.CODICEANAGRAFE,
                        CODICEFISCALE = a.CODICEFISCALE,
                        COMUNERESIDENZA = a.COMUNERESIDENZA,
                        DATANASCITA = a.DATANASCITA,
                        DATANOMINATIVO = a.DATANOMINATIVO,
                        EMAIL = a.EMAIL,
                        PecProtocollazione = String.IsNullOrEmpty(datiAnagrafe.Email) ? a.PecProtocollazione : datiAnagrafe.Email,
                        PecAnagrafica = a.PecProtocollazione,
                        FAX = a.FAX,
                        INDIRIZZO = a.INDIRIZZO,
                        NOME = a.NOME,
                        NOMINATIVO = a.NOMINATIVO,
                        PARTITAIVA = a.PARTITAIVA,
                        PROVINCIA = a.PROVINCIA,
                        SESSO = a.SESSO,
                        TELEFONO = a.TELEFONO,
                        TELEFONOCELLULARE = a.TELEFONOCELLULARE,
                        TIPOANAGRAFE = a.TIPOANAGRAFE,
                        TITOLO = a.TITOLO,
                    };

                    if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA || this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                    {
                        if (String.IsNullOrEmpty(datiAnagrafe.Email))
                        {
                            var gestionePec = (ProtocolloAnagrafe.TipoGestionePecEnum)Enum.Parse(typeof(ProtocolloAnagrafe.TipoGestionePecEnum), this._verticalizzazioneProtocolloAttivo.GestionePec);
                            protoAnag.SetPec(this._datiProtocollazione.Istanza, gestionePec);
                        }
                    }

                    if (!String.IsNullOrEmpty(a.CODCOMNASCITA))
                    {
                        var comune = this._protocollazioneRepository.RepositoryGetComuneById(a.CODCOMNASCITA);

                        protoAnag.CodiceIstatComNasc = comune.CodiceIstat;
                        protoAnag.CodiceStatoEsteroNasc = comune.CodiceStatoEstero;
                    }

                    if (!String.IsNullOrEmpty(a.COMUNERESIDENZA))
                    {
                        var comune = this._protocollazioneRepository.RepositoryGetComuneById(a.COMUNERESIDENZA);

                        protoAnag.CodiceIstatComRes = comune.CodiceIstat;
                        protoAnag.CodiceStatoEsteroRes = comune.CodiceStatoEstero;
                        protoAnag.ComuneResidenza = comune;
                    }

                    protoAnag.Mezzo = datiAnagrafe.Mezzo;
                    protoAnag.ModalitaTrasmissione = datiAnagrafe.ModalitaTrasmissione;

                    this._protoIn.Destinatari.Anagrafe.Add(protoAnag);

                    codiciGiaTrovati.Add(protoAnag.CODICEANAGRAFE);

                    if (this._protocolloAttivo.Anagrafiche == null)
                    {
                        this._protocolloAttivo.Anagrafiche = new List<IAnagraficaAmministrazione>();
                    }

                    this._protocolloAttivo.Anagrafiche.Add(new AnagraficaService(protoAnag));
                    */
                }
            }

            if (this._richiestaDiProtocollazione.Destinatari.Amministrazione != null)
            {
                codiciGiaTrovati.Clear();

                foreach (var datiAmministrazione in this._richiestaDiProtocollazione.Destinatari.Amministrazione)
                {
                    if (codiciGiaTrovati.Contains(datiAmministrazione.CODICEAMMINISTRAZIONE))
                    {
                        continue;
                    }

                    codiciGiaTrovati.Add(datiAmministrazione.CODICEAMMINISTRAZIONE);

                    this._protoIn.Destinatari.Amministrazione.Add(datiAmministrazione);

                    this._protocolloAttivo.Anagrafiche ??= new List<IAnagraficaAmministrazione>();
                    this._protocolloAttivo.Anagrafiche.Add(new AmministrazioneService(datiAmministrazione));
                    /*
                    var amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(datiAmministrazione.Cod));

                    if (amm == null)
                    {
                        throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON E' PRESENTE NELLA TABELLA DELLE AMMINISTRAZIONI", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));
                    }

                    amm.Mezzo = datiAmministrazione.Mezzo;
                    amm.ModalitaTrasmissione = datiAmministrazione.ModalitaTrasmissione;

                    if (!String.IsNullOrEmpty(datiAmministrazione.Email))
                    {
                        amm.PEC = datiAmministrazione.Email;
                    }

                    this._protoIn.Destinatari.Amministrazione.Add(amm);

                    codiciGiaTrovati.Add(amm.CODICEAMMINISTRAZIONE);

                    this._protocolloAttivo.Anagrafiche ??= new List<IAnagraficaAmministrazione>();

                    this._protocolloAttivo.Anagrafiche.Add(new AmministrazioneService(amm));
                    */
                }
            }

            if ((this._protoIn.Destinatari.Amministrazione.Count == 0) && (this._protoIn.Destinatari.Anagrafe.Count == 0))
            {
                throw new ProtocolloException("UN PROTOCOLLO IN PARTENZA DEVE AVERE COME DESTINATARIO ALMENO UN'AMMINISTRAZIONE O UN'ANAGRAFICA!");
            }
        }

        private void SetDestinatariPerFlussoInArrivoOInterno()
        {
            if (this._richiestaDiProtocollazione.Destinatari == null)
            {
                var codiceAmministrazione = this._protocollazioneRepository.RepositoryGetCodiceAmministrazioneFromAlberoProcProtocollo(
                    this._datiProtocollazione.CodiceInterventoProc,
                    this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault
                );

                if (!String.IsNullOrEmpty(codiceAmministrazione))
                {
                    var amministrazione = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(codiceAmministrazione));
                    amministrazione.Mezzo = this._verticalizzazioneProtocolloAttivo.MezzoDefault;
                    amministrazione.ModalitaTrasmissione = this._verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault;
                    this._richiestaDiProtocollazione.Destinatari = new DatiDestinatariXmlType
                    {
                        Amministrazione = [amministrazione]
                    };
                }
            }

            if (!(this._richiestaDiProtocollazione.Destinatari?.Amministrazione?.Any() ?? false))
            {
                throw new ProtocolloException("AMMINISTRAZIONE DESTINATARIO NON PRESENTE");
            }

            var codiciGiaTrovati = new List<string>();

            foreach (var datiAmministrazione in this._richiestaDiProtocollazione.Destinatari.Amministrazione)
            {
                if (codiciGiaTrovati.Contains(datiAmministrazione.CODICEAMMINISTRAZIONE))
                {
                    continue;
                }

                if (!datiAmministrazione.HaUnitaOrganizzativaORuoloSettati)
                {
                    throw new ProtocolloException(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON HA SETTATO NE' L'UNITÀ ORGANIZZATIVA NE' IL RUOLO", datiAmministrazione.CODICEAMMINISTRAZIONE, this._datiProtocollazione.CodiceComune));
                }

                codiciGiaTrovati.Add(datiAmministrazione.CODICEAMMINISTRAZIONE);

                this._protoIn.Destinatari.Amministrazione.Add(datiAmministrazione);

                /*

                var amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(datiAmministrazione.Cod));

                if (amm == null)
                {
                    throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI!", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));
                }

                if (!amm.HaUnitaOrganizzativaORuoloSettati)
                {
                    throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON HA SETTATO NE' L'UNITÀ ORGANIZZATIVA NE' IL RUOLO", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));
                }

                if (!codiciGiaTrovati.Contains(amm.CODICEAMMINISTRAZIONE))
                {
                    amm.Mezzo = datiAmministrazione.Mezzo;
                    amm.ModalitaTrasmissione = datiAmministrazione.ModalitaTrasmissione;
                    this._protoIn.Destinatari.Amministrazione.Add(amm);

                    codiciGiaTrovati.Add(amm.CODICEAMMINISTRAZIONE);
                }
                */
            }
        }

        public EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            if (String.IsNullOrEmpty(idProtocollo) && !dataProtocollo.HasValue && String.IsNullOrEmpty(numeroProtocollo))
            {
                throw new Exception("L'ID, IL NUMERO E LA DATA DEL PROTOCOLLO SONO VUOTI!");
            }

            if (String.IsNullOrEmpty(idProtocollo))
            {
                if (!dataProtocollo.HasValue)
                {
                    throw new Exception("LA DATA DEL PROTOCOLLO È VUOTA");
                }

                if (String.IsNullOrEmpty(numeroProtocollo))
                {
                    throw new Exception("IL NUMERO DEL PROTOCOLLO È VUOTO!");
                }
            }

            this.SetStampaEtichette();
            var datiEtichette = this._protocolloAttivo.StampaEtichette(idProtocollo, dataProtocollo, numeroProtocollo, numeroCopie, stampante);

            if (datiEtichette != null)
            {
                this._log.InfoFormat("ID ETICHETTA RESTITUITO: {0}", datiEtichette.IdEtichetta);
            }

            this._protocolloAttivo.EliminaTempLogs();

            return datiEtichette;
        }

        private void SetStampaEtichette()
        {
            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;

            var amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault));

            if (amm == null)
            {
                throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI OPPURE NON È STATO SETTATO NELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO!", this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault, this._datiProtocollazione.CodiceComune));
            }

            this._protocolloAttivo.Ruolo = string.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;
        }

        public ListaFascicoliResponseType ListaFascicoli(DatiFascType datiFascicolo)
        {
            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
            this._fascicolo = new Fascicolo
            {
                Classifica = datiFascicolo.ClassificaFascicolo,
                NumeroFascicolo = datiFascicolo.NumeroFascicolo,
                Oggetto = datiFascicolo.OggettoFascicolo,
                DataFascicolo = datiFascicolo.DataFascicolo,
            };

            if (!String.IsNullOrEmpty(datiFascicolo.AnnoFascicolo))
            {
                this._fascicolo.AnnoFascicolo = Convert.ToInt32(datiFascicolo.AnnoFascicolo);
            }

            var listaFascicoli = this._protocolloAttivo.GetFascicoli(this._fascicolo);

            if (listaFascicoli != null)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ListaFascicoliResponseFileName, listaFascicoli);
            }

            return listaFascicoli;
        }

        public DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._log.DebugFormat("Verifica fascicolazione protocollo: idProtocollo={0}, annoProtocollo={1}, numeroProtocollo={2}", idProtocollo, annoProtocollo, numeroProtocollo);

            var chiamataRiuscita = true;

            try
            {
                if (!this.GetParamVertBool(this._verticalizzazioneProtocolloAttivo.GestisciFascicolazione, "GESTISCIFASCICOLAZIONE"))
                {
                    return new DatiProtocolloFascicolatoResponseType
                    {
                        Fascicolato = EnumFascicolatoType.nondefinito,
                        NoteFascicolo = "Con il sistema di protocollazione attivo non è possibile fascicolare il protocollo"
                    };
                }

                if (string.IsNullOrEmpty(idProtocollo) && string.IsNullOrEmpty(annoProtocollo) && string.IsNullOrEmpty(numeroProtocollo))
                {
                    return new DatiProtocolloFascicolatoResponseType
                    {
                        Fascicolato = EnumFascicolatoType.warning,
                        NoteFascicolo = "L'id, il numero e la data del protocollo sono vuoti!"
                    };
                }

                if (string.IsNullOrEmpty(idProtocollo))
                {
                    if (string.IsNullOrEmpty(annoProtocollo))
                    {
                        return new DatiProtocolloFascicolatoResponseType
                        {
                            Fascicolato = EnumFascicolatoType.warning,
                            NoteFascicolo = "La data del protocollo è vuota!"
                        };
                    }

                    if (string.IsNullOrEmpty(numeroProtocollo))
                    {
                        return new DatiProtocolloFascicolatoResponseType
                        {
                            Fascicolato = EnumFascicolatoType.warning,
                            NoteFascicolo = "Il numero del protocollo è vuoto!"
                        };
                    }
                }

                //Per ricavare informazioni utili per la fascicolazione del protocollo
                var codiceAmministrazione = Convert.ToInt32(this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault);
                var amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(codiceAmministrazione);

                if (amm == null)
                {
                    throw new ProtocolloException(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI OPPURE NON È STATO SETTATO NELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO", this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault, this._datiProtocollazione.IdComune));
                }
                this._protocolloAttivo.Ruolo = string.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;
                this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;

                return this._protocolloAttivo.IsFascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                this._log.Error("Errore durante IsFascicolato", ex);

                chiamataRiuscita = false;

                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.warning,
                    NoteFascicolo = "Verifica fascicolazione terminata con errore: " + ex.Message
                };
            }
            finally
            {
                if (chiamataRiuscita)
                {
                    this._protocolloAttivo.EliminaTempLogs();
                }
            }
        }

        public DatiFascicoloResponseType Fascicola(DatiFascType dati, int source = (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO,
            string idProtocollo = null, string numeroProtocollo = null, string annoProtocollo = null, TipoProvenienza provenienza = TipoProvenienza.BACKOFFICE)
        {
            this._protocolloAttivo.Provenienza = provenienza;
            var gestisciFascicolazione = this.GetParamVertBool(this._verticalizzazioneProtocolloAttivo.GestisciFascicolazione, "GESTISCIFASCICOLAZIONE");

            if (!gestisciFascicolazione)
            {
                return new DatiFascicoloResponseType();
            }

            if (source != (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO)
            {
                var fascicolazioneAutomatica = this._protocollazioneRepository.RepositoryVerificaFascicolazioneAutomaticaDaCodiceIntervento(this._datiProtocollazione.CodiceInterventoProc.Value, source);

                if (!fascicolazioneAutomatica)
                {
                    return new DatiFascicoloResponseType();
                }
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                if (String.IsNullOrEmpty(this._datiProtocollazione.Istanza.FKIDPROTOCOLLO) && (String.IsNullOrEmpty(this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO) || !this._datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue))
                {
                    return new DatiFascicoloResponseType();
                }

                if (!String.IsNullOrEmpty(this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO))
                {
                    var partiProtocollo = this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO.Split('/');
                    var sNumProtocollo = partiProtocollo[0];

                    this._protocolloAttivo.IdProtocollo = this._datiProtocollazione.Istanza.FKIDPROTOCOLLO;
                    this._protocolloAttivo.NumProtocollo = sNumProtocollo;
                    this._protocolloAttivo.AnnoProtocollo = this._datiProtocollazione.Istanza.DATAPROTOCOLLO.GetValueOrDefault(DateTime.Now).Year.ToString();
                }
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (String.IsNullOrEmpty(this._datiProtocollazione.Movimento.FKIDPROTOCOLLO) && (String.IsNullOrEmpty(this._datiProtocollazione.Movimento.NUMEROPROTOCOLLO) || !this._datiProtocollazione.Movimento.DATAPROTOCOLLO.HasValue))
                {
                    return new DatiFascicoloResponseType();
                }

                if (!String.IsNullOrEmpty(this._datiProtocollazione.Movimento.NUMEROPROTOCOLLO))
                {
                    var sNumProtSplit = this._datiProtocollazione.Movimento.NUMEROPROTOCOLLO.Split('/');
                    var sNumProtocollo = sNumProtSplit[0];

                    this._protocolloAttivo.IdProtocollo = this._datiProtocollazione.Movimento.FKIDPROTOCOLLO;
                    this._protocolloAttivo.NumProtocollo = sNumProtocollo;
                    this._protocolloAttivo.AnnoProtocollo = this._datiProtocollazione.Movimento.DATAPROTOCOLLO.GetValueOrDefault(DateTime.Now).Year.ToString();
                }
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO)
            {
                if (String.IsNullOrEmpty(idProtocollo) && (String.IsNullOrEmpty(numeroProtocollo) || String.IsNullOrEmpty(annoProtocollo)))
                {
                    throw new Exception("NON E' POSSIBILE FASCICOLARE, NON SONO STATI SPECIFICATI I DATI RELATIVI AL PROTOCOLLO DA FASCICOLARE");
                }

                this._protocolloAttivo.IdProtocollo = idProtocollo;
                this._protocolloAttivo.NumProtocollo = numeroProtocollo;
                this._protocolloAttivo.AnnoProtocollo = annoProtocollo;
            }

            if (dati != null)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.FascicolazioneSoapRequestFileName, dati);
            }

            this._fascicolo = new Fascicolo();

            var isRichiestaDiFascicolazionePronta = this.PreparaLaRichiestaPerCreazioneOCambiamentoFascicolo(dati ?? new DatiFascType(), TipiFascicolazione.CREA);

            DatiFascicoloResponseType rVal;

            if (isRichiestaDiFascicolazionePronta)
            {
                rVal = this._protocolloAttivo.Fascicola(this._fascicolo);

                if (rVal != null)
                {
                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.FascicolazioneSoapResponseFileName, rVal);
                }
            }
            else
            {
                rVal = new DatiFascicoloResponseType { Warning = "LA PRATICA NON È STATA FASCICOLATA!!" };
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ResponseFileName, rVal);
            }

            this._protocolloAttivo.EliminaTempLogs();

            return rVal;
        }

        public DatiFascicoloResponseType CambiaFascicolo(DatiFascType datiFascicolo)
        {
            var datiFascicoloRes = new DatiFascicoloResponseType();

            if (datiFascicolo == null)
            {
                throw new ArgumentNullException(nameof(datiFascicolo));
            }

            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.FascicolazioneSoapRequestFileName, datiFascicolo);

            this._fascicolo = new Fascicolo();

            if (this.PreparaLaRichiestaPerCreazioneOCambiamentoFascicolo(datiFascicolo))
            {
                datiFascicoloRes = this._protocolloAttivo.CambiaFascicolo(this._fascicolo);
            }
            else
            {
                datiFascicoloRes.Warning = "La pratica non è stata fascicolata!!";
            }

            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.FascicolazioneSoapResponseFileName, datiFascicoloRes);

            return datiFascicoloRes;
        }

        public void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            if (string.IsNullOrEmpty(idProtocollo) && string.IsNullOrEmpty(annoProtocollo) && string.IsNullOrEmpty(numeroProtocollo))
            {
                throw new Exception("L'id, il numero e la data del protocollo sono vuoti!");
            }
            else
            {
                if (string.IsNullOrEmpty(idProtocollo))
                {
                    if (string.IsNullOrEmpty(annoProtocollo))
                    {
                        throw new Exception("La data del protocollo è vuota!");
                    }
                    if (string.IsNullOrEmpty(numeroProtocollo))
                    {
                        throw new Exception("Il numero del protocollo è vuoto!");
                    }
                }
            }

            //Per ricavare informazioni utili per la stampa delle etichette
            this.SetAnnullaProtocollo();

            this._protocolloAttivo.AnnullaProtocollo(idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento);
        }

        public DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                if (string.IsNullOrEmpty(idProtocollo) && string.IsNullOrEmpty(annoProtocollo) && string.IsNullOrEmpty(numeroProtocollo))
                {
                    return new DatiProtocolloAnnullatoResponseType
                    {
                        Annullato = EnumAnnullatoType.warning,
                        NoteAnnullamento = "L'id, il numero e la data del protocollo sono vuoti!"
                    };
                }
                else
                {
                    if (string.IsNullOrEmpty(idProtocollo))
                    {
                        if (string.IsNullOrEmpty(annoProtocollo))
                        {
                            return new DatiProtocolloAnnullatoResponseType
                            {
                                Annullato = EnumAnnullatoType.warning,
                                NoteAnnullamento = "La data del protocollo è vuota!"
                            };
                        }
                        if (string.IsNullOrEmpty(numeroProtocollo))
                        {
                            return new DatiProtocolloAnnullatoResponseType
                            {
                                Annullato = EnumAnnullatoType.warning,
                                NoteAnnullamento = "Il numero del protocollo è vuoto!"
                            };
                        }
                    }
                }
                //Per ricavare informazioni utili per l'annullamento del protocollo
                this.SetAnnullaProtocollo();
                return this._protocolloAttivo.IsAnnullato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                return new DatiProtocolloAnnullatoResponseType
                {
                    Annullato = EnumAnnullatoType.warning,
                    NoteAnnullamento = String.Format("Verifica nullabilità terminata con errore: {0}", ex.Message)
                };
            }
        }

        public ListaMotiviAnnullamentoResponseType ListaMotivoAnnullamento()
        {
            this.SetAnnullaProtocollo();

            return this._protocolloAttivo.GetMotivoAnnullamento();
        }

        public void AggiungiAllegati(string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo, int[] codiciAllegati)
        {
            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
            var protoAllegatiList = new List<ProtocolloAllegati>();

            foreach (var codice in codiciAllegati)
            {
                var oggetto = this._protocollazioneRepository.RepositoryGetOggettoById(codice);
                var percorso = this._protocollazioneRepository.RepositoryGetPercorsoFile(codice);

                var nomeAllegato = new NomeFileAllegato(this._datiProtocollazione.IdComune, this._datiProtocollazione.CodiceComune, oggetto, oggetto.NOMEFILE, this._verticalizzazioneProtocolloAttivo.NomeFileMaxLength);

                var protoAllegato = new ProtocolloAllegati
                {
                    MimeType = this._protocollazioneRepository.RepositoryGetContentType(oggetto),
                    Extension = nomeAllegato.GetEstensione(),
                    NOMEFILE = nomeAllegato.GetNomeCompleto(this._verticalizzazioneProtocolloAttivo.NomefileOrigine, protoAllegatiList, this._verticalizzazioneProtocolloAttivo.CreaCopiaFile),
                    Descrizione = nomeAllegato.GetDescrizioneFileCopia(oggetto.NOMEFILE, protoAllegatiList, this._verticalizzazioneProtocolloAttivo.CreaCopiaDescrFile, 0, this._verticalizzazioneProtocolloAttivo.DescrFileMaxLength),
                    CODICEOGGETTO = oggetto.CODICEOGGETTO,
                    IDCOMUNE = oggetto.IDCOMUNE,
                    OGGETTO = oggetto.OGGETTO,
                    Percorso = percorso
                };

                protoAllegatiList.Add(protoAllegato);
            }

            this._protocolloAttivo.AggiungiAllegati(idProtocollo, numeroProtocollo, dataProtocollo, protoAllegatiList);
        }

        private void AggiungiAllegato(ProtocolloAllegati allegato)
        {
            this.AggiungiAllegati([allegato]);
        }

        private void AggiungiAllegati(IEnumerable<ProtocolloAllegati> allegati)
        {
            if (!allegati.Any())
            {
                return;
            }

            var nuoviAllegati = new List<ProtocolloAllegati>();

            foreach (var allegato in allegati)
            {
                var escludiFile = this.EscludiFile(allegato.Extension);

                if (!escludiFile)
                {
                    nuoviAllegati.Add(allegato);
                }
                else
                {
                    this._log.Warn($"ATTENZIONE, il file {allegato.NOMEFILE}: è stato escluso dalla protocollazione in quanto presente nella lista dei file da escludere presente nel parametro ESTENSIONI_NON_AMMESSE nella regola PROTOCOLLO_ATTIVO");
                }
            }

            if (this._verticalizzazioneProtocolloAttivo.TrasformaInPdf)
            {
                var fileConverter = new FileConverterProxy();

                nuoviAllegati
                    .Where(x =>
                      !String.IsNullOrEmpty(x.Extension) &&
                      !this._estensioniNonConvertibili.Contains(x.Extension.ToUpper()))
                    .ToList()
                    .ForEach(x =>
                    {
                        this._log.DebugFormat($"Conversione del file {x.NOMEFILE} con estensione {x.Extension.ToUpper()}");

                        var response = fileConverter.ConvertiFileInPdf(new ConvertiInPDFRequest
                        {
                            BinaryData = x.OGGETTO,
                            ContnentType = x.Extension.Replace(".", "").ToUpper()
                        });

                        x.OGGETTO = response.BinaryData;
                        x.NOMEFILE += ".PDF";
                        x.Extension = ".PDF";
                        x.MimeType = "application/pdf";
                        x.Metadati.Add("CONVERTITO_DA", x.Extension.Replace(".", "").ToUpper());
                        x.Metadati.Add("CONVERTITO_IN", "PDF");
                    });
            }

            this._log.DebugFormat($"Allegati presenti dopo la conversione {nuoviAllegati.Count()}");

            var estensioniAmmesse = this._verticalizzazioneProtocolloAttivo.EstensioniAmmesse;

            if (estensioniAmmesse.Any())
            {
                nuoviAllegati = nuoviAllegati
                                    .Where(x => this.EstensioneConsentita(estensioniAmmesse, x))
                                    .ToList();
            }

            this._log.DebugFormat($"Allegati presenti dopo la verifica delle estensioni ammesse {nuoviAllegati.Count()}");

            this._protoIn.AggiungiAllegati(nuoviAllegati);
        }

        private bool EstensioneConsentita(IEnumerable<string> estensioniAmmesse, ProtocolloAllegati allegato)
        {
            return estensioniAmmesse
                .Contains(allegato.Extension, StringComparer.OrdinalIgnoreCase);
        }


        private bool PreparaLaRichiestaPerCreazioneOCambiamentoFascicolo(DatiFascType richiestaFascicolazione, TipiFascicolazione operazioneDiFascicolazione = TipiFascicolazione.CAMBIA)
        {
            this._log.Debug("Preparazione della richiesta per creazione / cambio fascicolazione");

            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
            this._log.DebugFormat("Operatore: {0}", this._protocolloAttivo.Operatore);
            if (String.IsNullOrEmpty(richiestaFascicolazione.DataFascicolo) && String.IsNullOrEmpty(richiestaFascicolazione.AnnoFascicolo))
            {
                richiestaFascicolazione.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");
            }

            int? annoRiferimentoFascicolo = !String.IsNullOrEmpty(richiestaFascicolazione.AnnoFascicolo) ? Convert.ToInt32(richiestaFascicolazione.AnnoFascicolo) : DateTime.ParseExact(richiestaFascicolazione.DataFascicolo, "dd/MM/yyyy", null).Year;


            this._log.DebugFormat("Tipo fascicolazione: {0}", operazioneDiFascicolazione);

            switch (operazioneDiFascicolazione)
            {
                case TipiFascicolazione.CREA:
                    if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO)
                    {
                        if (!String.IsNullOrEmpty(richiestaFascicolazione.DataFascicolo))
                        {
                            this._fascicolo.DataFascicolo = richiestaFascicolazione.DataFascicolo;
                        }
                        else
                        {
                            this._fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");
                        }

                        this._fascicolo.AnnoFascicolo = annoRiferimentoFascicolo;

                        if (!String.IsNullOrEmpty(richiestaFascicolazione.NumeroFascicolo))
                        {
                            this._fascicolo.NumeroFascicolo = richiestaFascicolazione.NumeroFascicolo;
                        }

                        if (String.IsNullOrEmpty(richiestaFascicolazione.OggettoFascicolo))
                        {
                            throw new Exception("OGGETTO NON VALORIZZATO");
                        }

                        this._fascicolo.Oggetto = richiestaFascicolazione.OggettoFascicolo;
                        this._fascicolo.Classifica = richiestaFascicolazione.ClassificaFascicolo;

                        if (String.IsNullOrEmpty(richiestaFascicolazione.ClassificaFascicolo))
                        {
                            this._fascicolo.Classifica = this._verticalizzazioneProtocolloAttivo.ClassificaFascDefaultBo;
                        }

                        if (String.IsNullOrEmpty(richiestaFascicolazione.NumeroFascicolo) && String.IsNullOrEmpty(this._fascicolo.Classifica))
                        {
                            throw new Exception("NON E' STATA VALORIZZATA LA CLASSIFICA DEL FASCICOLO");
                        }
                    }

                    if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                    {
                        if (!String.IsNullOrEmpty(richiestaFascicolazione.DataFascicolo))
                        {
                            this._fascicolo.DataFascicolo = richiestaFascicolazione.DataFascicolo;
                        }
                        else
                        {
                            this._fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");
                        }

                        this._fascicolo.AnnoFascicolo = annoRiferimentoFascicolo;

                        if (!String.IsNullOrEmpty(richiestaFascicolazione.NumeroFascicolo))
                        {
                            this._fascicolo.NumeroFascicolo = richiestaFascicolazione.NumeroFascicolo;
                        }
                        else
                        {
                            this._fascicolo.NumeroFascicolo = this._protocollazioneRepository.RepositoryGetNumeroFascicoloFromAlberoProcProtocollo(this._datiProtocollazione.CodiceInterventoProc.Value);
                        }

                        if (String.IsNullOrEmpty(richiestaFascicolazione.OggettoFascicolo))
                        {
                            var mailTipoSrv = MailTipoServiceFactory.Create(this._datiProtocollazione, this._log, this._protocolloSerializer, this._bindingFactory, true);
                            var mailTipo = mailTipoSrv.GetMailTipo();
                            this._fascicolo.Oggetto = mailTipo.Oggetto;

                            this._log.DebugFormat("Oggetto restituito: {0}", this._fascicolo.Oggetto);
                        }
                        else
                        {
                            this._fascicolo.Oggetto = richiestaFascicolazione.OggettoFascicolo;
                        }

                        if (!String.IsNullOrEmpty(richiestaFascicolazione.ClassificaFascicolo))
                        {
                            this._fascicolo.Classifica = richiestaFascicolazione.ClassificaFascicolo;
                        }
                        else
                        {
                            this._fascicolo.Classifica = this._protocollazioneRepository.RepositoryGetClassificaFascicoloByCodiceIntervento(this._datiProtocollazione.CodiceInterventoProc.Value);

                            if (String.IsNullOrEmpty(this._fascicolo.Classifica))
                            {
                                this._fascicolo.Classifica = this._verticalizzazioneProtocolloAttivo.ClassificaFascDefaultBo;
                            }
                        }

                        if (String.IsNullOrEmpty(richiestaFascicolazione.NumeroFascicolo) && String.IsNullOrEmpty(this._fascicolo.Classifica))
                        {
                            throw new Exception("NON È STATA SETTATA LA CLASSIFICA NELL'ALBERO DEI PROCEDIMENTI");
                        }
                    }

                    if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                    {
                        if (!String.IsNullOrEmpty(richiestaFascicolazione.NumeroFascicolo))
                        {
                            this._fascicolo.NumeroFascicolo = richiestaFascicolazione.NumeroFascicolo;

                            if (!String.IsNullOrEmpty(richiestaFascicolazione.DataFascicolo))
                            {
                                this._fascicolo.DataFascicolo = richiestaFascicolazione.DataFascicolo;
                            }
                            else
                            {
                                this._fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");
                            }

                            this._fascicolo.AnnoFascicolo = annoRiferimentoFascicolo;
                            this._fascicolo.Classifica = richiestaFascicolazione.ClassificaFascicolo;
                            this._fascicolo.Oggetto = richiestaFascicolazione.OggettoFascicolo;
                        }
                        else
                        {
                            var datiProtFasc = this.IsFascicolato(this._datiProtocollazione.Istanza.FKIDPROTOCOLLO, this._datiProtocollazione.Istanza.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO);
                            this._fascicolo.NumeroFascicolo = datiProtFasc.NumeroFascicolo;
                            this._fascicolo.DataFascicolo = datiProtFasc.DataFascicolo;
                            this._fascicolo.AnnoFascicolo = string.IsNullOrEmpty(datiProtFasc.AnnoFascicolo) ? 0 : Convert.ToInt32(datiProtFasc.AnnoFascicolo);
                            this._fascicolo.Classifica = datiProtFasc.Classifica;
                            this._fascicolo.Oggetto = datiProtFasc.Oggetto;
                        }

                        if (String.IsNullOrEmpty(this._fascicolo.NumeroFascicolo) || (this._fascicolo.AnnoFascicolo == 0))
                        {
                            return false;
                        }
                    }
                    break;

                case TipiFascicolazione.CAMBIA:
                    if (!string.IsNullOrEmpty(richiestaFascicolazione.DataFascicolo))
                    {
                        this._fascicolo.DataFascicolo = richiestaFascicolazione.DataFascicolo;
                    }
                    else
                    {
                        this._fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");
                    }

                    this._fascicolo.AnnoFascicolo = annoRiferimentoFascicolo;

                    if (String.IsNullOrEmpty(richiestaFascicolazione.NumeroFascicolo))
                    {
                        this._fascicolo.NumeroFascicolo = "";
                    }
                    else
                    {
                        this._fascicolo.NumeroFascicolo = richiestaFascicolazione.NumeroFascicolo;
                    }

                    if (!String.IsNullOrEmpty(richiestaFascicolazione.ClassificaFascicolo))
                    {
                        this._fascicolo.Classifica = richiestaFascicolazione.ClassificaFascicolo;
                    }

                    break;
            }

            ProtocolloAmministrazioni? amm = null;

            if (this._datiProtocollazione.TipoAmbito != AmbitoProtocollazioneEnum.NESSUNO)
            {
                amm = this._protocollazioneRepository.RepositoryGetAmministrazioneFromAlberoProcProtocollo(this._datiProtocollazione.CodiceInterventoProc.Value);
            }

            if (amm == null)
            {
                amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault));
            }

            if (amm == null)
            {
                throw new ProtocolloException("PreparaLaRichiestaPerCreazioneOCambiamentoFascicolo: L'amministrazione non è stata trovata");
            }

            this._protocolloAttivo.Ruolo = String.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;

            return true;
        }

        private void SetAnnullaProtocollo()
        {
            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
        }

        public void InvioPec()
        {
            this._protocolloAttivo.InvioPec(this._datiProtocollazione.Movimento.FKIDPROTOCOLLO, this._datiProtocollazione.Movimento.NUMEROPROTOCOLLO, this._datiProtocollazione.Movimento.DATAPROTOCOLLO.Value.Year.ToString());
        }

        public DatiProtocolloResponseType CreaCopie(string codiceAmministrazione)
        {
            if (String.IsNullOrEmpty(this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO) || !this._datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue)
            {
                throw new Exception("IL NUMERO O LA DATA PROTOCOLLO NON E' VALORIZZATO");
            }

            this._log.DebugFormat("Valorizzazione dati tramite la chiamata a SetCreaCopie");
            this.SetCreaCopie(codiceAmministrazione);
            this._log.DebugFormat("Fine valorizzazione dati tramite la chiamata a SetCreaCopie");

            this._log.DebugFormat("Chiamata a CreaCopie da ProtocolloMgr");
            var datiProtocollo = this._protocolloAttivo.CreaCopie();
            this._log.DebugFormat("Fine chiamata a CreaCopie da ProtocolloMgr");

            if (datiProtocollo != null)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.CreaCopieReturnFileName, datiProtocollo);

                var codiceIstanza = Convert.ToInt32(this._datiProtocollazione.CodiceIstanza);
                var fkIdProtocollo = String.IsNullOrEmpty(datiProtocollo.IdProtocollo) ? null : datiProtocollo.IdProtocollo;
                var numeroProtocollo = this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO;
                var dataProtocollo = this._datiProtocollazione.Istanza.DATAPROTOCOLLO.Value;
                if (!String.IsNullOrEmpty(datiProtocollo.NumeroProtocollo) && datiProtocollo.NumeroProtocollo != "0")
                {
                    numeroProtocollo = datiProtocollo.NumeroProtocollo;
                    dataProtocollo = DateTime.ParseExact(datiProtocollo.DataProtocollo, "dd/MM/yyyy", null);
                }
                this._protocollazioneRepository.RepositoryAggiornaRiferimentoProtocolloIstanza(codiceIstanza, fkIdProtocollo, numeroProtocollo, dataProtocollo);

                // Prima con il protocollo Sigepro non veniva aggiornato il campo FKIDPROTOCOLLO per il movimento di avvio
                this._protocollazioneRepository.RepositoryAggiornaRiferimentoProtocolloMovimentoAvvio(codiceIstanza, this._datiProtocollazione.Istanza.TIPOMOVAVVIO, datiProtocollo, dataProtocollo);
            }

            return datiProtocollo;
        }




        private void SetCreaCopie(string codiceAmministrazione)
        {
            ProtocolloAmministrazioni amm = null;

            if (String.IsNullOrEmpty(codiceAmministrazione))
            {
                amm = this._protocollazioneRepository.RepositoryGetAmministrazioneFromAlberoProcProtocollo(this._datiProtocollazione.CodiceInterventoProc.Value);

                if (amm == null)
                {
                    codiceAmministrazione = this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault;
                }
            }

            if (amm == null)
            {
                amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(codiceAmministrazione));
            }

            if (amm == null)
            {
                throw new Exception("AMMINISTRAZIONE NON TROVATA");
            }

            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
            this._protocolloAttivo.Ruolo = amm.PROT_RUOLO;
            this._protocolloAttivo.CodAmministrazione = amm.CODICEAMMINISTRAZIONE;
        }

        public List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest) => this._LeggiProtocollo(leggiProtocolloRequest);

        public List<DatiProtocolloLettoResponseType> LeggiProtocolloUoRuolo(LeggiProtocolloRequest leggiProtocolloRequest, string uo, string ruolo) => this._LeggiProtocollo(leggiProtocolloRequest, uo, ruolo);

        private List<DatiProtocolloLettoResponseType> _LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest, string? uo = null, string? ruolo = null)
        {
            var haIdProtocollo = !String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo);

            var haNumeroEAnno =
                !String.IsNullOrEmpty(leggiProtocolloRequest.NumeroProtocollo) &&
                !String.IsNullOrEmpty(leggiProtocolloRequest.AnnoProtocollo);

            var haRangeDate =
                leggiProtocolloRequest.DallaData.HasValue &&
                leggiProtocolloRequest.AllaData.HasValue;

            if (!haIdProtocollo && !haNumeroEAnno && !haRangeDate)
            {
                throw new Exception("Specificare: ID protocollo, oppure Numero+Anno, oppure un intervallo di date.");
            }

            if (uo != null && ruolo != null)
            {
                this.SetLettura(uo, ruolo);
            }
            else
            {
                this.SetLettura();
            }

            var protocolliLetti = this._protocolloAttivo.LeggiProtocollo(leggiProtocolloRequest);

            if (protocolliLetti != null)
            {
                foreach (var protocollo in protocolliLetti)
                {
                    //TODO dovrebbe bastar passare soltanto  "protocollo". Da verificare!
                    this._protocolloAttivo.CheckProtocolloLetto(protocollo.AnnoProtocollo, protocollo.NumeroProtocollo, protocollo.IdProtocollo, protocollo);
                    this.SetAllegatiWsReturn(protocollo);
                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ResponseFileName, protocollo);
                }

                if (protocolliLetti.Count > 0)
                    this._protocolloAttivo.EliminaTempLogs();
            }

            return protocolliLetti;
        }

        public List<DatiProtocolloLettoResponseType> LeggiProtocolloConData(string idProtocollo, DateTime dataProtocollo, string numeroProtocollo)
        {
            if (dataProtocollo == null)
            {
                throw new Exception("LA DATA DEL PROTOCOLLO E' VUOTA!!");
            }

            if (String.IsNullOrEmpty(idProtocollo) && String.IsNullOrEmpty(numeroProtocollo))
            {
                throw new Exception("ID E NUMERO PROTOCOLLO SONO VUOTI, E' OBBLIGATORIO VALORIZZARNE ALMENO UNO DEI DUE!!");
            }

            var annoProtocollo = dataProtocollo.Year;

            if (this._protocolloStorico.ProtocollazioneStoricaAttiva)
            {
                if (dataProtocollo.Date <= this._protocolloStorico.DataUltimaProtocollazione)
                {
                    var protoLetto = this._protocolloStorico.StoricoLeggiProtocolloConData(idProtocollo, annoProtocollo, numeroProtocollo);
                    if (protoLetto != null)
                    {
                        this._protocolloAttivo.CheckProtocolloLetto(annoProtocollo.ToString(), numeroProtocollo, idProtocollo, protoLetto);
                        this.SetAllegatiWsReturn(protoLetto);
                        this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ResponseFileName, protoLetto);
                    }

                    return new List<DatiProtocolloLettoResponseType>() { protoLetto };
                }
            }

            return this.LeggiProtocollo(new LeggiProtocolloRequest()
            {
                IdProtocollo = idProtocollo,
                AnnoProtocollo = annoProtocollo.ToString(),
                NumeroProtocollo = numeroProtocollo
            });
        }

        /// <summary>
        /// Setta l'id dell'allegato con il formato IDPROTOCOLLO|NUMERO|ANNO|IDALLEGATO e elimina dal tag Image dell'Xml il file in formato binario, vengono letti successivamente con il metodo LeggiAllegato
        /// </summary>
        /// <param name="allout"></param>
        private void SetAllegatiWsReturn(DatiProtocolloLettoResponseType datiProtLetto)
        {
            var allout = datiProtLetto.Allegati;

            if (allout != null)
            {
                for (var i = 0; i < allout.Length; i++)
                {
                    var idprotocollo = String.IsNullOrEmpty(datiProtLetto.IdProtocollo) ? "0" : datiProtLetto.IdProtocollo;
                    var numeroProtocollo = datiProtLetto.NumeroProtocollo;
                    var annoProtocollo = datiProtLetto.AnnoProtocollo;
                    var idAllegato = allout[i].IDBase;
                    allout[i].IDBase = idprotocollo + "|" + numeroProtocollo + "|" + annoProtocollo + "|" + idAllegato + "|" + this._datiProtocollazione.Software;
                    allout[i].Image = null;
                }
            }
        }

        public AllegatoResponseType LeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            this._protocolloAttivo.IdProtocollo = IdProtocollo;
            this._protocolloAttivo.NumProtocollo = numProtocollo;
            this._protocolloAttivo.AnnoProtocollo = annoProtocollo;
            this._protocolloAttivo.IdAllegato = idAllegato;
            this._protocolloAttivo.EstraiEml = this._verticalizzazioneProtocolloAttivo.EstraiEml;
            this._protocolloAttivo.EstraiZip = this._verticalizzazioneProtocolloAttivo.EstraiZip;
            this._protocolloAttivo.EscludiFileDaEml = this._verticalizzazioneProtocolloAttivo.EscludiFilesDaEml.Split('|');
            this._protocolloAttivo.ZipExtensions = this._verticalizzazioneProtocolloAttivo.ExtFileZip.Split(',');

            this.SetLettura();

            this._log.DebugFormat("Lettura dell'allegato, ID Protocollo: {0}, Numero Protocollo: {1}, Anno Protocollo: {2}, Id Allegato: {3}", IdProtocollo, numProtocollo, annoProtocollo, idAllegato);
            var res = this._protocolloAttivo.LeggiAllegato();

            this._protocolloAttivo.EliminaTempLogs();
            return res;
        }

        public AllegatoResponseType LeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato, string uo, string ruolo)
        {
            this._protocolloAttivo.IdProtocollo = IdProtocollo;
            this._protocolloAttivo.NumProtocollo = numProtocollo;
            this._protocolloAttivo.AnnoProtocollo = annoProtocollo;
            this._protocolloAttivo.IdAllegato = idAllegato;
            this._protocolloAttivo.EstraiEml = this._verticalizzazioneProtocolloAttivo.EstraiEml;
            this._protocolloAttivo.EstraiZip = this._verticalizzazioneProtocolloAttivo.EstraiZip;
            this._protocolloAttivo.EscludiFileDaEml = this._verticalizzazioneProtocolloAttivo.EscludiFilesDaEml.Split('|');
            this._protocolloAttivo.ZipExtensions = this._verticalizzazioneProtocolloAttivo.ExtFileZip.Split(',');

            this.SetLettura(uo, ruolo);

            this._log.DebugFormat("Lettura dell'allegato, ID Protocollo: {0}, Numero Protocollo: {1}, Anno Protocollo: {2}, Id Allegato: {3}, Uo: {4}, Ruolo: {5}", IdProtocollo, numProtocollo, annoProtocollo, idAllegato, uo, ruolo);
            var res = this._protocolloAttivo.LeggiAllegato();

            this._protocolloAttivo.EliminaTempLogs();
            return res;
        }

        public AllegatoResponseType LeggiAllegatoStorico(string idProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            if (!this._protocolloStorico.ProtocollazioneStoricaAttiva)
            {
                throw new Exception("La protocollazione storica non è attiva");
            }

            return this._protocolloStorico.StoricoLeggiAllegato(idProtocollo, numProtocollo, annoProtocollo, idAllegato);
        }

        public List<MetadatoType> RecuperaMetadati()
        {
            return this._protocolloAttivo.RecuperaMetadati();
        }

        private void SetLettura(string? uo = null, string? ruolo = null)
        {
            this._protocolloAttivo.Uo = uo;
            this._protocolloAttivo.Ruolo = ruolo;

            if (String.IsNullOrEmpty(this._protocolloAttivo.Uo) && String.IsNullOrEmpty(this._protocolloAttivo.Ruolo))
            {
                var amm = this._protocollazioneRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault));

                if (amm == null)
                {
                    throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI OPPURE NON È STATO SETTATO NELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO", this._verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault, this._datiProtocollazione.IdComune));
                }
                this._protocolloAttivo.Uo = amm.PROT_UO;
                this._protocolloAttivo.Ruolo = String.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;
            }

            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo.Operatore;
            this._protocolloAttivo.EstraiEml = this._verticalizzazioneProtocolloAttivo.EstraiEml;
            this._protocolloAttivo.EstraiZip = this._verticalizzazioneProtocolloAttivo.EstraiZip;
            this._protocolloAttivo.EscludiFileDaEml = this._verticalizzazioneProtocolloAttivo.EscludiFilesDaEml.Split('|');
            this._protocolloAttivo.ZipExtensions = this._verticalizzazioneProtocolloAttivo.ExtFileZip.Split(',');


            this._log.DebugFormat("SetLettura - Operatore: {0}, Uo: {1}, Ruolo: {2}", this._protocolloAttivo.Operatore, this._protocolloAttivo.Uo, this._protocolloAttivo.Ruolo);
        }

        public ListaTipiDocumentoResponseType ListaTipiDocumento()
        {
            var listaTipiDocumento = this._protocolloAttivo.GetTipiDocumento();

            if (listaTipiDocumento != null && this._log.IsDebugEnabled)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.TipiDocumentoSoapResponseFileName, listaTipiDocumento);
            }

            return listaTipiDocumento;
        }

        #region Metodo usato per le classifiche
        public ListaTipiClassificaType ListaClassifiche()
        {
            this._protocolloAttivo.Operatore = this._verticalizzazioneProtocolloAttivo?.Operatore ?? String.Empty;

            var response = this._protocolloAttivo.GetClassifiche();

            if (response != null && this._log.IsDebugEnabled)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ResponseFileName, response);
            }

            return response;
        }

        public ListaFirmatari GetFirmatari()
        {
            return this._protocolloAttivo.GetFirmatari();
        }

        #endregion

        public DatiProtocolloResponseType MettiAllaFirma(DatiRequestType dati)
        {
            this._richiestaDiProtocollazione = dati;

            if (!String.IsNullOrEmpty(this._datiProtocollazione.Movimento.FKIDPROTOCOLLO))
            {
                throw new Exception("ID PROTOCOLLO VALORIZZATO");
            }

            //Deserializzo il file xml che ricevo in ingresso all'interno dell'oggetto Dati
            if (this._richiestaDiProtocollazione != null)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.RequestFileName, this._richiestaDiProtocollazione);
            }
            else
            {
                this._richiestaDiProtocollazione = new DatiRequestType();
            }

            this.SetMettiAllaFirma();

            var response = this._protocolloAttivo.MettiAllaFirma(this._protoIn);

            if (response != null)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ResponseFileName, response);

                // this._datiProtocollazione.Movimento.FKIDPROTOCOLLO = string.IsNullOrEmpty(response.IdProtocollo) ? null : response.IdProtocollo;
                // this._protocollazioneRepository.RepositoryUpdateMovimentoNonElaborare(this._datiProtocollazione.Movimento);
                this._protocollazioneRepository.AggiornaRiferimentiProtocolloSuMovimento(Convert.ToInt32(this._datiProtocollazione.Movimento.CODICEMOVIMENTO), response.IdProtocollo);
            }

            return response;
        }

        public DatiProtocolloResponseType? Protocollazione(TipoProvenienza provenienza, DatiRequestType dati, Source tipoInserimento)
        {
            this._richiestaDiProtocollazione = dati ?? new DatiRequestType();

            this.VerificaValiditaDatiProtocollazione(provenienza, tipoInserimento);

            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.RequestFileName, this._richiestaDiProtocollazione);

            this.SetProtocollo(provenienza, tipoInserimento);

            if (this._log.IsDebugEnabled)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.DatiProtocolloInFileName, this._protoIn);
            }

            var datiProtocollo = this._protocolloAttivo.Protocollazione(this._protoIn);
            this._log.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO: Numero Protocollo: {0} | DataProtocollo: {1} | AnnoProtocollo: {2}", datiProtocollo.NumeroProtocollo, datiProtocollo.DataProtocollo, datiProtocollo.AnnoProtocollo);

            if (datiProtocollo == null)
            {
                return null;
            }

            this._protocolloSerializer.SerializeAndValidateStream(datiProtocollo, ProtocolloLogsConstants.ResponseFileName);

            var numeroProtocollo = datiProtocollo.NumeroProtocollo;
            var dataProtocollo = String.IsNullOrEmpty(datiProtocollo.DataProtocollo) ? (DateTime?)null : DateTime.ParseExact(datiProtocollo.DataProtocollo, "dd/MM/yyyy", null);
            var idProtocollo = datiProtocollo.IdProtocollo;

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                this.AggiornaRiferimentiProtocolloSuIstanzaEMovimentoAvvio(numeroProtocollo, dataProtocollo, idProtocollo);
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                this.AggiornaRiferimentiProtocolloSuMovimento(numeroProtocollo, dataProtocollo, idProtocollo);
            }

            datiProtocollo.CodiciOggettoAllegati = this._protoIn
                                                    .CodiciOggettoAllegati()
                                                    .ToArray();

            this._protocollazioneRepository.RepositoryProtocolloMetadatiInsert(idProtocollo, datiProtocollo.Metadati);

            this._protocolloAttivo.EliminaTempLogs();

            return datiProtocollo;
        }

        private void VerificaValiditaDatiProtocollazione(TipoProvenienza provenienza, Source tipoInserimento)
        {
            var notificaAutomatica = provenienza == TipoProvenienza.BACKOFFICE && (tipoInserimento == Source.ON_LINE);

            if ((tipoInserimento != Source.PROT_IST_MOV_AUT_BO) && !notificaAutomatica)
            {
                var isProtocollazioneAutomatica = this._protocollazioneRepository.RepositoryVerificaProtocollazioneAutomaticaDaCodiceIntervento(this._datiProtocollazione.CodiceInterventoProc.Value, tipoInserimento);

                if (!isProtocollazioneAutomatica)
                {
                    throw new ProtocolloException($"LA PROTOCOLLAZIONE AUTOMATICA NON E' CONFIGURATA, VERIFICARE LA CONFIGURAZIONE DEI PARAMETRI DEL PROTOCOLLO SULLA GESTIONE DELL'ALBERO DEGLI INTERVENTI, CODICE INTERVENTO PROC: {this._datiProtocollazione.CodiceInterventoProc.Value}, SOURCE: {tipoInserimento}, IDCOMUNE: {this._datiProtocollazione.IdComune}, SOFTWARE: {this._datiProtocollazione.Software}, CODICE COMUNE: {this._datiProtocollazione.CodiceComune}");
                }
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                if (!String.IsNullOrEmpty(this._datiProtocollazione.Istanza.FKIDPROTOCOLLO) || !String.IsNullOrEmpty(this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO) || this._datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue)
                {
                    throw new ProtocolloException($"L'ISTANZA CODICE {this._datiProtocollazione.CodiceIstanza} RISULTA AVERE ALCUNI O TUTTI I DATI PROTOCOLLATI, FKIDPROTOCOLLO: {this._datiProtocollazione.Istanza.FKIDPROTOCOLLO}, NUMEROPROTOCOLLO: {this._datiProtocollazione.Istanza.NUMEROPROTOCOLLO}, DATAPROTOCOLLO: {(this._datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue ? this._datiProtocollazione.Istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : String.Empty)}");
                }
            }

            if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (!String.IsNullOrEmpty(this._datiProtocollazione.Movimento.FKIDPROTOCOLLO) || !String.IsNullOrEmpty(this._datiProtocollazione.Movimento.NUMEROPROTOCOLLO) || this._datiProtocollazione.Movimento.DATAPROTOCOLLO.HasValue)
                {
                    throw new ProtocolloException($"IL MOVIMENTO CODICE {this._datiProtocollazione.CodiceMovimento} RISULTA AVERE ALCUNI O TUTTI I DATI PROTOCOLLATI, FKIDPROTOCOLLO: {this._datiProtocollazione.Movimento.FKIDPROTOCOLLO}, NUMEROPROTOCOLLO: {this._datiProtocollazione.Movimento.NUMEROPROTOCOLLO}, DATAPROTOCOLLO: {(this._datiProtocollazione.Movimento.DATAPROTOCOLLO.HasValue ? this._datiProtocollazione.Movimento.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : String.Empty)}");
                }
            }
        }

        private void AggiornaRiferimentiProtocolloSuMovimento(string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo)
        {
            this._log.DebugFormat("Prima dell'update del movimento {0} con i dati del protocollo, idprotocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceMovimento, idProtocollo, dataProtocollo, numeroProtocollo);

            var idMovimento = Convert.ToInt32(this._datiProtocollazione.CodiceMovimento);
            this._protocollazioneRepository.RepositoryMovimentiUpdateDatiProtocollo(idMovimento, idProtocollo, numeroProtocollo, dataProtocollo);

            this._log.DebugFormat("Update del movimento {0} con i dati del protocollo, idprotocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceMovimento, idProtocollo, dataProtocollo, numeroProtocollo);
        }

        private void AggiornaRiferimentiProtocolloSuIstanzaEMovimentoAvvio(string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo)
        {
            var codiceIstanza = Convert.ToInt32(this._datiProtocollazione.CodiceIstanza);
            var tipoMovimento = this._datiProtocollazione.Istanza.TIPOMOVAVVIO;

            this._log.DebugFormat("Prima dell'Update dell'istanza {0} con i dati del protocollo, id protocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceIstanza, idProtocollo, dataProtocollo, numeroProtocollo);

            this._protocollazioneRepository.RepositoryAggiornaRiferimentoProtocolloIstanza(codiceIstanza, idProtocollo, numeroProtocollo, dataProtocollo);

            this._log.DebugFormat("Update dell'istanza {0} con i dati del protocollo, id protocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceIstanza, idProtocollo, dataProtocollo, numeroProtocollo);

            var mov = this._protocollazioneRepository.RepositoryGetMovimentoByTipoMovimento(codiceIstanza, tipoMovimento);

            if (mov != null)
            {
                this._log.DebugFormat("Prima dell'update del movimento di avvio, codice movimento {0}", mov.CODICEMOVIMENTO);

                var idMovimento = Convert.ToInt32(mov.CODICEMOVIMENTO);
                this._protocollazioneRepository.RepositoryMovimentiUpdateDatiProtocollo(idMovimento, idProtocollo, numeroProtocollo, dataProtocollo);

                this._log.DebugFormat("Update del movimento di avvio, codice movimento {0}", mov.CODICEMOVIMENTO);
            }
        }

        public DatiProtocolloResponseType Registrazione(string registro, DatiRequestType dati, TipoProvenienza provenienza = TipoProvenienza.BACKOFFICE, int iSource = (int)Source.PROT_IST_MOV_AUT_BO)
        {
            var tipoInserimento = (Source)iSource;
            this._richiestaDiProtocollazione = dati;

            if (this._richiestaDiProtocollazione == null)
            {
                this._richiestaDiProtocollazione = new DatiRequestType();
            }

            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.RequestFileName, this._richiestaDiProtocollazione);

            this._protoIn = new DatiProtocolloIn();
            this.SetProtocollo(provenienza, tipoInserimento);

            if (this._log.IsDebugEnabled)
            {
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.DatiProtocolloInFileName, this._protoIn);
            }

            return this._protocolloAttivo.Registrazione(registro, this._protoIn);
        }

        public CreaUnitaDocumentaleResponseType CreaUnitaDocumentale(CreaUnitaDocumentaleRequestType request)
        {
            var protocolloAllegati = new List<ProtocolloAllegati>();

            foreach (var allegato in request.Allegati)
            {
                var codiceOggetto = Convert.ToInt32(allegato.Cod);
                var oggetto = this._protocollazioneRepository.RepositoryGetOggettoById(codiceOggetto);
                var percorso = this._protocollazioneRepository.RepositoryGetPercorsoFile(codiceOggetto);

                if (oggetto == null)
                {
                    throw new Exception(String.Format("IL CODICEOGGETTO {0} PER L'IDCOMUNE {1} NON È PRESENTE NELLA TABELLA OGGETTI", allegato.Cod, this._datiProtocollazione.IdComune));
                }

                var contentType = this._protocollazioneRepository.RepositoryGetContentType(oggetto);
                var nomeAllegato = new NomeFileAllegato(this._datiProtocollazione.IdComune, this._datiProtocollazione.CodiceComune, oggetto, allegato.Descrizione, this._verticalizzazioneProtocolloAttivo.NomeFileMaxLength);

                var protocolloAllegato = allegato.ToProtocolloAllegati(oggetto, percorso, contentType, nomeAllegato, this._verticalizzazioneProtocolloAttivo, protocolloAllegati);

                protocolloAllegati.Add(protocolloAllegato);
            }

            return this._protocolloAttivo.CreaUnitaDocumentale(request.TipoDocumento, protocolloAllegati);
        }

        private bool GetParamVertBool(string sParamVertValue, string sParamVertName)
        {
            switch (sParamVertValue)
            {
                case "":
                case "0":
                    return false;
                case "1":
                    return true;
                default:
                    throw new ProtocolloException("Il valore del parametro " + sParamVertName + " non è corretto! Valori ammissibili 0 ed 1, valore settato: " + sParamVertValue);
            }
        }

        public EseguiAccettazioneResponseType EseguiAccettazione(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            if (String.IsNullOrEmpty(idProtocollo) && String.IsNullOrEmpty(annoProtocollo) && String.IsNullOrEmpty(numeroProtocollo))
            {
                throw new ProtocolloException("L'ID, IL NUMERO E L'ANNO DEL PROTOCOLLO SONO VUOTI!");
            }

            if (string.IsNullOrEmpty(idProtocollo) && (String.IsNullOrEmpty(annoProtocollo) || String.IsNullOrEmpty(numeroProtocollo)))
            {
                throw new ProtocolloException($"E'obbligatorio specificare un anno protocollo e un numero protocollo quando non si specifica un id protocollo (anno: {annoProtocollo}, numero: {numeroProtocollo})");
            }

            this.SetLettura();

            var protoLetto = this._protocolloAttivo.EseguiAccettazione(this._authInfo, idProtocollo, annoProtocollo, numeroProtocollo);

            if (protoLetto == null)
            {
                // gestisci
            }

            return protoLetto;
        }

        public DatiProtocolloEsitatoResponseType IsEsitato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                if (string.IsNullOrEmpty(idProtocollo) && string.IsNullOrEmpty(annoProtocollo) && string.IsNullOrEmpty(numeroProtocollo))
                {
                    return new DatiProtocolloEsitatoResponseType("L'id, il numero e la data del protocollo sono vuoti!", null);
                }

                if (string.IsNullOrEmpty(idProtocollo))
                {
                    if (string.IsNullOrEmpty(annoProtocollo))
                    {
                        return new DatiProtocolloEsitatoResponseType("La data del protocollo è vuota!", null);
                    }

                    if (string.IsNullOrEmpty(numeroProtocollo))
                    {
                        return new DatiProtocolloEsitatoResponseType("Il numero del protocollo è vuoto!", null);
                    }

                    return this._protocolloAttivo.IsEsitato(this._authInfo, annoProtocollo, numeroProtocollo);
                }

                return this._protocolloAttivo.IsEsitato(this._authInfo, idProtocollo);
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la verifica dell'esito del protocollo (idProtocollo: {idProtocollo}, annoProtocollo: {annoProtocollo}, numeroProtocollo: {numeroProtocollo})", ex);

                return new DatiProtocolloEsitatoResponseType(ex);
            }
            finally
            {
                this._protocolloAttivo.EliminaTempLogs();
            }
        }

        public void Dispose()
        {
            this._datiProtocollazione.Db?.Dispose();
        }

        private (string oggetto, string corpo) GetOggettoECorpoByMailTipo(
            ResolveDatiProtocollazioneService datiProtocollazione,
            ProtocolloLogs protocolloLogs,
            ProtocolloSerializer protocolloSerializer,
            bool isFascicolazione)
        {
            var mailTipoSrv = MailTipoServiceFactory.Create(datiProtocollazione, protocolloLogs, protocolloSerializer, this._bindingFactory, isFascicolazione);
            protocolloLogs.DebugFormat("Fine creazione del WS per il recupero di oggetto e corpo dalla mailtipo. Il servizio è valorizzato? {0}", mailTipoSrv != null);

            if (mailTipoSrv == null)
            {
                return (string.Empty, string.Empty);
            }

            var mailTipo = mailTipoSrv.GetMailTipo();
            protocolloLogs.DebugFormat("Fine recupero della mail tipo via web service. L'oggetto è valorizzato? {0}", mailTipo != null);

            return (mailTipo?.Oggetto ?? String.Empty, mailTipo?.Corpo ?? String.Empty);
        }
    }
}
