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
                Foglio = this.foglio.TrimStart('0'),
                Particella = this.numero.TrimStart('0'),
				Sub	= this.sub,
				Longitudine = this.centerX,
				Latitudine = this.centerY
			};
		}
	}
}
