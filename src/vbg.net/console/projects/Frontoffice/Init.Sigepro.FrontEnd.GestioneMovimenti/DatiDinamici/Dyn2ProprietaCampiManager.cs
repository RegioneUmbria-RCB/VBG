using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class Dyn2ProprietaCampiManager : IDyn2ProprietaCampiManager
	{
		private   AppLogic.DatiDinamici.Entities.ModelloDinamicoCache _modelloDinamicoCache;

		public Dyn2ProprietaCampiManager(AppLogic.DatiDinamici.Entities.ModelloDinamicoCache modelloDinamicoCache)
		{
			// TODO: Complete member initialization
			this._modelloDinamicoCache = modelloDinamicoCache;
		}
		#region IDyn2ProprietaCampiManager Members

		public List<IDyn2ProprietaCampo> GetProprietaCampo(string idComune, int idCampo)
		{
			if (this._modelloDinamicoCache.ProprietaCampiDinamici.ContainsKey(idCampo))
				return this._modelloDinamicoCache.ProprietaCampiDinamici[idCampo];

			return new List<IDyn2ProprietaCampo>();
		}

		#endregion
	}
}
