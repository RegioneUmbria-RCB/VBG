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

        public string ToLogFormat()
        {
            return String.Format("this.Software = {0}, this.IdAttivita = {1}, this.CodiceIstanza = {2}, this.IdScheda = {3}", 
                this.Software, this.IdAttivita, this.CodiceIstanza, this.IdScheda);
        }
	}
}
