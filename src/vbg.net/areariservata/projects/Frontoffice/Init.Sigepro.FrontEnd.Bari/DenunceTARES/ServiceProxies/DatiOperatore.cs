// -----------------------------------------------------------------------
// <copyright file="DatiOperatore.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.ServiceProxies
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiOperatore
	{
		public readonly string Nome;
		public readonly string CodiceFiscale;
		public readonly string Email;

		public DatiOperatore(string nominativo, string codiceFiscale, string email)
		{
			this.Nome = nominativo;
			this.CodiceFiscale = codiceFiscale;
			this.Email = email;
		}
	}
}
