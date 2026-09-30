using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestioneFiltroInterventiAlbero;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRicercaPratiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.IOC;
using Ninject.Modules;
using Ninject.Web.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public class ConfigurazioneAreaRiservataModule : NinjectModule
    {
        public override void Load()
        {
            this.Bind<IConfigurazioneBuilder<ParametriVbg>>().To<ParametriVbgBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriDatiCatastali>>().To<ParametriDatiCatastaliBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriInvio>>().To<ParametriInvioBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriLogin>>().To<ParametriLoginBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriMenuV2>>().To<ParametriMenuBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriStc>>().To<ParametriStcBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriVisura>>().To<ParametriVisuraBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriWorkflow>>().To<ParametriWorkflowBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriSigeproSecurity>>().To<ParametriSigeproSecurityBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriScadenzario>>().To<ParametriScadenzarioBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriRegistrazione>>().To<ParametriRegistrazioneBuilder>().InRequestScope();
            //Bind<IConfigurazioneBuilder<ParametriSezioneContenuti>>().To<ParametriSezioneContenutiBuilder>();
            this.Bind<IConfigurazioneBuilder<ParametriSchedaCittadiniExtracomunitari>>().To<ParametriSchedaCittadiniExtracomunitariBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriFirmaDigitale>>().To<ParametriFirmaDigitaleBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriRicercaAnagrafiche>>().To<ParametriRicercaAnagraficheBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriAllegati>>().To<ParametriAllegatiBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriLocalizzazioni>>().To<ParametriLocalizzazioniBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriIntegrazioneLDP>>().To<ParametriIntegrazioneLDPBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriIntegrazioniDocumentali>>().To<ParametriIntegrazioniDocumentaliBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriPhantomjs>>().To<ParametriPhantomjsBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriLoghi>>().To<ParametriLoghiBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriLivornoConfigurazioneDrupal>>().To<ParametriLivornoConfigurazioneDrupalBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriUrlAreaRiservata>>().To<ParametriUrlAreaRiservataBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriGenerazioneRiepilogoDomanda>>().To<ParametriGenerazioneRiepilogoDomandaBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriARRedirect>>().To<ParametriARRedirectBuilder>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriStcConsole>>().To<ParametriStcConsoleBuilder>().InRequestScope();





            this.Bind<IConfigurazione<ParametriVbg>>().To<ConfigurazioneImpl<ParametriVbg>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriDatiCatastali>>().To<ConfigurazioneImpl<ParametriDatiCatastali>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriInvio>>().To<ConfigurazioneImpl<ParametriInvio>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriLogin>>().To<ConfigurazioneImpl<ParametriLogin>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriMenuV2>>().To<ConfigurazioneImpl<ParametriMenuV2>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriStc>>().To<ConfigurazioneImpl<ParametriStc>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriVisura>>().To<ConfigurazioneImpl<ParametriVisura>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriWorkflow>>().To<ConfigurazioneImpl<ParametriWorkflow>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriScadenzario>>().To<ConfigurazioneImpl<ParametriScadenzario>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriRegistrazione>>().To<ConfigurazioneImpl<ParametriRegistrazione>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriSigeproSecurity>>().To<ConfigurazioneImpl<ParametriSigeproSecurity>>().InRequestScope();
            //Bind<IConfigurazione<ParametriSezioneContenuti>>().To<ConfigurazioneImpl<ParametriSezioneContenuti>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriSchedaCittadiniExtracomunitari>>().To<ConfigurazioneImpl<ParametriSchedaCittadiniExtracomunitari>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriFirmaDigitale>>().To<ConfigurazioneImpl<ParametriFirmaDigitale>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriRicercaAnagrafiche>>().To<ConfigurazioneImpl<ParametriRicercaAnagrafiche>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriAllegati>>().To<ConfigurazioneImpl<ParametriAllegati>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriLocalizzazioni>>().To<ConfigurazioneImpl<ParametriLocalizzazioni>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriIntegrazioneLDP>>().To<ConfigurazioneImpl<ParametriIntegrazioneLDP>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriIntegrazioniDocumentali>>().To<ConfigurazioneImpl<ParametriIntegrazioniDocumentali>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriPhantomjs>>().To<ConfigurazioneImpl<ParametriPhantomjs>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriLoghi>>().To<ConfigurazioneImpl<ParametriLoghi>>().InRequestScope(); ;
            this.Bind<IConfigurazione<ParametriLivornoConfigurazioneDrupal>>().To<ConfigurazioneImpl<ParametriLivornoConfigurazioneDrupal>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriUrlAreaRiservata>>().To<ConfigurazioneImpl<ParametriUrlAreaRiservata>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriGenerazioneRiepilogoDomanda>>().To<ConfigurazioneImpl<ParametriGenerazioneRiepilogoDomanda>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriARRedirect>>().To<ConfigurazioneImpl<ParametriARRedirect>>().InRequestScope();
            this.Bind<IConfigurazione<ParametriStcConsole>>().To<ConfigurazioneImpl<ParametriStcConsole>>().InRequestScope();


            this.Bind<IConfigurazioneBuilder<ParametriServiziAreaRiservata>>().To<ParametriServiziAreaRiservataBuilder>().InRequestScope();
            this.Bind<IConfigurazione<ParametriServiziAreaRiservata>>().To<ConfigurazioneImpl<ParametriServiziAreaRiservata>>().InRequestScope();


            this.Bind<VbgAuthenticationService>().ToSelf().InRequestScope();

            // Precompilazione PDF
            this.Bind<IPdfUtilsService>().To<PdfUtilsService>();
            this.Bind<IPdfUtilsWsWrapper>().To<PdfUtilsWsWrapper>();

            this.Bind<ICodiceAccreditamentoHelper>().To<CodiceAccreditamentoHelper>();

            this.Bind<ISitService>().To<SigeproSitService>();
            this.Bind<ISitServiceCreator>().To<SitServiceCreator>();

            this.Bind<IResolveHttpContext>().To<ResolveHttpContext>();

            this.Bind<ILogicaSincronizzazioneAllegatiIntervento>().To<LogicaSincronizzazioneAllegatiIntervento>();

            // Conversione PDF
            this.Bind<IHtmlToPdfFileConverter>().To<PhantomjsFileConverter>();


            this.Bind<ILogicaRisoluzioneTecnico>().To<LogicaRisoluzioneTecnico>();
            this.Bind<IUserCredentialsStorage>().To<UserCredentialsStorage>();
            this.Bind<IRisorseTestualiService>().To<CachedRisorseTestualiService>().InRequestScope();

            // SIT
            this.Bind<IConfigurazione<ParametriSIT>>().To<ConfigurazioneImpl<ParametriSIT>>().InRequestScope();
            this.Bind<IConfigurazioneBuilder<ParametriSIT>>().To<ParametriSITBuilder>().InRequestScope();

            // 

            this.Bind<FiltroInterventiAlberoService>().ToSelf().InRequestScope();

            this.ConfiguraRicercaPratiche()
                .ConfiguraVerificaFirmaDigitale();
        }
    }
}
