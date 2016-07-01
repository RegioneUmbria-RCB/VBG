using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriDatiCatastaliBuilder : IConfigurazioneBuilder<ParametriDatiCatastali>
	{
		private static class Constants
		{
			public const string ConfigKeyName = "MostraDatiCatastaliEstesi";
		}

		bool _mostraDatiCatastaliEstesi = false;

		public ParametriDatiCatastaliBuilder()
		{
			var cfgKey = ConfigurationManager.AppSettings[Constants.ConfigKeyName];

			if (!String.IsNullOrEmpty(cfgKey))
				this._mostraDatiCatastaliEstesi = bool.Parse(cfgKey);
		}

		#region IBuilder<ParametriDatiCatastali> Members

		public ParametriDatiCatastali Build()
		{
			return new ParametriDatiCatastali(this._mostraDatiCatastaliEstesi); 
		}

		#endregion
	}
}
