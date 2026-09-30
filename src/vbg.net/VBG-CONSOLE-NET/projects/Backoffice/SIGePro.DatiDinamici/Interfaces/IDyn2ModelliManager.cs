using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{

	/// <summary>
	/// Manager dei modelli dinamici
	/// </summary>
	public interface IDyn2ModelliManager
	{
		IDyn2Modello GetById(string idComune, int idModello);
	}
}
