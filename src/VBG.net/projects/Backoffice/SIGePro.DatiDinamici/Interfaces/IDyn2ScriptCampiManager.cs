using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Scripts;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Manager dello script dei campi
	/// </summary>
	public interface IDyn2ScriptCampiManager
	{
		Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(string idComune, int idCampo);
	}
}
