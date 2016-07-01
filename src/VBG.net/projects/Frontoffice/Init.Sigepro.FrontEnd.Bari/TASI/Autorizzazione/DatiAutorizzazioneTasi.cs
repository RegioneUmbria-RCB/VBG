// -----------------------------------------------------------------------
// <copyright file="DatiAutorizzazioneTasi.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI.Autorizzazione
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;
	using log4net;
	using Init.Sigepro.FrontEnd.Bari.Core.Autorizzazione;
	using Init.Utils;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiAutorizzazioneTasi : DatiAutorizzazioneBase
	{
		private static class Constants
		{
			public const string NomeServizio = "getDatiImmobili";
		}

		datiAutorizzazioneTypeIdentificativoServizio _identificativoServizio = datiAutorizzazioneTypeIdentificativoServizio.TS01;

		ILog _log = LogManager.GetLogger(typeof(DatiAutorizzazioneTasi));

		public DatiAutorizzazioneTasi(string identificativoUtente, string passphrase, DateTime? dataRichiesta = null)
			:base(identificativoUtente, passphrase, Constants.NomeServizio, dataRichiesta)
		{
		}

		public datiAutorizzazioneType ToDatiAutorizzazioneType()
		{
			var autorizzazione = new datiAutorizzazioneType
			{
				dataRichiesta = this.GetDateString(),
				identificativoServizio = this._identificativoServizio,
				identificativoUtente = this._identificativoUtente,
				idRichiesta = GetIdRichiesta(),
				oraRichiesta = GetTimeString(),
				password = GetPassword()
			};

			if (_log.IsDebugEnabled)
			{
				_log.DebugFormat("DatiAutorizzazioneCid: dati autorizzazione CID= {0}", StreamUtils.SerializeClass(autorizzazione));
			}

			return autorizzazione;
		}
	}
}
