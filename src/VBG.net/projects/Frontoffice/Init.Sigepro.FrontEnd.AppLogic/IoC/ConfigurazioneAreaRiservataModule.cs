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

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
	public class ConfigurazioneAreaRiservataModule : NinjectModule
	{
		public override void Load()
		{
			Bind<IConfigurazioneBuilder<ParametriAspetto>>().To<ParametriAspettoBuilder>();
			Bind<IConfigurazioneBuilder<ParametriDatiCatastali>>().To<ParametriDatiCatastaliBuilder>();
			Bind<IConfigurazioneBuilder<ParametriInvio>>().To<ParametriInvioBuilder>();
			Bind<IConfigurazioneBuilder<ParametriLogin>>().To<ParametriLoginBuilder>();
			Bind<IConfigurazioneBuilder<ParametriMenu>>().To<ParametriMenuBuilder>();
			Bind<IConfigurazioneBuilder<ParametriStc>>().To<ParametriStcBuilder>();
			Bind<IConfigurazioneBuilder<ParametriVisura>>().To<ParametriVisuraBuilder>();
			Bind<IConfigurazioneBuilder<ParametriWorkflow>>().To<ParametriWorkflowBuilder>();
			Bind<IConfigurazioneBuilder<ParametriSigeproSecurity>>().To<ParametriSigeproSecurityBuilder>();
			Bind<IConfigurazioneBuilder<ParametriScadenzario>>().To<ParametriScadenzarioBuilder>();
			Bind<IConfigurazioneBuilder<ParametriRegistrazione>>().To<ParametriRegistrazioneBuilder>();
			//Bind<IConfigurazioneBuilder<ParametriSezioneContenuti>>().To<ParametriSezioneContenutiBuilder>();
			Bind<IConfigurazioneBuilder<ParametriSchedaCittadiniExtracomunitari>>().To<ParametriSchedaCittadiniExtracomunitariBuilder>();
			Bind<IConfigurazioneBuilder<ParametriFirmaDigitale>>().To<ParametriFirmaDigitaleBuilder>();
			Bind<IConfigurazioneBuilder<ParametriRicercaAnagrafiche>>().To<ParametriRicercaAnagraficheBuilder>();
			Bind<IConfigurazioneBuilder<ParametriAllegati>>().To<ParametriAllegatiBuilder>();


			Bind<IConfigurazione<ParametriAspetto>>().To<ConfigurazioneImpl<ParametriAspetto>>().InRequestScope();
			Bind<IConfigurazione<ParametriDatiCatastali>>().To<ConfigurazioneImpl<ParametriDatiCatastali>>().InRequestScope();
			Bind<IConfigurazione<ParametriInvio>>().To<ConfigurazioneImpl<ParametriInvio>>().InRequestScope();
			Bind<IConfigurazione<ParametriLogin>>().To<ConfigurazioneImpl<ParametriLogin>>().InRequestScope();
			Bind<IConfigurazione<ParametriMenu>>().To<ConfigurazioneImpl<ParametriMenu>>().InRequestScope();
			Bind<IConfigurazione<ParametriStc>>().To<ConfigurazioneImpl<ParametriStc>>().InRequestScope();
			Bind<IConfigurazione<ParametriVisura>>().To<ConfigurazioneImpl<ParametriVisura>>().InRequestScope();
			Bind<IConfigurazione<ParametriWorkflow>>().To<ConfigurazioneImpl<ParametriWorkflow>>().InRequestScope();
			Bind<IConfigurazione<ParametriScadenzario>>().To<ConfigurazioneImpl<ParametriScadenzario>>().InRequestScope();
			Bind<IConfigurazione<ParametriRegistrazione>>().To<ConfigurazioneImpl<ParametriRegistrazione>>().InRequestScope();
			Bind<IConfigurazione<ParametriSigeproSecurity>>().To<ConfigurazioneImpl<ParametriSigeproSecurity>>().InRequestScope();
			//Bind<IConfigurazione<ParametriSezioneContenuti>>().To<ConfigurazioneImpl<ParametriSezioneContenuti>>().InRequestScope();
			Bind<IConfigurazione<ParametriSchedaCittadiniExtracomunitari>>().To<ConfigurazioneImpl<ParametriSchedaCittadiniExtracomunitari>>().InRequestScope();
			Bind<IConfigurazione<ParametriFirmaDigitale>>().To<ConfigurazioneImpl<ParametriFirmaDigitale>>().InRequestScope();
			Bind<IConfigurazione<ParametriRicercaAnagrafiche>>().To<ConfigurazioneImpl<ParametriRicercaAnagrafiche>>().InRequestScope();
			Bind<IConfigurazione<ParametriAllegati>>().To<ConfigurazioneImpl<ParametriAllegati>>().InRequestScope();

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
		}
	}
}
