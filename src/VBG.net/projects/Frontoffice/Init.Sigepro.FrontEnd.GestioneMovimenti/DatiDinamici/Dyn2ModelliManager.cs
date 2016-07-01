using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class Dyn2ModelliManager : IDyn2ModelliManager
	{
		private   AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache;

		public Dyn2ModelliManager(AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache)
		{
			// TODO: Complete member initialization
			this.modelloDinamicoCache = modelloDinamicoCache;
		}

		#region IDyn2ModelliManager Members

		public IDyn2Modello GetById(string idComune, int idModello)
		{
			return this.modelloDinamicoCache.Modello;
		}

		#endregion
	}
}
