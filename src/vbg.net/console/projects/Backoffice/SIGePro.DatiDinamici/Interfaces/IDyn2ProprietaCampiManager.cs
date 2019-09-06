using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{

	/// <summary>
	/// Manager delle proprietà dei campi
	/// </summary>
	public interface IDyn2ProprietaCampiManager
	{
		/// <summary>
		/// Ottiene la lista delle proprietà del campo passato
		/// </summary>
		/// <param name="idComune">id comune</param>
		/// <param name="idCampo">id univoco del campo</param>
		/// <returns>Lista di proprietà del campo</returns>
		List<IDyn2ProprietaCampo> GetProprietaCampo(string idComune, int idCampo);
	}
}
