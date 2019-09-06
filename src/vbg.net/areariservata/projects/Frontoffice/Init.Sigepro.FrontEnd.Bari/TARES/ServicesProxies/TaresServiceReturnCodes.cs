// -----------------------------------------------------------------------
// <copyright file="TaresServiceReturnCodes.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public static class TaresServiceReturnCodes
	{
		public const int CONTRIBUENTE_ESISTENTE = 200;
		public const int CONTRIBUENTE_NON_ESISTENTE = 201;
		public const int ERRORE_GENERICO	= 999;
		public const int ERRORE_VALIDAZIONE = 111;
		public const int NESSUN_RISULTATO = 120;
		public const int RICHIESTA_ESISTENTE = 112;
		public const int RICHIESTA_NON_VALIDA = 110;
		public const int SUCCESSO = 0;
		public const int UTENTE_NON_ABILITATO = 101;
		public const int UTENTE_PASS_ERRATI = 100;
	}
}
