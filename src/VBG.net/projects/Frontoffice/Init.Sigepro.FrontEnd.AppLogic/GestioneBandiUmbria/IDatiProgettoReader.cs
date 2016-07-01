// -----------------------------------------------------------------------
// <copyright file="IDatiProgettoReader.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IDatiProgettoReader
	{
		DatiProgettoModello2 ReadDatiProgetto(DatiPdfCompilabile datiModello2);
	}
}
