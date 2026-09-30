// -----------------------------------------------------------------------
// <copyright file="SchedaDinamicaSalvata.cs" company="">
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
	public class SchedaDinamicaAttivitaSalvata
	{
		public readonly int IdAttivita;
		public readonly string Software;

		public SchedaDinamicaAttivitaSalvata(/*string software,*/int idAttivita)
		{
			this.IdAttivita = idAttivita;
			//this.Software = software;
			this.Software = "TT";
		}

	}
}
