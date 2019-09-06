// -----------------------------------------------------------------------
// <copyright file="IVisuraMobileProfileService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.VisuraMobile
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IVisuraMobileProfileService
	{
		bool RegistraProfilo(string uid, string nome, string cognome, string codiceFiscale);
	}
}
