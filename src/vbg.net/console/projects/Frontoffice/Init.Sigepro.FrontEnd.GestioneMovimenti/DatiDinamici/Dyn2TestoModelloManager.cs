using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class Dyn2TestoModelloManager : IDyn2TestoModelloManager
	{
		private   AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache;

		public Dyn2TestoModelloManager(AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache)
		{
			// TODO: Complete member initialization
			this.modelloDinamicoCache = modelloDinamicoCache;
		}

		#region IDyn2TestoModelloManager Members

		public IDyn2TestoModello GetById(string idComune, int idTesto)
		{
			throw new NotImplementedException();
		}

		public SIGePro.DatiDinamici.Utils.SerializableDictionary<int, IDyn2TestoModello> GetListaTestiDaIdModello(string idComune, int idModello)
		{
			return this.modelloDinamicoCache.ListaTesti;
		}

		#endregion
	}
}
