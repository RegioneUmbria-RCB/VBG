using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriRegistrazioneBuilder: AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriRegistrazione>
	{

		public ParametriRegistrazioneBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo):base( aliasResolver , repo)
		{

		}

		#region IBuilder<ParametriRegistrazione> Members

		public ParametriRegistrazione Build()
		{
			var cfg = GetConfig();

			return new ParametriRegistrazione(cfg.MessaggioRegistrazioneCompletata);
		}

		#endregion
	}
}
