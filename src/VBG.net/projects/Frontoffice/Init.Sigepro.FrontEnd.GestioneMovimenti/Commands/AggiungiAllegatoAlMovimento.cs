// -----------------------------------------------------------------------
// <copyright file="AllegatoAggiuntoAlMovimento.cs" company="">
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
	/// Comando da utilizzare per aggiungere un allegato ad un movimento di frontoffice
	/// </summary>
	public class AggiungiAllegatoAlMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdAllegato;
		public readonly string NomeFile;
		public readonly string Descrizione;

		public AggiungiAllegatoAlMovimento(string idComune, int idMovimento, int idAllegato, string nomeFile, string descrizione)
		{
			// TODO: Complete member initialization
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdAllegato = idAllegato;
			this.NomeFile = nomeFile;
			this.Descrizione = descrizione;
		}
	}

	/// <summary>
	/// Comando da utilizzare per aggiungere un allegato ad un movimento di frontoffice
	/// </summary>
	public class AggiungiAllegatoAlMovimentoV2 : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdAllegato;
		public readonly string NomeFile;
		public readonly string Descrizione;
		public readonly bool FirmatoDigitalmente;

		public AggiungiAllegatoAlMovimentoV2(string idComune, int idMovimento, int idAllegato, string nomeFile, string descrizione,bool firmatoDigitalmente)
		{
			// TODO: Complete member initialization
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdAllegato = idAllegato;
			this.NomeFile = nomeFile;
			this.Descrizione = descrizione;
			this.FirmatoDigitalmente = firmatoDigitalmente;
		}
	}
}
