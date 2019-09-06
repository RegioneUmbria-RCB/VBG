// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneServizioTares.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares.Configurazione
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
		using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.Bari.TARES;
	using Init.Sigepro.FrontEnd.Bari.Core.CafServices;

	public class ConfigurazioneServizioTares : ICafServiceUrl, IParametriConfigurazione
	{
		public string ServiceUrl
		{
			get;
			set;
		}


		internal ConfigurazioneServizioTares(IConfigurazioneBuilder<ConfigurazioneServizioTares> builder)
		{
			if (builder != null)
			{
				var model = builder.Build();

				this.ServiceUrl = model.ServiceUrl;
			}
		}



		public static ConfigurazioneServizioTares Create(string urlServizioSigepro)
		{
			return new ConfigurazioneServizioTares(null)
			{
				ServiceUrl = urlServizioSigepro
			};
		}


	}
}
