using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriAllegatiBuilder:  AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriAllegati>
	{
		private static class Constants
		{
			public static int DimensioneMassimaAllegatiDefault = 31457280;	// 30mb
			public static string WarningDimensioneMassimaAllegatiDefault = "Attenzione! Il file allegato supera la dimensione massima consentita (30MB).";
		}

		public ParametriAllegatiBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo)
			:base(aliasResolver , repo)
		{
		}

		public ParametriAllegati Build()
		{
			var cfg = GetConfig();

			var dimensioneMassimaAllegati = cfg.DimensioneMassimaAllegati;
			var warning = cfg.WarningDimensioneMassimaAllegati;
            var descrizioneDelegaATrasmettere = cfg.DescrizioneDelegaATrasmettere;

			if(dimensioneMassimaAllegati == 0)
			{
				dimensioneMassimaAllegati = Constants.DimensioneMassimaAllegatiDefault;
			}

			if(String.IsNullOrEmpty(warning))
			{
				warning = Constants.WarningDimensioneMassimaAllegatiDefault;
			}

			return new ParametriAllegati(dimensioneMassimaAllegati, warning, descrizioneDelegaATrasmettere);
		}
	}
}
