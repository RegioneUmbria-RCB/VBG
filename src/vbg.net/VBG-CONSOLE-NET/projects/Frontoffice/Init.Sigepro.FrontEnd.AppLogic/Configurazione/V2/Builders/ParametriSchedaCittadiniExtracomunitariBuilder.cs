using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriSchedaCittadiniExtracomunitariBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriSchedaCittadiniExtracomunitari>
	{
		public ParametriSchedaCittadiniExtracomunitariBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
			:base( aliasResolver , configurazioneAreaRiservataRepository )
		{

		}

		#region IConfigurazioneBuilder<ParametriSchedaCittadiniExtracomunitari> Members

		public ParametriSchedaCittadiniExtracomunitari Build()
		{
			var cfg = GetConfig();

			if (cfg.SchedaDinamicaCittadiniExtracomunitari == null)
				return new ParametriSchedaCittadiniExtracomunitari(null, String.Empty, false);

			var idScheda = cfg.SchedaDinamicaCittadiniExtracomunitari.Codice;
			var nomeScheda = cfg.SchedaDinamicaCittadiniExtracomunitari.Descrizione;
			var richiedeFirma = cfg.SchedaDinamicaCittadiniExtracomunitari.RichiedeFirma;

			return new ParametriSchedaCittadiniExtracomunitari( idScheda, nomeScheda, richiedeFirma );
		}

		#endregion
	}
}
