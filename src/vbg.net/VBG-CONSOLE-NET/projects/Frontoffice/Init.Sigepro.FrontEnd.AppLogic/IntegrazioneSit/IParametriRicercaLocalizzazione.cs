// -----------------------------------------------------------------------
// <copyright file="IParametriRicercaLocalizzazione.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IParametriRicercaLocalizzazione
	{
		string CodViario { get; }
		string Civico { get; }
		string Esponente { get; }

		string TipoCatasto { get; }
		string Sezione { get; }
		string Foglio { get; }
		string Particella { get; }
		string Sub { get; }

		Sit ToSit();
	}
}
