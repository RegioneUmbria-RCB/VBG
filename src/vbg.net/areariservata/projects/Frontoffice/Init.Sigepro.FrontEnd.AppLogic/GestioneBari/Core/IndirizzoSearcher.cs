// -----------------------------------------------------------------------
// <copyright file="ImdirizzoSearcher.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class IndirizzoSearcher
	{
		public class MatchIndirizzo
		{
			public readonly int Codice;
			public readonly string Descrizione;

			private MatchIndirizzo(int codice, string descrizione)
			{
				this.Codice = codice;
				this.Descrizione = descrizione;
			}

			public MatchIndirizzo(StradarioDto stradario)
			{
				this.Codice = stradario.CodiceStradario;
				this.Descrizione = stradario.NomeVia;
			}


			public static MatchIndirizzo NonTrovato(int idCodiceNonDefinito)
			{
				return new MatchIndirizzo(idCodiceNonDefinito, "Non definito");
			}
		}

		LocalizzazioniService _stradarioService;
		string _idcomune;
		int _idIndirizzoNonDefinito;

		public IndirizzoSearcher(string idcomune, LocalizzazioniService stradarioService, int idIndirizzoNonDefinito)
		{
			this._stradarioService = stradarioService;
			this._idcomune = idcomune;
			this._idIndirizzoNonDefinito = idIndirizzoNonDefinito;
		}

		public MatchIndirizzo TrovaDaMatchParziale(string codiceComune, string comuneLocalizzazione, string nomeVia)
		{
			var indirizziTrovati = this._stradarioService.FindByMatchParziale(this._idcomune, codiceComune, comuneLocalizzazione, nomeVia);

			if (indirizziTrovati == null)
				return MatchIndirizzo.NonTrovato(this._idIndirizzoNonDefinito);

			if (indirizziTrovati.Count() == 1)
				return new MatchIndirizzo(indirizziTrovati.First());

			var match = indirizziTrovati.Where(x => x.NomeVia.ToUpperInvariant() == nomeVia.Trim().ToUpperInvariant());

			if (match.Count() != 0)
				return new MatchIndirizzo(match.FirstOrDefault());

			return MatchIndirizzo.NonTrovato(this._idIndirizzoNonDefinito);

		}
	}
}
