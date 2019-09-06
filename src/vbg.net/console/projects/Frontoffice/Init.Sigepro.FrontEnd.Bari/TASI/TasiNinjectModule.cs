// -----------------------------------------------------------------------
// <copyright file="TasiNinjectModule.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Ninject.Modules;
	using Init.Sigepro.FrontEnd.Bari.Core.Config;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs.Converters;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TasiNinjectModule
	{
		NinjectModule _module;

		public TasiNinjectModule(NinjectModule module)
		{
			this._module = module;
		}

		public void Configure()
		{
			this._module.Bind<IUtenzeTasiService>().To<UtenzeTasiService>();
			this._module.Bind<ITasiServiceProxy>().To<TasiServiceProxy>();
			this._module.Bind<ITributiConfigService>().To<TributiConfigService>();
			this._module.Bind<IMapTo<datiImmobiliResponseType, DatiContribuenteTasiDto>>().To<DatiImmobiliResponseTypeToDatiContribuenteTasiDto>();
			this._module.Bind<IMapTo<datiIndirizzoType, IndirizzoTasiDto>>().To<DatiIndirizzoTypeToIndirizzoTasiDto>();
			this._module.Bind<IMapTo<datiImmobileResponseType, ImmobileTasiDto>>().To<DatiImmobileResponseTypeToImmobileTasiDto>();
			this._module.Bind<IMapTo<indirizzoImmobileType, IndirizzoTasiDto>>().To<IndirizzoImmobileTypeToIndirizzoTasiDto>();
			this._module.Bind<IMapTo<datiCatastaliType, DatiCatastaliTasiDto>>().To<DatiCatastaliTypeToDatiCatastaliDto>();

		}

	}
}
