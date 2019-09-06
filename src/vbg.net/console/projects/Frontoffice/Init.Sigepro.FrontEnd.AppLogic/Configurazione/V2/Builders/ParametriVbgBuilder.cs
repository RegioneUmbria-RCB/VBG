using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriVbgBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriVbg>
	{
		private static class Constants
		{
			public const string ConfigKeyName = "file-configurazione-contenuti";
		}

		string _nomeFileContenuti = String.Empty;

		public ParametriVbgBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo)
			:base(aliasResolver , repo)
		{
			this._nomeFileContenuti = ConfigurationManager.AppSettings[Constants.ConfigKeyName];
		}

		#region IBuilder<ParametriAspetto> Members

		public ParametriVbg Build()
		{
			var cfg = GetConfig();

			if (!String.IsNullOrEmpty(cfg.NomeConfigurazioneContenuti))
				this._nomeFileContenuti = cfg.NomeConfigurazioneContenuti;

			var intestazioneCertificatoDiInvio = cfg.IntestazioneCertificatoInvio;

			return new ParametriVbg(this._nomeFileContenuti, intestazioneCertificatoDiInvio, cfg.ParametriPrivacy?.ResponsabileTrattamento, cfg.ParametriPrivacy?.DataProtectionOfficer, cfg.ParametriPrivacy?.DenominazioneComune); 
		}

		#endregion
	}
}
