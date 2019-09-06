using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using Ninject.Modules;
using Ninject.Web.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel;
using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public class ConfigurazioneAreaRiservataModule : NinjectModule
    {
        public override void Load()
        {
            Bind<IConfigurazioneBuilder<ParametriVbg>>().To<ParametriVbgBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriDatiCatastali>>().To<ParametriDatiCatastaliBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriInvio>>().To<ParametriInvioBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriLogin>>().To<ParametriLoginBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriMenuV2>>().To<ParametriMenuBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriStc>>().To<ParametriStcBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriVisura>>().To<ParametriVisuraBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriWorkflow>>().To<ParametriWorkflowBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriSigeproSecurity>>().To<ParametriSigeproSecurityBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriScadenzario>>().To<ParametriScadenzarioBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriRegistrazione>>().To<ParametriRegistrazioneBuilder>().InRequestScope();
            //Bind<IConfigurazioneBuilder<ParametriSezioneContenuti>>().To<ParametriSezioneContenutiBuilder>();
            Bind<IConfigurazioneBuilder<ParametriSchedaCittadiniExtracomunitari>>().To<ParametriSchedaCittadiniExtracomunitariBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriCart>>().To<ParametriCartBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriFirmaDigitale>>().To<ParametriFirmaDigitaleBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriRicercaAnagrafiche>>().To<ParametriRicercaAnagraficheBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriAllegati>>().To<ParametriAllegatiBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriConfigurazionePagamentiMIP>>().To<ParametriPagamentiMipServiceBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriLocalizzazioni>>().To<ParametriLocalizzazioniBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriIntegrazioneLDP>>().To<ParametriIntegrazioneLDPBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriIntegrazioniDocumentali>>().To<ParametriIntegrazioniDocumentaliBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriPhantomjs>>().To<ParametriPhantomjsBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriLoghi>>().To<ParametriLoghiBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriLivornoConfigurazioneDrupal>>().To<ParametriLivornoConfigurazioneDrupalBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriUrlAreaRiservata>>().To<ParametriUrlAreaRiservataBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriGenerazioneRiepilogoDomanda>>().To<ParametriGenerazioneRiepilogoDomandaBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriARRedirect>>().To<ParametriARRedirectBuilder>().InRequestScope();
            Bind<IConfigurazioneBuilder<ParametriStcAidaSmart>>().To<ParametriStcAidaSmartBuilder>().InRequestScope();
            



            Bind<IConfigurazione<ParametriVbg>>().To<ConfigurazioneImpl<ParametriVbg>>().InRequestScope();
            Bind<IConfigurazione<ParametriDatiCatastali>>().To<ConfigurazioneImpl<ParametriDatiCatastali>>().InRequestScope();
            Bind<IConfigurazione<ParametriInvio>>().To<ConfigurazioneImpl<ParametriInvio>>().InRequestScope();
            Bind<IConfigurazione<ParametriLogin>>().To<ConfigurazioneImpl<ParametriLogin>>().InRequestScope();
            Bind<IConfigurazione<ParametriMenuV2>>().To<ConfigurazioneImpl<ParametriMenuV2>>().InRequestScope();
            Bind<IConfigurazione<ParametriStc>>().To<ConfigurazioneImpl<ParametriStc>>().InRequestScope();
            Bind<IConfigurazione<ParametriVisura>>().To<ConfigurazioneImpl<ParametriVisura>>().InRequestScope();
            Bind<IConfigurazione<ParametriWorkflow>>().To<ConfigurazioneImpl<ParametriWorkflow>>().InRequestScope();
            Bind<IConfigurazione<ParametriScadenzario>>().To<ConfigurazioneImpl<ParametriScadenzario>>().InRequestScope();
            Bind<IConfigurazione<ParametriRegistrazione>>().To<ConfigurazioneImpl<ParametriRegistrazione>>().InRequestScope();
            Bind<IConfigurazione<ParametriSigeproSecurity>>().To<ConfigurazioneImpl<ParametriSigeproSecurity>>().InRequestScope();
            //Bind<IConfigurazione<ParametriSezioneContenuti>>().To<ConfigurazioneImpl<ParametriSezioneContenuti>>().InRequestScope();
            Bind<IConfigurazione<ParametriSchedaCittadiniExtracomunitari>>().To<ConfigurazioneImpl<ParametriSchedaCittadiniExtracomunitari>>().InRequestScope();
            Bind<IConfigurazione<ParametriCart>>().To<ConfigurazioneImpl<ParametriCart>>().InRequestScope();
            Bind<IConfigurazione<ParametriFirmaDigitale>>().To<ConfigurazioneImpl<ParametriFirmaDigitale>>().InRequestScope();
            Bind<IConfigurazione<ParametriRicercaAnagrafiche>>().To<ConfigurazioneImpl<ParametriRicercaAnagrafiche>>().InRequestScope();
            Bind<IConfigurazione<ParametriAllegati>>().To<ConfigurazioneImpl<ParametriAllegati>>().InRequestScope();
            Bind<IConfigurazione<ParametriConfigurazionePagamentiMIP>>().To<ConfigurazioneImpl<ParametriConfigurazionePagamentiMIP>>().InRequestScope();
            Bind<IConfigurazione<ParametriLocalizzazioni>>().To<ConfigurazioneImpl<ParametriLocalizzazioni>>().InRequestScope();
            Bind<IConfigurazione<ParametriIntegrazioneLDP>>().To<ConfigurazioneImpl<ParametriIntegrazioneLDP>>().InRequestScope();
            Bind<IConfigurazione<ParametriIntegrazioniDocumentali>>().To<ConfigurazioneImpl<ParametriIntegrazioniDocumentali>>().InRequestScope();
            Bind<IConfigurazione<ParametriPhantomjs>>().To<ConfigurazioneImpl<ParametriPhantomjs>>().InRequestScope();
            Bind<IConfigurazione<ParametriLoghi>>().To<ConfigurazioneImpl<ParametriLoghi>>().InRequestScope();;
            Bind<IConfigurazione<ParametriLivornoConfigurazioneDrupal>>().To<ConfigurazioneImpl<ParametriLivornoConfigurazioneDrupal>>().InRequestScope();
            Bind<IConfigurazione<ParametriUrlAreaRiservata>>().To<ConfigurazioneImpl<ParametriUrlAreaRiservata>>().InRequestScope();
            Bind<IConfigurazione<ParametriGenerazioneRiepilogoDomanda>>().To< ConfigurazioneImpl<ParametriGenerazioneRiepilogoDomanda>>().InRequestScope();
            Bind<IConfigurazione<ParametriARRedirect>>().To<ConfigurazioneImpl<ParametriARRedirect>>().InRequestScope();
            Bind<IConfigurazione<ParametriStcAidaSmart>>().To<ConfigurazioneImpl<ParametriStcAidaSmart>>().InRequestScope();


            Bind<VbgAuthenticationService>().ToSelf().InRequestScope();

            // Precompilazione PDF
            Bind<IPdfUtilsService>().To<PdfUtilsService>();
            Bind<IPdfUtilsWsWrapper>().To<PdfUtilsWsWrapper>();

            Bind<ICodiceAccreditamentoHelper>().To<CodiceAccreditamentoHelper>();

            Bind<ISitService>().To<SigeproSitService>();
            Bind<ISitServiceCreator>().To<SitServiceCreator>();

            // Bandi regione Umbria
            Bind<IConfigurazioneValidazioneReader>().To<ConfigurazioneValidazioneReader>();
            Bind<IBandiUmbriaService>().To<BandiUmbriaService>();
            Bind<IValidazioneBandoA1Service>().To<ValidazioneBandoA1Service>();
            Bind<IValidazioneBandoB1Service>().To<ValidazioneBandoB1Service>();
            Bind<IDatiProgettoReader>().To<DatiProgettoReader>();

            Bind<IBandiIncomingService>().To<BandiIncomingService>();
            Bind<IValidazioneBandoIncomingService>().To<ValidazioneBandoIncomingService>();


            Bind<IRegoleRepository>().To<RegoleRepository>();
            Bind<IDatiDinamiciExcelService>().To<DatiDinamiciExcelService>();

            Bind<IResolveHttpContext>().To<ResolveHttpContext>();

            Bind<ILogicaSincronizzazioneAllegatiIntervento>().To<LogicaSincronizzazioneAllegatiIntervento>();

            // Conversione PDF
            Bind<IHtmlToPdfFileConverter>().To<PhantomjsFileConverter>();


            Bind<ILogicaRisoluzioneTecnico>().To<LogicaRisoluzioneTecnico>();
            Bind<IUserCredentialsStorage>().To<UserCredentialsStorage>();
            Bind<IRisorseTestualiService>().To<CachedRisorseTestualiService>().InRequestScope();
        }
    }
}
