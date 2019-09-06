// -----------------------------------------------------------------------
// <copyright file="PersonaDenunciaTares.cs" company="">
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
	public class PersonaDenunciaTares
	{
		public TipoPersonaEnum TipoPersona { get; set; }
		public string Nome { get; set; }
		public string Cognome { get; set; }

		public string CodiceIstatComuneNascita { get; set; }
		public string ComuneNascita { get; set; }
		public string ProvinciaNascita { get; set; }
		public SessoEnum? Sesso { get; set; }
		public DateTime? DataNascita { get; set; }
		public string CodiceFiscale { get; set; }

		public string Pec { get; set; }
		public string Telefono { get; set; }
		public string Fax { get; set; }
	}
}
