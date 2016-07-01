using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Dettagli di un modello dinamico
	/// </summary>
	public interface IDyn2DettagliModello
	{
		/// <summary>
		/// Alias del comune
		/// </summary>
		string Idcomune{get;set;}

		/// <summary>
		/// Identificativo univoco del modello
		/// </summary>
		int? Id{get;set;}

		/// <summary>
		/// Id del modello a cui il dettaglio appartiene
		/// </summary>
		int? FkD2mtId{get;set;}

		/// <summary>
		/// Id del campo collegato al dettaglio
		/// </summary>
		int? FkD2cId{get;set;}

		/// <summary>
		/// id del testo collegato al dettaglio
		/// </summary>
		int? FkD2mdtId{get;set;}

		/// <summary>
		/// Posizione verticale (colonna)
		/// </summary>
		int? Posverticale{get;set;}

		/// <summary>
		/// Posizione orizzontale (riga)
		/// </summary>
		int? Posorizzontale{get;set;}

		/// <summary>
		/// Flag che identifica se la riga è multipla
		/// </summary>
		int? FlgMultiplo{get;set;}
	}
}
