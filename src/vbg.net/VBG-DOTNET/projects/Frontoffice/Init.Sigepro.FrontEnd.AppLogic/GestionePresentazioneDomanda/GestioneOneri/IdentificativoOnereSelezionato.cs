// -----------------------------------------------------------------------
// <copyright file="IdentificativoOnereSelezionato.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class IdentificativoOnereSelezionato
	{
		public readonly int IdCausale;
		public readonly string IdInterventoOEndo;
		public readonly string TipoOnere;

		public IdentificativoOnereSelezionato(int idCausale, string idInterventoOEndo, string tipoOnere)
		{
			this.IdCausale = idCausale;
			this.IdInterventoOEndo = idInterventoOEndo;
			this.TipoOnere = tipoOnere;
		}
	}


}
