using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{

	/// <summary>
	/// Rappresenta la configurazione di un campo dinamico 
	/// </summary>
	public interface IDyn2Campo
	{
		/// <summary>
		/// Alias del comune
		/// </summary>
		string Idcomune{get;set;}
		
		/// <summary>
		/// Id univoco del campo
		/// </summary>
		int? Id { get; set; }

		/// <summary>
		/// Modulo in cui è utilizzato il campo
		/// </summary>
		string Software { get; set; }

		/// <summary>
		/// Nome interno del campo
		/// </summary>
		string Nomecampo{ get; set; }

		/// <summary>
		/// Etichetta del campo
		/// </summary>
		string Etichetta{ get; set; }

		/// <summary>
		/// Descrizione del campo
		/// </summary>
		string Descrizione{ get; set; }

		/// <summary>
		/// Tipo di campo (Testo, Numericointero etc etc)
		/// </summary>
		string Tipodato { get; set; }

		/// <summary>
		/// Flag che identifica se il campo è obbligatorio
		/// </summary>
		int? Obbligatorio{ get; set; }

		/// <summary>
		/// Contesto in cui è utilizzato il campo
		/// </summary>
		string FkD2bcId{ get; set; }
	}
}
