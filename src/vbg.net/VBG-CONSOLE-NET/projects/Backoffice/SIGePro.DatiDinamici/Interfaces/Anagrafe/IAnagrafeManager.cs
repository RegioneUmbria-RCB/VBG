using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Anagrafe
{
	public interface IAnagrafeManager
	{
		IClasseContestoModelloDinamico LeggiAnagrafica(string idComune, int codiceAnagrafe);
	}
}
