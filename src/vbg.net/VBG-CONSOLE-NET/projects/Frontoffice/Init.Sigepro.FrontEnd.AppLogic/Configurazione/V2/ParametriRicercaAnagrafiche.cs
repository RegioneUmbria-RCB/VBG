using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriRicercaAnagrafiche : IParametriConfigurazione
	{
		public readonly string UrlRicercaPersoneFisiche;
		public readonly string UrlRicercaPersoneGiuridiche;

		internal ParametriRicercaAnagrafiche(string urlRicercaPersoneFisiche, string urlRicercaPersoneGiuridiche)
		{
			this.UrlRicercaPersoneFisiche = urlRicercaPersoneFisiche;
			this.UrlRicercaPersoneGiuridiche = urlRicercaPersoneGiuridiche;
		}
	}
}
