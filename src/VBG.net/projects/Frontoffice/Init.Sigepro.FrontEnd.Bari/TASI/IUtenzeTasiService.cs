// -----------------------------------------------------------------------
// <copyright file="IUtenzeTasiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IUtenzeTasiService
	{
		DatiContribuenteTasiDto GetDatiImmobili(string codiceFiscaleOperatore, string codFiscaleOPIvaUtente);
	}
}
