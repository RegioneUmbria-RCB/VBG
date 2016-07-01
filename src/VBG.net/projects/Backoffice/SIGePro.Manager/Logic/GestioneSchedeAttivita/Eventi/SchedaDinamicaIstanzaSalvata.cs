// -----------------------------------------------------------------------
// <copyright file="SchedaDinamicaIstanzaSalvata.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class SchedaDinamicaIstanzaSalvata
	{
		public readonly int IdAttivita;
		public readonly int CodiceIstanza;
		public readonly string Software;
		public readonly int IdScheda;

		public SchedaDinamicaIstanzaSalvata(/*string software,*/ int idAttivita, int codiceIstanza, int idScheda)
		{
			//this.Software = software;
			this.Software = "TT";
			this.IdAttivita = idAttivita;
			this.CodiceIstanza = codiceIstanza;
			this.IdScheda = idScheda;
		}
	}
}
