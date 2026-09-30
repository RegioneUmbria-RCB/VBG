// -----------------------------------------------------------------------
// <copyright file="ValoreCampoPresente.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure;
	using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ValoreCampoPresente : ISpecification<DatiPdfCompilabile>
	{
		string _nomeCampo;

		public ValoreCampoPresente(string nomeCampo)
		{
			this._nomeCampo = nomeCampo;
		}

		public bool IsSatisfiedBy(DatiPdfCompilabile datiPdf)
		{
			var valore = datiPdf.Valore(this._nomeCampo);

			return !String.IsNullOrEmpty(valore);
		}
	}
}
