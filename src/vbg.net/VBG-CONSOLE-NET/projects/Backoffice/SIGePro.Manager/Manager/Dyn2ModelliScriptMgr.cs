
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Scripts;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class Dyn2ModelliScriptMgr : IDyn2ScriptModelloManager
    {

		#region IDyn2ScriptModelloManager Members

		public List<Dyn2ModelliScript> GetList(string idComune, int idModello)
		{
			var filtro = new Dyn2ModelliScript
			{
				Idcomune = idComune,
				FkD2mtId = idModello
			};

			return GetList(filtro);
		}

		public IDyn2ScriptModello GetById(string idComune, int idModello, TipoScriptEnum contesto)
		{
			return GetById(idComune, idModello, contesto.ToString());
		}

		#endregion
	}
}
				