// -----------------------------------------------------------------------
// <copyright file="BariNinjectModule.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Ninject.Modules;
	using Init.Sigepro.FrontEnd.Bari;
	using Init.Sigepro.FrontEnd.Bari.CID;
	using Init.Sigepro.FrontEnd.Bari.Core;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares.Configurazione;
	using Init.Sigepro.FrontEnd.Bari.TARES;
	using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
	using Init.Sigepro.FrontEnd.Bari.Core.CafServices;
	using Init.Sigepro.FrontEnd.Bari.TASI;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tasi;
	using Init.Sigepro.FrontEnd.Bari.IMU;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Cid;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BariNinjectModule : NinjectModule
	{
		public override void Load()
		{
			Bind<IToken>().To<BariTokenProvider>();
			Bind<ISoftware>().To<BariSoftwareProvider>();
			Bind<IConfigServiceUrl>().To<ConfigurazioneServizioConfigBari>();
			Bind<IConfigurazioneBuilder<ConfigurazioneServizioConfigBari>>().To<ConfigurazioneServizioConfigBuilder>();
			
			Bind<ICidConfigService>().To<ParametriConfigurazioneService>();
			Bind<IBariCidService>().To<BariCidService>();


			Bind<ICafServiceUrl>().To<ConfigurazioneServizioTares>();
			Bind<IConfigurazioneBuilder<ConfigurazioneServizioTares>>().To<ConfigurazioneServizioTaresBuilder>();

			Bind<IUtenzeServiceProxy>().To<UtenzeServiceProxy>();

			Bind<ICafServiceProxy>().To<CafServiceProxy>();


			// Tasi
			Bind<IResolveViaDaCodviario>().To<BariStradarioService>();
            Bind<IResolveComuneDaCodiceIstat>().To<BariComuniService>();

            Bind<FirmaCidPinService>().ToSelf();

			new TasiNinjectModule(this).Configure();
			new ImuNinjectModule(this).Configure();
			new DenunceTaresNinjectModule(this).Configure();
			
			
		}
	}
}
