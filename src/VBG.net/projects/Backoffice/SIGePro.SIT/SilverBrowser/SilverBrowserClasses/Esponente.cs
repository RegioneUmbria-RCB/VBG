using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
	public class Esponente : Civico
	{
		public string esponente { get; set; }

		internal Data.Sit ToDatiLocalizzazione()
		{
			return new Data.Sit()
			{
				CodCivico = this.wkt,
				Longitudine = this.centerX,
				Latitudine = this.centerY,
				CodVia = this.codiceVia,
				Civico = this.numero,
				Esponente = this.esponente
			};
		}
	}
}
