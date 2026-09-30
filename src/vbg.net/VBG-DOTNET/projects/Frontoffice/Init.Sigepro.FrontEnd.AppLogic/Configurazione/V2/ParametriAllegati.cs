using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriAllegati : IParametriConfigurazione
	{
		public readonly int DimensioneMassimaAllegato;
		public readonly string WarningDimensioneMassimaAllegatoSuperata;
        public readonly string DescrizioneDelegaATrasmettere;

        public ParametriAllegati(int dimensioneMassimaAllegato, string warningDimensioneMassimaAllegatoSuperata, string descrizioneDelegaATrasmettere)
		{
			this.DimensioneMassimaAllegato = dimensioneMassimaAllegato;
			this.WarningDimensioneMassimaAllegatoSuperata = warningDimensioneMassimaAllegatoSuperata;
            this.DescrizioneDelegaATrasmettere = String.IsNullOrEmpty(descrizioneDelegaATrasmettere) ? "Delega a trasmettere" : descrizioneDelegaATrasmettere;
		}
	}
}
