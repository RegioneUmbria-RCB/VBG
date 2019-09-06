using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;
using Init.SIGePro.DatiDinamici.Scripts;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class Dyn2ScriptCampiManager : IDyn2ScriptCampiManager
	{
		private ModelloDinamicoCache modelloDinamicoCache;

		public Dyn2ScriptCampiManager(ModelloDinamicoCache modelloDinamicoCache)
		{
			// TODO: Complete member initialization
			this.modelloDinamicoCache = modelloDinamicoCache;
		}

		#region IDyn2ScriptCampiManager Members

		public Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(string idComune, int idCampo)
		{
			if (modelloDinamicoCache.ScriptsCampiDinamici.ContainsKey(idCampo))
				return modelloDinamicoCache.ScriptsCampiDinamici[idCampo];

			return new Dictionary<TipoScriptEnum, IDyn2ScriptCampo>();
		}

		#endregion
	}
}
