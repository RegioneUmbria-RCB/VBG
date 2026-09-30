using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript.API;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.Common;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.Navigation;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.Stc.Adapters;
using Init.Sigepro.FrontEnd.WebForms.ConversionePDF.Ghostscript;
using Init.Sigepro.FrontEnd.WebForms.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.WebForms.GestioneUrl;
using Init.SIGePro.DatiDinamici;
using Ninject.Modules;
using Ninject.Web.Common;
using System;
using System.Configuration;
using VBG.DatiDinamici;

namespace Init.Sigepro.FrontEnd.IoC
{
    public class FrontendNinjectModule : NinjectModule
    {
        public override void Load()
        {
            // Condizioni di ingresso
            this.Bind<CondizioneIngressoGestioneSottoscrittori>().ToSelf();
            this.Bind<CondizioneIngressoStepSempreVera>().ToSelf();

            // Condizioni di uscita
            this.Bind<CondizioneUscitaStepSempreVera>().ToSelf();
            this.Bind<CondizioneUscitaGestioneAnagraficheBase>().ToSelf();
            this.Bind<CondizioniUscitaGestioneAnagrafiche>().ToSelf();
            this.Bind<CondizioniUscitaGestioneAnagraficheSemplificata>().ToSelf();
            this.Bind<CondizioneUscitaPrivacyAccettata>().ToSelf();

            // Autenticazione
            this.Bind<ITokenResolver>().To<FormsAuthenticationService>().InRequestScope();
            this.Bind<IAreaRiservataAuthenticationService>().To<FormsAuthenticationService>().InRequestScope();
            this.Bind<IAuthenticationDataResolver>().To<ContextAuthenticationDataResolver>().InRequestScope();

            // Alias/software
            this.Bind<IAliasResolver>().To<QuerystringAliasResolver>().InRequestScope();
            this.Bind<IAliasSoftwareResolver>().To<QuerystringAliasSoftwareResolver>().InRequestScope();
            this.Bind<ISoftwareResolver>().To<QuerystringAliasSoftwareResolver>().InRequestScope();
            this.Bind<IAliasSoftwareSetter>().To<ContextAliasSoftwareSetter>().InRequestScope();

            // Domanda
            this.Bind<IIdDomandaResolver>().To<QuerystringIdDomandaResolver>().InRequestScope();
            //this.Bind<IIdPresentazioneResolver>().To<IdPresentazioneFromQuerystringResolver>().InRequestScope();

            // Navigazione
            this.Bind<IRedirectService>().To<RedirectService>().InRequestScope();

            // Gestione Oggetti
            this.Bind<IPostedFileSpecificationFactory>().To<PostedFileSpecificationFactory>().InRequestScope();

            // Gestione movimenti
            this.Bind<IIdMovimentoResolver>().To<IdMovimentoQuerystringResolver>().InRequestScope();

            // Configurazione
            this.Bind<IAppConfigurationReader>().To<WebConfigReader>().InRequestScope();
            // STC
            this.Bind<ICondizioneAttivazioneDatiDinamiciAdapter>().To<CondizioneAttivazioneDatiDinamiciAdapter>().InRequestScope();

            // Strategia di salvataggio
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

            // Dati dinamici
            this.Bind<IModelloDinamicoHtmlRenderer>().To<ModelloDinamicoHtmlRenderer>().InTransientScope();

            // Path base
            this.Bind<IBaseUrl>().To<FrameworkBaseUrl>().InRequestScope();

            // Conversione files in pdf/a tramite ghostscript
            this.Bind<IGhostscriptAPI>().ToMethod(context =>
            {
                if (IntPtr.Size == 4)
                    return new GhostscriptAPI32();
                return new GhostscriptAPI64();
            }).InSingletonScope();
        }
    }
}