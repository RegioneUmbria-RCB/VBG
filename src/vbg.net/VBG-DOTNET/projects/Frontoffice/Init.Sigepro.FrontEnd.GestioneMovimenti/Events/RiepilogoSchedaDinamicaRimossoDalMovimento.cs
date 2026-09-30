// -----------------------------------------------------------------------
// <copyright file="RiepilogoSchedaDinamicaRimossoDalMovimento.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Events
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RiepilogoSchedaDinamicaRimossoDalMovimento : Event
	{
		public string IdComune { get; set; }
		public int IdMovimento { get; set; }
		public int IdSchedaDinamica { get; set; }
		public int IdAllegato { get; set; }
	}
}
