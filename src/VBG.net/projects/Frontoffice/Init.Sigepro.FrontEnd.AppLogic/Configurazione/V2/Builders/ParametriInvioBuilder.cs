using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriInvioBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriInvio>
	{



		public ParametriInvioBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
			:base( aliasResolver , configurazioneAreaRiservataRepository)
		{

		}


		#region IBuilder<ParametriInvio> Members

		public ParametriInvio Build()
		{
			var cfg = GetConfig();

			var messaggioInvioFallito = cfg.MessaggioInvioFallito;

			if (String.IsNullOrEmpty(messaggioInvioFallito))
			{
				messaggioInvioFallito = "La pratica è stata inviata allo sportello tuttavia non è stato possibile effettuarne l'elaborazione.<br />La preghiamo di contattare telefonicamente lo sportello al numero {0} negli orari: {1}<br><br>Fornendo all'operatore il numero pratica riportato qui sotto";
			}
			
			return new ParametriInvio(
				messaggioInvioFallito,
				cfg.MessaggioInvioPec,
				cfg.CodiceOggettoInvioConFirma,
				cfg.CodiceOggettoInvioConSottoscrizione
			);
		}

		#endregion


	}
}
