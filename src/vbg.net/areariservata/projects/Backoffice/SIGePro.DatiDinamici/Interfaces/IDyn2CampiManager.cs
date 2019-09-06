using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Rappresenta un manager di database in grado di leggere 
	/// e far persistere la configurazione di un campo dinamico
	/// </summary>
	public interface IDyn2CampiManager
	{
		IDyn2Campo GetById(string idComune, int idCampo);

		SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(string idComune, int idModello);
	}
}
