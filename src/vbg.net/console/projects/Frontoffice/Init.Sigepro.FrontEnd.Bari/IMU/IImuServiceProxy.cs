// -----------------------------------------------------------------------
// <copyright file="IImuServiceProxy.cs" company="">
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
	public interface IImuServiceProxy
	{
		DatiContribuenteImuDto GetDatiContribuenteByCodiceFiscale(string codiceFiscaleCaf, string indirizzoPecCaf, string codiceFiscaleUtenza);
		DatiContribuenteImuDto GetDatiContribuenteByPartitaIva(string codiceFiscaleCaf, string indirizzoPecCaf, int partitaIvaUtente);
	}
}
