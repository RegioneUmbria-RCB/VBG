// -----------------------------------------------------------------------
// <copyright file="CompletaCompilazioneSchedaDinamica.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Commands
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CompletaCompilazioneSchedaDinamica : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdScheda;

		public CompletaCompilazioneSchedaDinamica(string idComune, int idMovimento, int idScheda)
		{
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdScheda = idScheda;
		}

	}
}
