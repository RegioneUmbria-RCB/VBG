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
        public string codiceFabbricato { get; set; }

		internal Data.Sit ToDatiLocalizzazione()
		{
			var dati = new Data.Sit()
			{
				CodVia = codiceVia,
				Civico = numero,
				CodCivico = wkt,
				Esponente = esponente,
				Longitudine = this.centerX,
				Latitudine = this.centerY,
                Fabbricato = this.codiceFabbricato
			};

            if (this.particella != null)
            {
                dati.TipoCatasto = this.particella.tipo;
				dati.Sezione = this.particella.sez;
                dati.Foglio = this.particella.foglio.TrimStart('0');
                dati.Particella = this.particella.numero.TrimStart('0');
            }

            return dati;
		}

	}
}
