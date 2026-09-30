using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneStradario.RicercaStradario
{
    internal class TokenizerPartiEspressione
    {
		public static string[] PAROLE_RISERVATE = {
			"STR",
			"PIAZZA",
			"PZZA",
			"PONTE",
			"CORSO",
			"CALA",
			"STRADA",
			"PIAZZALE",
			"PARALLELA",
			"PIAZZETTA",
			"SALITA",
			"CHIASSO",
			"ARCO",
			"LARGO",
			"STRADETTA",
			"TRAVERSA",
			"RACCORDO",
			"TRATTO",
			"VIA",
			"CORTE",
			"STRADELLA",
			"LUNGOMARE",
			"PIAZZA",
			"PROLUNGAMENTO",
			"VIALE",
			"VICO",
			"CONTRADA",
			"SOTTOVIA",
			"MOLO"
		};
		private readonly string _indirizzoParziale;

		public TokenizerPartiEspressione(string indirizzoParziale)
        {
			_indirizzoParziale = indirizzoParziale;
		}

		public IEnumerable<string> GetTokens()
		{
			var partiIndirizzo = _indirizzoParziale.Trim().Replace(".", ". ").Split(' ');

			return partiIndirizzo.Where(x => !PAROLE_RISERVATE.Contains(x.ToUpperInvariant()) && x.Length >= 2);
		}
	}
}
