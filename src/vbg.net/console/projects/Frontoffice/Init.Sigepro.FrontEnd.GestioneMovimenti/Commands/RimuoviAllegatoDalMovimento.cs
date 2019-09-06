// -----------------------------------------------------------------------
// <copyright file="RimuoviAllegatoDalMovimento.cs" company="">
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
	/// Comando da utilizzare per rimuovere un allegato da un movimento di frontoffice
	/// </summary>
	public class RimuoviAllegatoDalMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdAllegato;

		public RimuoviAllegatoDalMovimento(string idComune, int idMovimento, int idAllegato)
		{
			// TODO: Complete member initialization
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdAllegato = idAllegato;
		}
	}
}
