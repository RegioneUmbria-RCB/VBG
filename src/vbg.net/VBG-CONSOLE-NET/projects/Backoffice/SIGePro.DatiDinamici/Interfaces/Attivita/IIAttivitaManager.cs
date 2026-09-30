using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Attivita
{
	public interface IIAttivitaManager
	{
		IClasseContestoModelloDinamico LeggiAttivita(string idComune , int idAttivita);
	}
}
