using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Rappresenta lo script di un campo dinamico
	/// </summary>
	public interface IDyn2ScriptCampo
	{
		/// <summary>
		/// Alias del comune
		/// </summary>
		string Idcomune{get;set;}

		/// <summary>
		/// Id del campo a cui appartiene lo script
		/// </summary>
		int? FkD2cId{get;set;}

		/// <summary>
		/// Evento in cui deve essere eseguito lo script
		/// </summary>
		string Evento{get;set;}

		/// <summary>
		/// Script da eseguire codificato in utf8
		/// </summary>
		byte[] Script { get; set; }

		/// <summary>
		/// Testo dello script in plain text
		/// </summary>
		/// <returns>testo dello script</returns>
		string GetTestoScript();

		/// <summary>
		/// Imposta il testo dello script ed effettua internamente l'encoding in utf8
		/// </summary>
		/// <param name="testo">testo dello script</param>
		void SetTestoScript(string testo);
	}
}
