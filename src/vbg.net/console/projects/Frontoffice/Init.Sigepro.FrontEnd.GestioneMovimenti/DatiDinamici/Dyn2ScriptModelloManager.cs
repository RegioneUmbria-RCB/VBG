using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;
using Init.SIGePro.DatiDinamici.Scripts;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class Dyn2ScriptModelloManager : IDyn2ScriptModelloManager
	{
		private ModelloDinamicoCache modelloDinamicoCache;

		public Dyn2ScriptModelloManager(ModelloDinamicoCache modelloDinamicoCache)
		{
			// TODO: Complete member initialization
			this.modelloDinamicoCache = modelloDinamicoCache;
		}

		#region IDyn2ScriptModelloManager Members

		public IDyn2ScriptModello GetById(string idComune, int idModello, TipoScriptEnum contesto)
		{
			if (!modelloDinamicoCache.ScriptsModello.ContainsKey(contesto))
				return null;

			return modelloDinamicoCache.ScriptsModello[contesto];
		}

		#endregion
	}
}
