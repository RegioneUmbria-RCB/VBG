using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Dati di un modello dinamico
	/// </summary>
	public interface IDyn2Modello
	{
		/// <summary>
		/// Alias del comune
		/// </summary>
		string Idcomune{get;set;}

		/// <summary>
		/// id univoco del modello
		/// </summary>
		int? Id{get;set;}

		/// <summary>
		/// Modulo software a cui appartiene il modello
		/// </summary>
		string Software{get;set;}

		/// <summary>
		/// Descrizione del modello
		/// </summary>
		string Descrizione{get;set;}

		/// <summary>
		/// Contesto incui viene utilizzato il modello
		/// </summary>
		string FkD2bcId{get;set;}

		/// <summary>
		/// Dismesso
		/// </summary>
		string Scriptcode{get;set;}

		/// <summary>
		/// Flag che identifica se il modello è multiplo
		/// </summary>
		int? Modellomultiplo{get;set;}

		/// <summary>
		/// Flag che identifica se il modello deve essere storicizzato
		/// </summary>
		int? FlgStoricizza{get;set;}

		/// <summary>
		/// Flag che identifica se il modello deve essere in sola lettura
		/// </summary>
		int? FlgReadonlyWeb{get;set;}

		/// <summary>
		/// Codice parlante della scheda
		/// </summary>
		string CodiceScheda { get; set; }
	}
}
