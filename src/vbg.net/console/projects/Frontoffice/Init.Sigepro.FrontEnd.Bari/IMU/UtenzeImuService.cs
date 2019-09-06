// -----------------------------------------------------------------------
// <copyright file="UtenzeImuService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.Core.CafServices;
	using Init.Sigepro.FrontEnd.Bari.Core;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class UtenzeImuService : UtenzeServiceBase, IUtenzeImuService
	{
		ICafServiceProxy _cafServiceProxy;
		IImuServiceProxy _imuServiceProxy;

		internal UtenzeImuService(ICafServiceProxy cafServiceProxy, IImuServiceProxy tasiServiceProxy)
		{
			this._cafServiceProxy = cafServiceProxy;
			this._imuServiceProxy = tasiServiceProxy;
		}

		public DatiContribuenteImuDto GetDatiImmobili(string cfOperatore, string cfOPIvaUtente)
		{
			var codiceFiscaleOperatore = cfOperatore.ToUpper();
			var codFiscaleOPIvaUtente = cfOPIvaUtente.ToUpper();

			var riferimentiCaf = this._cafServiceProxy.GetRiferimentiCafDaCodiceFiscaleoperatore(codiceFiscaleOperatore);

            var codiceFiscaleCaf = riferimentiCaf == null ? String.Empty : riferimentiCaf.CodiceFiscale;
            var pecCaf = riferimentiCaf == null ? String.Empty : riferimentiCaf.IndirizzoPec;            

			if (GetTipoRichiesta(codFiscaleOPIvaUtente) == TipoRichiestaUtenzaEnum.CodiceFiscale)
                return this._imuServiceProxy.GetDatiContribuenteByCodiceFiscale(codiceFiscaleCaf, pecCaf, codFiscaleOPIvaUtente);

            return this._imuServiceProxy.GetDatiContribuenteByPartitaIva(codiceFiscaleCaf, pecCaf, Convert.ToInt32(codFiscaleOPIvaUtente));
		}

	}
}
