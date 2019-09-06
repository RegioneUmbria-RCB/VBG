// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneServizioTaresBuilder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares.Configurazione
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

	internal class ConfigurazioneServizioTaresBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ConfigurazioneServizioTares>
	{
		IConfigurazione<ParametriSigeproSecurity> _webServicesConfig;

		public ConfigurazioneServizioTaresBuilder(IConfigurazione<ParametriSigeproSecurity> webServicesConfig, IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo)
			: base(aliasResolver, repo)
		{
			this._webServicesConfig = webServicesConfig;
		}

		public ConfigurazioneServizioTares Build()
		{
			var urlServizioSigepro = this._webServicesConfig.Parametri.UrlServizioTares;

			return ConfigurazioneServizioTares.Create(urlServizioSigepro);
		}
	}
}
