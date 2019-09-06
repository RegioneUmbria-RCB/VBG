using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Istanze
{
	public interface IIstanzeManager
	{
		IClasseContestoModelloDinamico LeggiIstanza(string idComune, int codiceIstanza);
	}
}
