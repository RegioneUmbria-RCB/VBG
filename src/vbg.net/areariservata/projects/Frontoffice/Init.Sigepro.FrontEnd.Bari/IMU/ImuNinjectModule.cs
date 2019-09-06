// -----------------------------------------------------------------------
// <copyright file="ImuNinjectModule.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Ninject.Modules;
	using Init.Sigepro.FrontEnd.Bari.Core.Config;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
	using Init.Sigepro.FrontEnd.Bari.IMU.wsdl;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs.Converters;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ImuNinjectModule
	{
		NinjectModule _module;

		public ImuNinjectModule(NinjectModule module)
		{
			this._module = module;
		}

		public void Configure()
		{
			this._module.Bind<IUtenzeImuService>().To<UtenzeImuService>();
			this._module.Bind<IImuServiceProxy>().To<ImuServiceProxy>();
			// this._module.Bind<ITributiConfigService>().To<TributiConfigService>(); // <<- è già registrato su TASI, sarebbe meglio spostarlo su un modulo CORE
			this._module.Bind<IMapTo<datiContribuenteImuResponseType, DatiContribuenteImuDto>>().To<DatiContribuenteImuResponseTypeToDatiContribuenteImuDto>();
			this._module.Bind<IMapTo<datiIndirizzoResponseType, IndirizzoImuDto>>().To<DatiIndirizzoResponseTypeToIndirizzoImuDto>();
			this._module.Bind<IMapTo<datiImmobileResponseType, ImmobileImuDto>>().To<DatiImmobileResponseTypeToImmobileImuDto>();
			this._module.Bind<IMapTo<datiIndirizzoImmobileResponse, IndirizzoImuDto>>().To<DatiIndirizzoImmobileResponseToIndirizzoImuDto>();
			this._module.Bind<IMapTo<datiCatastaliType, DatiCatastaliImuDto>>().To<DatiCatastaliTypeToDatiCatastaliImuDto>();

		}
	}
}
