using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Attivita
{
	public interface IIAttivitaDyn2Dati
	{
		string Idcomune { get; set; }
		int? FkIaId { get; set; }
		int? FkD2cId { get; set; }
		int? Indice { get; set; }
		int? IndiceMolteplicita { get; set; }
		string Valore { get; set; }
		string Valoredecodificato { get; set; }
	}
}
