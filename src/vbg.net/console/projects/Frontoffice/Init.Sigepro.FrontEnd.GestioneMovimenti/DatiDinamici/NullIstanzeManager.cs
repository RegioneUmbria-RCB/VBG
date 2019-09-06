using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class NullIstanzeManager : IIstanzeManager
	{
		#region IIstanzeManager Members

		public SIGePro.DatiDinamici.Interfaces.IClasseContestoModelloDinamico LeggiIstanza(string idComune, int codiceIstanza)
		{
			return null;
		}

		#endregion
	}
}
