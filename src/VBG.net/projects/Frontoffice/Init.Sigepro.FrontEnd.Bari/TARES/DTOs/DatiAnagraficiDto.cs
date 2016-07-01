using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.TARES.DTOs
{
	public class DatiAnagraficiDto
	{
		public string CodiceFiscale { get; set; }
		public string Cognome { get; set; }
		public string ComuneNascita { get; set; }
		public string ComuneEsteroNascita { get; set; }
		public string DataNascita { get; set; }
		public string Nome { get; set; }
		public string ProvinciaNascita { get; set; }
		public string Sesso { get; set; }
		public string Telefono { get; set; }

		public string NominativoCompleto { get { return String.Format("{0} {1}", this.Cognome, this.Nome); } }

		public string DatiNascita { 
			get 
			{
				var str = String.Format("Nato il {0} a {1} ({2})", DataNascita, ComuneNascita, ProvinciaNascita);

				if (!String.IsNullOrEmpty(ComuneEsteroNascita))
					return str + " " + ComuneEsteroNascita;

				return str;
			} 
		}
	}
}
