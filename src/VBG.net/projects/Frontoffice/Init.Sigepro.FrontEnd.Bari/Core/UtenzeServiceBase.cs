// -----------------------------------------------------------------------
// <copyright file="UtenzeServiceBase.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class UtenzeServiceBase
	{
		protected enum TipoRichiestaUtenzaEnum
		{
			CodiceFiscale,
			PartitaIva
		}

		protected TipoRichiestaUtenzaEnum GetTipoRichiesta(string codFiscaleOCodUtente)
		{
			Int32 codiceUtente = 0;

			if (Int32.TryParse(codFiscaleOCodUtente, out codiceUtente))
				return TipoRichiestaUtenzaEnum.PartitaIva;

			return TipoRichiestaUtenzaEnum.CodiceFiscale;
		}
	}
}
