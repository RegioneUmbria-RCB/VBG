// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiNinjectModule.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.NinjectModule
{
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
	using Ninject.Modules;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;
	using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche;

	/// <summary>
	/// Modulo ninject per la configurazione del componente di gestione movimenti
	/// </summary>
	public class GestioneMovimentiNinjectModule : NinjectModule
	{
		public override void Load()
		{
			Bind<IUnitOfWork<GestioneMovimentiDataStore>>().To<GestioneMovimentiHttpDataContext>();
			Bind<IIdMovimentoResolver>().To<IdMovimentoQuerystringResolver>();
			Bind<IScadenzeService>().To<ScadenzeService>();
			Bind<IMovimentiBackofficeService>().To<MovimentiBackofficeService>();
			Bind<IGestioneMovimentiDataContext>().To<GestioneMovimentiHttpDataContext>();
			Bind<IMovimentiReadRepository>().To<MovimentiReadRepository>();
			Bind<MovimentiBackofficeServiceCreator>().ToSelf();
			Bind<ITrasmissioneMovimentoService>().To<TrasmissioneMovimentoService>();
			Bind<GestioneMovimentiBootstrapper.GestioneMovimentiBootstrapperSettings>().ToSelf();
			Bind<IGenerazioneRiepilogoSchedeDinamicheService>().To<GenerazioneRiepilogoSchedeDinamicheService>();
			
			// View models
			Bind<RiepilogoMovimentoDiOrigineViewModel>().ToSelf();
			Bind<RiepilogoMovimentoDaEffettuareViewModel>().ToSelf();
		}
	}
}
