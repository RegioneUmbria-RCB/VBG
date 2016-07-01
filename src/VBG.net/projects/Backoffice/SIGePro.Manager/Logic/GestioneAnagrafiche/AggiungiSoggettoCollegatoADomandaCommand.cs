using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneAnagrafiche
{
	public class AggiungiSoggettoCollegatoADomandaCommand
	{
		public string Cognome { get; set; }
		public string Nome { get; set; }
		public DateTime? DataNascita { get; set; }
		public string ComuneNascita { get; set; }
		public string CodiceFiscale { get; set; }
		public string Indirizzo { get; set; }
		public string Civico { get; set; }
		public string Localita { get; set; }
		public string Cap { get; set; }
		public string Comune { get; set; }
		public string Provincia { get; set; }
		public string Telefono { get; set; }
		public string Fax { get; set; }
		public string Email { get; set; }

		public int CodiceIstanza { get; set; }
		public int IdTipoSoggetto { get; set; }
	}
}
