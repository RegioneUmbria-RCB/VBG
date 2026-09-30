using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Ricerche;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRitornoScrivaniaVirtuale;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface.Imp;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.STC;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using Init.Sigepro.FrontEnd.Infrastructure.DatesAndTimes;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject.Modules;
using Ninject.Web.Common;
using System;
using System.Configuration;



namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public class IoCConfigurationModule : NinjectModule
    {

        public override void Load()
        {
            // Modalità di invio
            this.Bind<IInvioDomandaStrategy>().To<InvioDomandaSTCStrategy>();

            // Logica di salvataggio xml
            var tipoLogicaSalvataggioXmlEnum = ConfigurationManager.AppSettings["logicaSalvataggioXml"];

            var logicaSalvataggio = TipoLogicaSalvataggioXmlEnum.CachedThreaded;

            if (!String.IsNullOrEmpty(tipoLogicaSalvataggioXmlEnum))
                logicaSalvataggio = (TipoLogicaSalvataggioXmlEnum)Enum.Parse(typeof(TipoLogicaSalvataggioXmlEnum), tipoLogicaSalvataggioXmlEnum);

            switch (logicaSalvataggio)
            {
                case TipoLogicaSalvataggioXmlEnum.Default:
                    this.Bind<ISalvataggioDomandaStrategy>().To<SalvataggioDirettoStrategy>();
                    break;
                case TipoLogicaSalvataggioXmlEnum.Cached:
                    this.Bind<ISalvataggioDomandaStrategy>().To<SalvataggioCachedStrategy>();
                    break;
                default:
                    //Bind<ISalvataggioDomandaStrategy>().To<SalvataggioCachedThreadedStrategy>();
                    this.Bind<ISalvataggioDomandaStrategy>().To<SalvataggioCachedStrategy>();

                    break;
            }


            this.Bind<IInterventiRepository>().To<WsInterventiRepository>();
            this.Bind<IAlboPretorioRepository>().To<WsAlboPretorioRepository>();
            this.Bind<IAllegatiIstanzaRepository>().To<WsAllegatiIstanzaRepository>();
            this.Bind<IAllegatiDomandaFoRepository>().To<WsAllegatiDomandaFoRepository>();
            this.Bind<IAnagraficheRepository>().To<WsAnagraficheRepository>();
            this.Bind<IAtecoRepository>().To<WsAtecoRepository>();
            this.Bind<ICampiRicercaVisuraRepository>().To<WsCampiRicercaVisuraRepository>();
            this.Bind<ICartRepository>().To<WsCartRepository>();
            this.Bind<IComuniRepository>().To<WsComuniRepository>();
            this.Bind<IConfigurazioneContenutiRepository>().To<WsConfigurazioneContenutiRepository>();
            this.Bind<IConfigurazioneAreaRiservataRepository>().To<WsConfigurazioneAreaRiservataRepository>();
            this.Bind<IConfigurazioneVbgRepository>().To<WsConfigurazioneVbgRepository>();
            this.Bind<IDatiDinamiciRepository>().To<WsDatiDinamiciRepository>().InRequestScope();
            this.Bind<IModelliDinamiciService>().To<ModelliDinamiciService>().InRequestScope();
            this.Bind<IRicercheDatiDinamiciService>().To<RicercheDatiDinamiciService>();
            this.Bind<IDatiDomandaFoRepository>().To<WsDatiDomandaFoRepository>();
            this.Bind<IDomandeEndoRepository>().To<WsDomandeEndoRepository>();
            this.Bind<IElenchiProfessionaliRepository>().To<WsElenchiProfessionaliRepository>();
            this.Bind<IAllegatiEndoprocedimentiRepository>().To<WsAllegatiEndoprocedimentiRepository>();
            this.Bind<IEndoprocedimentiRepository>().To<WsEndoprocedimentiRepository>();
            this.Bind<IFormeGiuridicheRepository>().To<WsFormeGiuridicheRepository>();
            this.Bind<IInterventiAllegatiRepository>().To<WsInterventiAllegatiRepository>();
            this.Bind<IIstanzePresentateRepository>().To<WsIstanzePresentateRepository>();
            //			Bind<IMovimentiRepository>().To<WsMovimentiRepository>();
            this.Bind<IMessaggiFrontofficeRepository>().To<WsMessaggiFrontofficeRepository>();
            this.Bind<ICurrentDateTimeProvider>().To<CurrentDateTimeProvider>().InTransientScope();
            this.Bind<IOneriRepository>().To<OneriRepository>();
            //Bind<IScadenzeRepository>().To<ScadenzeRepository>();
            this.Bind<ISoftwareRepository>().To<WsSoftwareRepository>();
            this.Bind<ISottoscrizioniRepository>().To<WsSottoscrizioniRepository>();
            this.Bind<IStatiIstanzaRepository>().To<WsStatiIstanzaRepository>();
            this.Bind<IStradarioRepository>().To<WsStradarioRepository>();
            this.Bind<ITipiSoggettoRepository>().To<TipiSoggettoRepository>();
            this.Bind<ITitoliRepository>().To<WsTitoliRepository>();
            //Bind<IVisuraRepository>().To<WsVisuraRepository>().InRequestScope();

            this.Bind<IComuniService>().To<ComuniService>();
            this.Bind<ICittadinanzeService>().To<CittadinanzeService>();
            this.Bind<ITipiSoggettoService>().To<TipiSoggettoService>();
            this.Bind<IAllegatiEndoprocedimentiService>().To<AllegatiEndoprocedimentiService>();
            this.Bind<ILogicaSincronizzazioneTipiSoggetto>().To<LogicaSincronizzazioneTipiSoggetto>();
            this.Bind<IAnagraficheService>().To<AnagraficheService>();
            //Bind<IMovimentiService>().To<MovimentiService>();
            // File converter


            // Verifica firma digitale

            this.Bind<IAliasResolver>().To<QuerystringAliasResolver>().InRequestScope();
            this.Bind<IAliasSoftwareResolver>().To<QuerystringAliasSoftwareResolver>().InRequestScope();
            this.Bind<ISoftwareResolver>().To<QuerystringAliasSoftwareResolver>().InRequestScope();
            this.Bind<IAuthenticationDataResolver>().To<ContextAuthenticationDataResolver>().InRequestScope();
            this.Bind<IIdDomandaResolver>().To<QuerystringIdDomandaResolver>().InRequestScope();
            this.Bind<ITokenResolver>().To<IdentityTokenResolver>().InRequestScope();


            // Services

            this.Bind<LocalizzazioniService>().ToSelf();
            this.Bind<DomandeOnlineService>().ToSelf();
            this.Bind<IEndoprocedimentiService>().To<EndoprocedimentiService>();
            this.Bind<EventiDomandaService>().ToSelf();
            this.Bind<InvioDomandaService>().ToSelf();
            this.Bind<ProcureService>().ToSelf();

            this.Bind<FileConverterService>().ToSelf();

            this.Bind<ITokenApplicazioneService>().To<TokenApplicazioneService>();
            this.Bind<IAliasToIdComuneTranslator>().To<AliasToIdComuneTranslator>();


            this.Bind<IReadFacade>().To<ReadFacadeImp>().InRequestScope();
            this.Bind<IReadDatiDomanda>().To<ReadFacadeImp>().InRequestScope();
            this.Bind<GeneratoreCertificatoDiInvio>().ToSelf();
            this.Bind<CertificatoDiInvioAllegato>().ToSelf();

            // Ricerca del riepilogo della domanda online
            this.Bind<IndividuazioneCertificatoInvioDaConfigurazione>().ToSelf();
            this.Bind<IndividuazioneCertificatoInvioDaProcedura>().ToSelf();
            this.Bind<IstanzaStcAdapter>().ToSelf();
            this.Bind<IStrategiaIndividuazioneCertificatoInvio>().To<IndividuazioneCertificatoInvioDaProceduraOConfigurazione>();


            this.Bind<IMessaggioErroreInvioService>().To<MessaggioErroreInvioService>();
            this.Bind<IStcService>().To<StcServiceImpl>();
            this.Bind<IIstanzaStcAdapter>().To<IstanzaStcAdapter>();

            // Segnaposto schede dinamiche
            this.Bind<ISostituzioneSegnapostoRiepilogoService>().To<SostituzioneSegnapostoRiepilogoService>().InRequestScope();
            this.Bind<IGeneratoreHtmlSchedeDinamiche>().To<GeneratoreHtmlSchedeDinamiche>().InRequestScope();




            this.Bind<IStrutturaModelloReader>().To<StrutturaModelloReader>();



            this.Bind<IEndoprocedimentiIncompatibiliService>().To<EndoprocedimentiIncompatibiliService>();
            this.Bind<IEndoprocedimentiIncompatibiliRepository>().To<WsEndoprocedimentiRepository>();

            this.Bind<ILogicaSincronizzazioneOneri>().To<LogicaSincronizzazioneOneri>();

            this.Bind<IInpsInailService>().To<InpsInailService>();

            this.Bind<IResolveDescrizioneIntervento>().To<ResolveDescrizioneIntervento>();
            this.Bind<IBookmarksService>().To<BookmarksService>();


            this.Bind<ILocalizzazioniService>().To<LocalizzazioniService>();

            // Dati dinamici della visura
            this.Bind<IVisuraDatiDinamiciService>().To<VisuraDatiDinamiciService>().InRequestScope();
            this.Bind<VisuraDyn2ModelliManager>().ToSelf().InRequestScope();
            this.Bind<VisuraIstanzeDyn2DatiManager>().ToSelf().InRequestScope();

            this.Bind<IUrlEncoder>().To<HttpContextUrlEncoder>().InRequestScope();

            // Redirect a fine presentazione  e copia dati domanda
            this.Bind<IRedirectFineDomandaService>().To<RedirectFineDomandaService>().InRequestScope();
            this.Bind<ICopiaDatiDomandaService>().To<CopiaDatiDomandaService>().InRequestScope();

            this.Bind<IVisuraIstanzeServiceCreator>().To<VisuraIstanzeServiceCreator>().InRequestScope();
            this.Bind<RitornoScrivaniaVirtualeService>().ToSelf().InRequestScope();

            this.Bind<IGuidWrapperService>().To<GuidServiceWrapper>();
            this.Bind<IIdPresentazioneResolver>().To<IdPresentazioneFromQuerystringResolver>().InRequestScope();

            this.Kernel.RegistraIntegrazionePagamenti()
                  .RegistraGestioneOggetti();

        }
    }
}
