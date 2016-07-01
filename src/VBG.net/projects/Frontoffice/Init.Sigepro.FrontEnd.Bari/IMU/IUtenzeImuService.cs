// -----------------------------------------------------------------------
// <copyright file="IUtenzeImuService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IUtenzeImuService
	{
		DatiContribuenteImuDto GetDatiImmobili(string codiceFiscaleOperatore, string codFiscaleOPIvaUtente);
	}	
}
