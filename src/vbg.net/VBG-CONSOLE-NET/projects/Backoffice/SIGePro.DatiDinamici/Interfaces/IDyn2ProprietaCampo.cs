using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Proprietà di un campo dinamico
	/// </summary>
	public interface IDyn2ProprietaCampo
	{
		/// <summary>
		/// Alias del comune
		/// </summary>
		string Idcomune{get;set;}

		/// <summary>
		/// Id del campo a cui la proprietà appartiene
		/// </summary>
		int? FkD2cId{get;set;}

		/// <summary>
		/// Nome della proprietà
		/// </summary>
		string Proprieta{get;set;}

		/// <summary>
		/// Valore della proprietà
		/// </summary>
		string Valore{get;set;}
	}
}
