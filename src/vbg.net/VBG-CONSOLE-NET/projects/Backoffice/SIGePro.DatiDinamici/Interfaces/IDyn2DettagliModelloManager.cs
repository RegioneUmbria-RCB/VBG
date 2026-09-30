using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{

	/// <summary>
	/// Manager dei dettagli di un modello dinamico
	/// </summary>
	public interface IDyn2DettagliModelloManager
	{
		List<IDyn2DettagliModello> GetList(string idComune , int idModello);
	}
}
