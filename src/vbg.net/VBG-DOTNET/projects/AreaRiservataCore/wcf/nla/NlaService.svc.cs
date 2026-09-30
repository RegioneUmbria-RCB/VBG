using AreaRiservataCore.wcf.nla;
using CoreWCF;
using Gotenberg.Sharp.API.Client.Infrastructure;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;

namespace Init.Sigepro.FrontEnd.WebServices.Nla
{
    [ServiceBehavior(Namespace = "http://sigepro.init.it/rte/definitions", IncludeExceptionDetailInFaults = true)]
    public class NlaService : Nla
    {
        private readonly ILogger<NlaService> _logger;

        private static class Constants
        {
            public const string IdAttivitaRequestCorrectionSsu = "REQUEST_CORRECTION";
            public const string IdAttivitaRequestIntegrationSsu = "REQUEST_INTEGRATION";
            public const string ElementoAttivitaContenenteCorrezioniSsu = "SSU_CORREZIONI_RICHIESTE";

            public const string IdAttivitaInstanceRefusedSsu = "INSTANCE_REFUSED";
        }


        private readonly AliasSoftwareProvider _aliasSoftwareProvider;
        private readonly IOggettiService _oggettiService;
        private readonly SsuNlaService _ssuNlaService;

        public NlaService(AliasSoftwareProvider aliasSoftwareProvider, IOggettiService oggettiService, SsuNlaService ssuNlaService, ILogger<NlaService> logger)
        {
            this._aliasSoftwareProvider = aliasSoftwareProvider;
            this._oggettiService = oggettiService;
            this._ssuNlaService = ssuNlaService;
            this._logger = logger;
        }

        #region INlaService Members

        public RichiestaPraticheListaNLAResponse1 RichiestaPraticheListaNLA(RichiestaPraticheListaNLARequest1 request)
        {
            throw new NotImplementedException();
        }

        public InserimentoPraticaNLAResponse1 InserimentoPraticaNLA(InserimentoPraticaNLARequest1 request)
        {
            throw new NotImplementedException();
        }

        public RichiestaPraticaNLAResponse1 RichiestaPraticaNLA(RichiestaPraticaNLARequest1 request)
        {
            this._logger.LogDebug("Ricevuta richiesta di dettaglio pratica NLA per la pratica {IdPratica}", request.RichiestaPraticaNLARequest.rifPratica.idPratica);
            return new RichiestaPraticaNLAResponse1
            {
                RichiestaPraticaNLAResponse = new RichiestaPraticaNLAResponse
                {
                    dettaglioPratica = new DettaglioPraticaVisuraType
                    {
                        dettaglioPratica = new DettaglioPraticaType
                        {
                            idPratica = request.RichiestaPraticaNLARequest.rifPratica.idPratica,
                            numeroPratica = request.RichiestaPraticaNLARequest.rifPratica.idPratica
                        }
                    }
                }
            };

        }

        public TestNLAResponse1 TestNLA(TestNLARequest1 request)
        {
            this._logger.LogDebug("Esecuzione del test NLA");
            return new TestNLAResponse1
            {
                TestNLAResponse = new TestNLAResponse
                {
                    nlaXsdVersion = XsdNlaVersion.V_1_13,
                    typesXsdVersion = XsdTypesVersion.V_1_13
                }
            };
        }



        public InserimentoAttivitaNLAResponse1 InserimentoAttivitaNLA(InserimentoAttivitaNLARequest1 request)
        {
            this._logger.LogDebug("Ricevuta richiesta di inserimento attività NLA per la pratica {IdPratica} e attività {IdAttivita}",
                request.InserimentoAttivitaNLARequest.datiAttivita.idPratica,
                request.InserimentoAttivitaNLARequest.datiAttivita.idAttivita);

            var aliasComune = request.InserimentoAttivitaNLARequest.sportelloDestinatario.idEnte;
            var software = request.InserimentoAttivitaNLARequest.sportelloDestinatario.idSportello;
            var idAttivita = request.InserimentoAttivitaNLARequest.datiAttivita.idAttivita;
            var identificativoDomanda = request.InserimentoAttivitaNLARequest.datiAttivita.idPratica;

            this._aliasSoftwareProvider.AliasComune = aliasComune;
            this._aliasSoftwareProvider.Software = software;


            return idAttivita switch
            {
                Constants.IdAttivitaRequestCorrectionSsu => this._ssuNlaService.CreaCorrezioneSsu(identificativoDomanda, request),
                Constants.IdAttivitaRequestIntegrationSsu => this._ssuNlaService.CreaRichiestaIntegrazioneSsu(identificativoDomanda, request),
                Constants.IdAttivitaInstanceRefusedSsu => this._ssuNlaService.RifiutaIstanzaSsu(identificativoDomanda, request),
                // _ => this.LogFailure($"Attività non supportata: {idAttivita}", ErroriInserimentoAttivita.AttivitaNonSupportata(idAttivita))
                _ => this.LogFailure($"Attività non supportata: {idAttivita}, Corsetti ha detto che devo comunque ritornare un 200",
                                    InserimentoAttivitaResult.Success(identificativoDomanda, request.InserimentoAttivitaNLARequest.datiAttivita.idAttivita))  //ErroriInserimentoAttivita.AttivitaNonSupportata(idAttivita))
            };

        }

        private T LogFailure<T>(string message, T result)
        {
            this._logger.LogError(message);

            return result;
        }

        public IOggettiService CreaOggettiService(string idcomune, string software)
        {
            this._aliasSoftwareProvider.AliasComune = idcomune;
            this._aliasSoftwareProvider.Software = software;

            return this._oggettiService;
            /*
            var applicationCache = new NullApplicationCache();
            var timedCache = new TimedCache();
            var aliasSoftwareResolver = new StaticAliasSoftwareResolver(idcomune, software);
            var configReader = new WebConfigReader(aliasSoftwareResolver);
            var bindingsFactory = new BindingFactory();
            var sigeproSecurity = new SigeproSecurityProxy(new NullApplicationCache(), configReader, bindingsFactory);

            var sigeproSecurityConfig = new ConfigurazioneImpl<ParametriSigeproSecurity>(new ParametriSigeproSecurityBuilder(sigeproSecurity, configReader));

            var tokenApplicazioneService = new TokenApplicazioneService(
                                                new TokenApplicazioneRepository(sigeproSecurity, sigeproSecurityConfig, timedCache),
                                                aliasSoftwareResolver
                                           );
            var oggettiServiceCreator = new OggettiServiceCreator(
                                sigeproSecurityConfig,
                                tokenApplicazioneService,
                                bindingsFactory);

            var oggettiRepository = new WsOggettiRepository(oggettiServiceCreator);

            return new OggettiService(oggettiRepository,
                        new NullMetadatiOggettoProvider());
            */
        }

        public AllegatoBinarioNLAResponse1 AllegatoBinarioNLA(AllegatoBinarioNLARequest1 request)
        {
            var aliasComune = request.AllegatoBinarioNLARequest.sportelloDestinatario.idEnte;
            var software = request.AllegatoBinarioNLARequest.sportelloDestinatario.idSportello;
            var codiceOggetto = request.AllegatoBinarioNLARequest.riferimentiAllegato.idAllegato;

            var oggettiService = this.CreaOggettiService(aliasComune, software);

            var file = oggettiService.GetById(Convert.ToInt32(codiceOggetto));

            return new AllegatoBinarioNLAResponse1
            {
                AllegatoBinarioNLAResponse = new AllegatoBinarioNLAResponse
                {
                    binaryData = file.FileContent,
                    fileName = file.FileName,
                    mimeType = file.MimeType
                }
            };
        }

        public AggiungiDocumentiNLAResponse AggiungiDocumentiNLA(AggiungiDocumentiNLARequest1 request)
        {
            throw new NotImplementedException();
        }

        #endregion



    }
}
