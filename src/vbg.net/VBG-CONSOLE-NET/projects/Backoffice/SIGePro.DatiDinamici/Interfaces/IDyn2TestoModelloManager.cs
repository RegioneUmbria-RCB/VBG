using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Manager dei testi non dinamici del modello
	/// </summary>
	public interface IDyn2TestoModelloManager
	{
		//IDyn2TestoModello GetById( string idComune , int idTesto );
		SerializableDictionary<int, IDyn2TestoModello> GetListaTestiDaIdModello(string idComune, int idModello);
	}
}
