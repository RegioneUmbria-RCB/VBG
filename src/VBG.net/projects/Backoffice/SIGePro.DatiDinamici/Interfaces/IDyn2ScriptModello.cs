using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{

	/// <summary>
	/// Rappresenta lo script di un modello
	/// </summary>
	public interface IDyn2ScriptModello
	{
		/// <summary>
		/// Alias del comune
		/// </summary>
		string Idcomune{get;set;}

		/// <summary>
		/// Id del modello a cui appartiene lo script
		/// </summary>
		int? FkD2mtId{get;set;}

		/// <summary>
		/// Evento in cui deve essere eseguito lo script
		/// </summary>
		string Evento{get;set;}

		/// <summary>
		/// Script (con encoding utf8)
		/// </summary>
		byte[] Script{get;set;}

		/// <summary>
		/// Ottiene il testo dello script risolvendo l'encoding
		/// </summary>
		/// <returns>testo dello script</returns>
		string GetTestoScript();

		/// <summary>
		/// Imposta il testo dello script e lo codifica in utf8
		/// </summary>
		/// <param name="testo">testo dello script</param>
		void SetTestoScript(string testo);
	}
}
