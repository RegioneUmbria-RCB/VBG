using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public class DatiNascitaAnagrafica
	{
		public string CodiceComune { get; private set; }
		public string SiglaProvincia { get; private set; }
		public DateTime? Data { get; private set; }

		public DatiNascitaAnagrafica(string codiceComune, string siglaProvincia, DateTime? dataNascita)
		{
			this.CodiceComune = codiceComune;
			this.SiglaProvincia = siglaProvincia;
			this.Data = dataNascita;
		}
	}
}
