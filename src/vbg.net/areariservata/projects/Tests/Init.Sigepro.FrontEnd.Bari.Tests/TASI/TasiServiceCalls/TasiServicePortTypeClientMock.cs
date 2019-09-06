// -----------------------------------------------------------------------
// <copyright file="TasiServicePortTypeClientMock.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Tests.TASI.TasiServiceCalls
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TasiServicePortTypeClientMock : ITasiServicePortTypeClient
	{
		public datiImmobiliContribuenteResponse DatiImmobiliContribuenteResponse { get; set; }
		public datiAutorizzazioneType DatiAutorizzazione{get;set;}
		public datiIdentificativiType DatiIdentificativi { get; set; }


		public datiImmobiliContribuenteResponse getDatiImmobili(datiAutorizzazioneType datiAutorizzazione, datiIdentificativiType datiIdentificativi)
		{
			this.DatiAutorizzazione = datiAutorizzazione;
			this.DatiIdentificativi = datiIdentificativi;

			return DatiImmobiliContribuenteResponse;
		}

		public commonResponseType setAgevolazioneCaf(datiAutorizzazioneType datiAutorizzazione, datiTracciamentoCafType datiTracciamento, richiestaAgevolazioneRequestType datiRichiesta)
		{
			throw new NotImplementedException();
		}

		public void Abort()
		{
		}

		public void Dispose()
		{
		}
	}
}
