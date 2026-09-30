using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi
{
	public class SchedaDinamicaIstanzaEliminata
	{
		public readonly int IdAttivita;
		public readonly int CodiceIstanza;
		public readonly string Software;
		public readonly int IdScheda;

		public SchedaDinamicaIstanzaEliminata( int idAttivita, int codiceIstanza, int idScheda)
		{
			//this.Software = software;
			this.Software = "TT";
			this.IdAttivita = idAttivita;
			this.CodiceIstanza = codiceIstanza;
			this.IdScheda = idScheda;
		}
	}
}
