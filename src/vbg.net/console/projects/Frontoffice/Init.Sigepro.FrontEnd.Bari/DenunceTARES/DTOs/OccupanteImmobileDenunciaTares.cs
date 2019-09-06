// -----------------------------------------------------------------------
// <copyright file="OccupanteImmobileDenunciaTares.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class OccupanteImmobileDenunciaTares
	{
		public string Cognome { get; set; }
		public string Nome { get; set; }
		public string CodiceFiscale { get; set; }

		public DataDto DataNascita { get; set; }
		public DataDto DataInizioOccupazione { get; set; }
		public DataDto DataFineOccupazione { get; set; }
	}
}
