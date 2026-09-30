// -----------------------------------------------------------------------
// <copyright file="RimuoviRiepilogoSchedaDinamicaDalMovimento.cs" company="">
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
	/// Comando da utilizzare per rimuovere il riepilogo di una scheda dinamica da un movimento di frontoffice
	/// </summary>
	public class RimuoviRiepilogoSchedaDinamicaDalMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdSchedaDinamica;

		public RimuoviRiepilogoSchedaDinamicaDalMovimento(string idComune, int idMovimento, int idSchedaDinamica)
		{
			// TODO: Complete member initialization
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdSchedaDinamica = idSchedaDinamica;
		}
	}
}
