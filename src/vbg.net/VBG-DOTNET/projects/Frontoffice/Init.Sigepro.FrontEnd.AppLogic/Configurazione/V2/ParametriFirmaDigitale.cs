using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriFirmaDigitale : IParametriConfigurazione
	{
		public readonly string UrlJspFirmaDigitale;
        public readonly int? IdSchedaDinamicaEstremiDocumento;

		internal ParametriFirmaDigitale(string urlJspFirmaDigitale, int? idSchedaDinamicaEstremiDocumento)
		{
			this.UrlJspFirmaDigitale = urlJspFirmaDigitale;
            this.IdSchedaDinamicaEstremiDocumento = idSchedaDinamicaEstremiDocumento;
		}
	}
}
