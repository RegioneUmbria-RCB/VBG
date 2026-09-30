using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{

	/// <summary>
	/// Rappresenta un testo non dinamico all'interno di un modello
	/// </summary>
	public interface IDyn2TestoModello
	{
		/// <summary>
		/// Alias del comune
		/// </summary>
		string Idcomune{get;set;}

		/// <summary>
		/// Identificativo univoco del campo
		/// </summary>
		int? Id{get;set;}

		/// <summary>
		/// Identificativo univoco del campo
		/// </summary>
		int? IdNelModello { get; set; }

		/// <summary>
		/// Tipo testo
		/// </summary>
		string IdTipoTesto{get;set;}

		/// <summary>
		/// Contenuto del testo non dinamico
		/// </summary>
		string Testo{get;set;}
	}
}
