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
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.ESED;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRitornoScrivaniaVirtuale;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface.Imp;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Cart;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.STC;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Pagamenti;
using Init.Sigepro.FrontEnd.Pagamenti.ESED;
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
			Bind<IInvioDomandaStrategy>().To<InvioDomandaSTCStrategy>();

			// Logica di salvataggio xml
			var tipoLogicaSalvataggioXmlEnum = ConfigurationManager.AppSettings["logicaSalvataggioXml"];

			var logicaSalvataggio = TipoLogicaSalvataggioXmlEnum.CachedThreaded;
			
			if(!String.IsNullOrEmpty(tipoLogicaSalvataggioXmlEnum))
				logicaSalvataggio = (TipoLogicaSalvataggioXmlEnum)Enum.Parse(typeof(TipoLogicaSalvataggioXmlEnum), tipoLogicaSalvataggioXmlEnum);

			switch (logicaSalvataggio)
			{
				case TipoLogicaSalvataggioXmlEnum.Default:
					Bind<ISalvataggioDomandaStrategy>().To<SalvataggioDirettoStrategy>();
					break;
				case TipoLogicaSalvataggioXmlEnum.Cached:
					Bind<ISalvataggioDomandaStrategy>().To<SalvataggioCachedStrategy>();
					break;
				default:
					//Bind<ISalvataggioDomandaStrategy>().To<SalvataggioCachedThreadedStrategy>();
					Bind<ISalvataggioDomandaStrategy>().To<SalvataggioCachedStrategy>();
					
					break;
			}


			Bind<IInterventiRepository>().To<WsInterventiRepository>();
			Bind<IAlboPretorioRepository>().To<WsAlboPretorioRepository>();
			Bind<IAllegatiIstanzaRepository>().To<WsAllegatiIstanzaRepository>();
			Bind<IAllegatiDomandaFoRepository>().To<WsAllegatiDomandaFoRepository>();
			Bind<IAnagraficheRepository>().To<WsAnagraficheRepository>();
			Bind<IAtecoRepository>().To<WsAtecoRepository>();
			Bind<ICampiRicercaVisuraRepository>().To<WsCampiRicercaVisuraRepository>();
			Bind<ICartRepository>().To<WsCartRepository>();
			Bind<IComuniRepository>().To<WsComuniRepository>();
			Bind<IConfigurazioneContenutiRepository>().To<WsConfigurazioneContenutiRepository>();
			Bind<IConfigurazioneAreaRiservataRepository>().To<WsConfigurazioneAreaRiservataRepository>();
			Bind<IConfigurazioneVbgRepository>().To<WsConfigurazioneVbgRepository>();
			Bind<IDatiDinamiciRepository>().To<WsDatiDinamiciRepository>().InRequestScope();
            Bind<IModelliDinamiciService>().To<ModelliDinamiciService>().InRequestScope();
			Bind<IRicercheDatiDinamiciService>().To<RicercheDatiDinamiciService>();
			Bind<IDatiDomandaFoRepository>().To<WsDatiDomandaFoRepository>();
			Bind<IDomandeEndoRepository>().To<WsDomandeEndoRepository>();
			Bind<IElenchiProfessionaliRepository>().To<WsElenchiProfessionaliRepository>();
			Bind<IAllegatiEndoprocedimentiRepository>().To<WsAllegatiEndoprocedimentiRepository>();
			Bind<IEndoprocedimentiRepository>().To<WsEndoprocedimentiRepository>();
			Bind<IFormeGiuridicheRepository>().To<WsFormeGiuridicheRepository>();
			Bind<IInterventiAllegatiRepository>().To<WsInterventiAllegatiRepository>();
			Bind<IIstanzePresentateRepository>().To<WsIstanzePresentateRepository>();
//			Bind<IMovimentiRepository>().To<WsMovimentiRepository>();
			Bind<IMessaggiFrontofficeRepository>().To<WsMessaggiFrontofficeRepository>();
			Bind<IOggettiRepository>().To<WsOggettiRepository>();
			Bind<IOneriRepository>().To<OneriRepository>();
			//Bind<IScadenzeRepository>().To<ScadenzeRepository>();
			Bind<ISoftwareRepository>().To<WsSoftwareRepository>();
			Bind<ISottoscrizioniRepository>().To<WsSottoscrizioniRepository>();
			Bind<IStatiIstanzaRepository>().To<WsStatiIstanzaRepository>();
			Bind<IStradarioRepository>().To<WsStradarioRepository>();
			Bind<ITipiSoggettoRepository>().To<TipiSoggettoRepository>();
			Bind<ITitoliRepository>().To<WsTitoliRepository>();
			//Bind<IVisuraRepository>().To<WsVisuraRepository>().InRequestScope();
		
			Bind<IOggettiService>().To<OggettiService>();
			Bind<IComuniService>().To<ComuniService>();
			Bind<ICittadinanzeService>().To<CittadinanzeService>();
			Bind<ITipiSoggettoService>().To<TipiSoggettoService>();
			Bind<IAllegatiEndoprocedimentiService>().To<AllegatiEndoprocedimentiService>();
			Bind<ILogicaSincronizzazioneTipiSoggetto>().To<LogicaSincronizzazioneTipiSoggetto>();
			Bind<IAnagraficheService>().To<AnagraficheService>();
			//Bind<IMovimentiService>().To<MovimentiService>();
			// File converter
			

			// Verifica firma digitale

			Bind<IAliasResolver>().To<QuerystringAliasResolver>().InRequestScope();
			Bind<IAliasSoftwareResolver>().To<QuerystringAliasSoftwareResolver>().InRequestScope();
            Bind<ISoftwareResolver>().To<QuerystringAliasSoftwareResolver>().InRequestScope();
            Bind<IAuthenticationDataResolver>().To<ContextAuthenticationDataResolver>().InRequestScope();
			Bind<IIdDomandaResolver>().To<QuerystringIdDomandaResolver>().InRequestScope();
            Bind<ITokenResolver>().To<IdentityTokenResolver>().InRequestScope();


            // Services

            Bind<LocalizzazioniService>().ToSelf();
			Bind<DomandeOnlineService>().ToSelf();
			Bind<IEndoprocedimentiService>().To<EndoprocedimentiService>();
			Bind<EventiDomandaService>().ToSelf();
			Bind<InvioDomandaService>().ToSelf();
			Bind<ProcureService>().ToSelf();

			Bind<FileConverterService>().ToSelf();

			Bind<ITokenApplicazioneService>().To<TokenApplicazioneService>();
			Bind<IAliasToIdComuneTranslator>().To<AliasToIdComuneTranslator>();


			Bind<IReadFacade>().To<ReadFacadeImp>().InRequestScope();
            Bind<IReadDatiDomanda>().To<ReadFacadeImp>().InRequestScope();
			Bind<GeneratoreCertificatoDiInvio>().ToSelf();
			Bind<CertificatoDiInvioAllegato>().ToSelf();

			// Ricerca del riepilogo della domanda online
			Bind<IndividuazioneCertificatoInvioDaConfigurazione>().ToSelf();
			Bind<IndividuazioneCertificatoInvioDaProcedura>().ToSelf();
			Bind<IstanzaStcAdapter>().ToSelf();
			Bind<IStrategiaIndividuazioneCertificatoInvio>().To<IndividuazioneCertificatoInvioDaProceduraOConfigurazione>();


			Bind<IMessaggiNotificaInvioService>().To<MessaggiNotificaInvioService>();
			Bind<IMessaggioErroreInvioService>().To<MessaggioErroreInvioService>();
			Bind<IStcService>().To<StcServiceImpl>();
			Bind<IIstanzaStcAdapter>().To<IstanzaStcAdapter>();

			// CART
			Bind<IFacctRedirectService>().To<FacctRedirectService>();

            // Segnaposto schede dinamiche
			Bind<ISostituzioneSegnapostoRiepilogoService>().To<SostituzioneSegnapostoRiepilogoService>().InRequestScope();
            Bind<IGeneratoreHtmlSchedeDinamiche>().To<GeneratoreHtmlSchedeDinamiche>().InRequestScope();

			Bind<IFirmaDigitaleMetadataService>().To<VerificaFirmaDigitaleService>();
			Bind<IVerificaFirmaDigitaleService>().To<VerificaFirmaDigitaleService>();


			Bind<IStrutturaModelloReader>().To<StrutturaModelloReader>();
			

			
			Bind<IEndoprocedimentiIncompatibiliService>().To<EndoprocedimentiIncompatibiliService>();
			Bind<IEndoprocedimentiIncompatibiliRepository>().To<WsEndoprocedimentiRepository>();

			Bind<ILogicaSincronizzazioneOneri>().To<LogicaSincronizzazioneOneri>();

            Bind<IInpsInailService>().To<InpsInailService>();

            Bind<IResolveDescrizioneIntervento>().To<ResolveDescrizioneIntervento>();
            Bind<IBookmarksService>().To<BookmarksService>();


            Bind<ILocalizzazioniService>().To<LocalizzazioniService>();


            Bind<IPortaleCittadinoService>().To<PortaleCittadinoService>().InRequestScope();

            // Dati dinamici della visura
            Bind<IVisuraDatiDinamiciService>().To<VisuraDatiDinamiciService>().InRequestScope();
            Bind<VisuraDyn2ModelliManager>().ToSelf().InRequestScope();
            Bind<VisuraIstanzeDyn2DatiManager>().ToSelf().InRequestScope();

            // Pagamenti
            Bind<IUrlEncoder>().To<HttpContextUrlEncoder>().InRequestScope();
            Bind<IGetStatoPagamento>().To<GetStatoPagamentoESED>().InRequestScope();
            Bind<PayServerClientWrapperESED>().ToMethod(x =>
            {

                var settingsReader = (IPagamentiSettingsReader)x.Kernel.GetService(typeof(IPagamentiSettingsReader));
                var urlEncoder = (IUrlEncoder)x.Kernel.GetService(typeof(IUrlEncoder));
                var getStatoPagamento = (IGetStatoPagamento)x.Kernel.GetService(typeof(IGetStatoPagamento));

                return new PayServerClientWrapperESED(new PayServerClientSettings(settingsReader.GetSettings()), urlEncoder, getStatoPagamento);
            }).InRequestScope();

            // Redirect a fine presentazione  e copia dati domanda
            Bind<IRedirectFineDomandaService>().To<RedirectFineDomandaService>().InRequestScope();
            Bind<ICopiaDatiDomandaService>().To<CopiaDatiDomandaService>().InRequestScope();

            Bind<IVisuraIstanzeServiceCreator>().To<VisuraIstanzeServiceCreator>().InRequestScope();
            Bind<RitornoScrivaniaVirtualeService>().ToSelf().InRequestScope();
        }
	}
}
