// -----------------------------------------------------------------------
// <copyright file="UtenzeTasiService.cs" company="">
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
	using Init.Sigepro.FrontEnd.Bari.Core.CafServices;
	using Init.Sigepro.FrontEnd.Bari.Core;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class UtenzeTasiService : UtenzeServiceBase, IUtenzeTasiService
	{

		ICafServiceProxy _cafServiceProxy;
		ITasiServiceProxy _tasiServiceProxy;

		internal UtenzeTasiService(ICafServiceProxy cafServiceProxy, ITasiServiceProxy tasiServiceProxy)
		{
			this._cafServiceProxy = cafServiceProxy;
			this._tasiServiceProxy = tasiServiceProxy;
		}

		public DatiContribuenteTasiDto GetDatiImmobili(string cfOperatore, string cfOPIvaUtente)
		{
			var codiceFiscaleOperatore = cfOperatore.ToUpper();
			var codFiscaleOPIvaUtente = cfOPIvaUtente.ToUpper();

			var riferimentiCaf = this._cafServiceProxy.GetRiferimentiCafDaCodiceFiscaleoperatore(codiceFiscaleOperatore);

			if (GetTipoRichiesta(codFiscaleOPIvaUtente) == TipoRichiestaUtenzaEnum.CodiceFiscale)
				return this._tasiServiceProxy.GetDatiContribuenteByCodiceFiscale(riferimentiCaf.CodiceFiscale, riferimentiCaf.IndirizzoPec, codFiscaleOPIvaUtente);

			return this._tasiServiceProxy.GetDatiContribuenteByPartitaIva(riferimentiCaf.CodiceFiscale, riferimentiCaf.IndirizzoPec, Convert.ToInt32(codFiscaleOPIvaUtente));
		}
	}
}
