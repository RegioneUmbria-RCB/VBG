// -----------------------------------------------------------------------
// <copyright file="ModificaNoteMovimento.cs" company="">
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
	/// Comando da utilizzare per modificare le note di un movimento di frontoffice
	/// </summary>
	public class ModificaNoteMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly string TestoNote;

		public ModificaNoteMovimento(string idComune, int idMovimento, string testoNote)
		{
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.TestoNote = testoNote;
		}
	}
}
