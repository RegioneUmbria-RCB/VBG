// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneServizioConfigBuilder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

	internal class ConfigurazioneServizioConfigBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ConfigurazioneServizioConfigBari>
	{
		IConfigurazione<ParametriSigeproSecurity> _webServicesConfig;

		public ConfigurazioneServizioConfigBuilder(IConfigurazione<ParametriSigeproSecurity> webServicesConfig, IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo)
			: base(aliasResolver, repo)
		{
			this._webServicesConfig = webServicesConfig;
		}

		public ConfigurazioneServizioConfigBari Build()
		{
			var urlServizioSigepro = this._webServicesConfig.Parametri.UrlServizioConfigBari;

			return ConfigurazioneServizioConfigBari.Create(urlServizioSigepro);
		}
	}
}
