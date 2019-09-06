// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneServizioConfig.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.Bari.Core;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;


	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ConfigurazioneServizioConfigBari : IConfigServiceUrl, IParametriConfigurazione
	{
		public string ServiceUrl
		{
			get;
			set;
		}

		internal ConfigurazioneServizioConfigBari(IConfigurazioneBuilder<ConfigurazioneServizioConfigBari> builder)
		{
			if (builder != null)
			{
				var model = builder.Build();

				this.ServiceUrl = model.ServiceUrl;
			}
		}

		public static ConfigurazioneServizioConfigBari Create(string urlServizioSigepro)
		{
			return new ConfigurazioneServizioConfigBari(null)
			{
				ServiceUrl = urlServizioSigepro
			};
		}
	}
}
