using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
	public class RisultatoVerificaParticella: Particella
	{
		public Esponente[] civici { get; set; }



		internal Data.Sit ToDatiLocalizzazione()
		{
			var rVal = new Data.Sit()
			{
				TipoCatasto = this.tipo,
				Sezione = this.sez,
                Foglio = this.foglio.TrimStart('0'),
                Particella = this.numero.TrimStart('0')
			};

			if (this.civici.Length == 1)
			{
				rVal.ExtendWith(this.civici[0].ToDatiLocalizzazione());
			}

			return rVal;
		}
	}
}
