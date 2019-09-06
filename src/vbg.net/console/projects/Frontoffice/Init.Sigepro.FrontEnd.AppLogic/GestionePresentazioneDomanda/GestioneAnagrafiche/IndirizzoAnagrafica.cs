using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public class IndirizzoAnagraficaDomanda
	{
		public string Via { get; private set; }
		public string Citta { get; private set; }
		public string Cap { get; private set; }
		public string SiglaProvincia { get; private set; }
		public string CodiceComune { get; private set; }

		public IndirizzoAnagraficaDomanda(string via, string citta, string cap, string siglaProvincia, string codiceComune)
		{
			this.Via = via;
			this.Citta = citta;
			this.Cap = cap;
			this.SiglaProvincia = siglaProvincia;
			this.CodiceComune = codiceComune;
		}

		//TODO: implementare le logiche di confronto uguaglianza
	}
}
