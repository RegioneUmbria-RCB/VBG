// -----------------------------------------------------------------------
// <copyright file="DatiRichiestaDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using log4net;
	using Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiRichiestaDto
	{
		public readonly string Cid;
		public readonly string CodiceFiscale;

		ILog _log = LogManager.GetLogger(typeof(DatiRichiestaDto));

		public DatiRichiestaDto(string cid, string codiceFiscale)
		{
			this.Cid = cid;
			this.CodiceFiscale = codiceFiscale ?? String.Empty;
		}

		internal datiIdentificativiCidRequestType ToDatiIdentificativiCidRequestType() {

			_log.DebugFormat("DatiRichiestaDto: dati richiesta Cid: codicefiscale={0}, cid={1}", this.CodiceFiscale, this.Cid);

			var dati = new datiIdentificativiCidRequestType{
				cid = null,
				codiceFiscale = null
			};

			if(!String.IsNullOrEmpty(this.CodiceFiscale.Trim()))
				dati.codiceFiscale = this.CodiceFiscale.Trim().ToUpper();	// il servizio accetta solo richieste con codice fiscale in maiuscolo :P

			if (!String.IsNullOrEmpty(this.Cid.Trim()))
				dati.cid = this.Cid.Trim();

			return dati;
		}
	}
}
