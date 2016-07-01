// -----------------------------------------------------------------------
// <copyright file="DatiRiepilogoSchedaDinamica.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiRiepilogoSchedaDinamica
	{
		public int IdScheda { get; set; }
		public string NomeFile { get; set; }
		public int? IdAllegato { get; set; }
		public bool FirmatoDigitalmente { get; set; }

		public DatiRiepilogoSchedaDinamica()
		{
			FirmatoDigitalmente = true;
		}
	}
}
