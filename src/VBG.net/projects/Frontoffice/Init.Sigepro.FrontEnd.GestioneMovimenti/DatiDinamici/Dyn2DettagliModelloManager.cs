using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class Dyn2DettagliModelloManager : IDyn2DettagliModelloManager
	{
		private   AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache;

		public Dyn2DettagliModelloManager(AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache)
		{
			// TODO: Complete member initialization
			this.modelloDinamicoCache = modelloDinamicoCache;
		}
		#region IDyn2DettagliModelloManager Members

		public List<IDyn2DettagliModello> GetList(string idComune, int idModello)
		{
			return this.modelloDinamicoCache.Struttura;
		}

		#endregion
	}
}
