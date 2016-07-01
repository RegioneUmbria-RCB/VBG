using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit.Data;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
	public class RisultatoVerificaCivico : Civico
	{
		public Particella particella {get;set;}
		public string esponente { get; set; }

		internal Data.Sit ToDatiLocalizzazione()
		{
			return new Data.Sit()
			{
				CodVia = codiceVia,
				Civico = numero,
				CodCivico = wkt,
				Esponente = esponente,
				TipoCatasto = this.particella.tipo,
				Sezione = this.particella.sez,
				Foglio = this.particella.foglio,
				Particella = this.particella.numero,
				Longitudine = this.centerX,
				Latitudine = this.centerY
			};
		}

	}
}
