using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriAspettoBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriAspetto>
	{
		private static class Constants
		{
			public const string ConfigKeyName = "file-configurazione-contenuti";
		}

		string _nomeFileContenuti = String.Empty;

		public ParametriAspettoBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo)
			:base(aliasResolver , repo)
		{
			this._nomeFileContenuti = ConfigurationManager.AppSettings[Constants.ConfigKeyName];
		}

		#region IBuilder<ParametriAspetto> Members

		public ParametriAspetto Build()
		{
			var cfg = GetConfig();

			if (!String.IsNullOrEmpty(cfg.NomeConfigurazioneContenuti))
				this._nomeFileContenuti = cfg.NomeConfigurazioneContenuti;

			var intestazioneCertificatoDiInvio = cfg.IntestazioneCertificatoInvio;

			return new ParametriAspetto(this._nomeFileContenuti, intestazioneCertificatoDiInvio); 
		}

		#endregion
	}
}
