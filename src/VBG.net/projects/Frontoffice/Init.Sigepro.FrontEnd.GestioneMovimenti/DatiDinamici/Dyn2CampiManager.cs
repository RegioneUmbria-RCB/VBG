using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class Dyn2CampiManager : IDyn2CampiManager 
	{
		private   AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache;

		public Dyn2CampiManager(AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache)
		{
			// TODO: Complete member initialization
			this.modelloDinamicoCache = modelloDinamicoCache;
		}
		#region IDyn2CampiManager Members

		public IDyn2Campo GetById(string idComune, int idCampo)
		{
			throw new NotImplementedException();
		}

		public SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(string idComune, int idModello)
		{
			return this.modelloDinamicoCache.ListaCampiDinamici;
		}

		#endregion
	}
}
