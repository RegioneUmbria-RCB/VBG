// -----------------------------------------------------------------------
// <copyright file="DenunceTaresNinjectModule.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Ninject.Modules;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs.Converters;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.ServiceProxies;
	using Init.Sigepro.FrontEnd.Bari.FirmaCidPin;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DenunceTaresNinjectModule
	{
		NinjectModule _module;

		public DenunceTaresNinjectModule(NinjectModule module)
		{
			this._module = module;
		}

		public void Configure()
		{
			// this._module.Bind<IUtenzeTasiService>().To<UtenzeTasiService>();
			// this._module.Bind<ITasiServiceProxy>().To<TasiServiceProxy>();
			// this._module.Bind<ITributiConfigService>().To<TributiConfigService>();
			this._module.Bind<IMapTo<datiContribuenteResponseType, DatiAnagraficiContribuenteDenunciaTares>>().To<DatiContribuenteResponseTypeToDatiAnagraficiContribuenteDenunciaTares>();
			this._module.Bind<IMapTo<datiRappresentanteType, RappresentanteDenunciaTares>>().To<DatiRappresentanteTypeToRappresentanteDenunciaTares>();
			this._module.Bind<IMapTo<indirizzoType, IndirizzoDenunciaTares>>().To<IndirizzoTypeToIndirizzoDenunciaTares>();
			this._module.Bind<IMapTo<datiUtenzaCommercialeResponseType, UtenzaCommercialeDenunciaTaresDto>>().To<DatiUtenzaCommercialeResponseTypeToUtenzaCommercialeDenunciaTaresDto>();
			this._module.Bind<IMapTo<indirizzoComuneType, IndirizzoDenunciaTares>>().To<IndirizzoComuneTypeToIndirizzoDenunciaTares>();
			this._module.Bind<IMapTo<datiUtenzaDomesticaResponseType, UtenzaDomesticaDenunciaTaresDto>>().To<DatiUtenzaDomesticaResponseTypeToUtenzaDomesticaDenunciaTaresDto>();
			this._module.Bind<IMapTo<occupanteImmobileType, OccupanteImmobileDenunciaTares>>().To<OccupanteImmobileTypeToOccupanteImmobileDenunciaTares>();
			this._module.Bind<IMapTo<datiCatastaliNewType, DatiCatastaliDenunciaTares>>().To<DatiCatastaliNewTypeToDatiCatastaliDenunciaTares>();

			this._module.Bind<IDenunceTaresService>().To<DenunceTaresService>();
			this._module.Bind<IDenunceTaresServiceProxy>().To<DenunceTaresServiceProxy>();
			this._module.Bind<IFirmaCidPinService>().To<FirmaCidPinService>();
			
		}
	}
}
