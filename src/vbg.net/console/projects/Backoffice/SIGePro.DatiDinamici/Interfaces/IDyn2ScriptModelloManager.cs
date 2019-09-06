using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Scripts;

namespace Init.SIGePro.DatiDinamici.Interfaces
{

	/// <summary>
	/// Manager desgli script del modello dinamico
	/// </summary>
	public interface IDyn2ScriptModelloManager
	{
		IDyn2ScriptModello GetById(string idComune, int idModello, TipoScriptEnum contesto);
	}
}
