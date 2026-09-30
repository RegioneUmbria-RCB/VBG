// -----------------------------------------------------------------------
// <copyright file="AllegaRiepilogoSchedaDinamicaAMovimento.cs" company="">
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
	/// Comando da utilizzare per aggiungere un riepilogo di una scheda dinamica ad un movimento di frontoffice
	/// </summary>
	public class AllegaRiepilogoSchedaDinamicaAMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdSchedaDinamica;
		public readonly int IdAllegato;
		public readonly string NomeFile;

		public AllegaRiepilogoSchedaDinamicaAMovimento(string idComune, int idMovimento, int idSchedaDinamica, int idAllegato, string nomeFile)
		{
			// TODO: Complete member initialization
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdSchedaDinamica = idSchedaDinamica;
			this.IdAllegato = idAllegato;
			this.NomeFile = nomeFile;
		}
	}

	/// <summary>
	/// Comando da utilizzare per aggiungere un riepilogo di una scheda dinamica ad un movimento di frontoffice
	/// </summary>
	public class AllegaRiepilogoSchedaDinamicaAMovimentoV2 : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdSchedaDinamica;
		public readonly int IdAllegato;
		public readonly string NomeFile;
		public readonly bool FirmatoDigitalmente;

		public AllegaRiepilogoSchedaDinamicaAMovimentoV2(string idComune, int idMovimento, int idSchedaDinamica, int idAllegato, string nomeFile, bool firmatoDigitalmente)
		{
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdSchedaDinamica = idSchedaDinamica;
			this.IdAllegato = idAllegato;
			this.NomeFile = nomeFile;
			this.FirmatoDigitalmente = firmatoDigitalmente;
		}
	}
}
