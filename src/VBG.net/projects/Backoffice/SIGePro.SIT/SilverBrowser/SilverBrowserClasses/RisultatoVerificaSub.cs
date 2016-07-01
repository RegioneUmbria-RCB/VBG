using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
	public class RisultatoVerificaSub : Sub
	{
		internal Data.Sit ToDatiLocalizzazione()
		{
			return new Data.Sit()
			{
				TipoCatasto = this.tipo,
				Sezione = this.sez,
				Foglio = this.foglio,
				Particella = this.numero,
				Sub	= this.sub,
				Longitudine = this.centerX,
				Latitudine = this.centerY
			};
		}
	}
}
