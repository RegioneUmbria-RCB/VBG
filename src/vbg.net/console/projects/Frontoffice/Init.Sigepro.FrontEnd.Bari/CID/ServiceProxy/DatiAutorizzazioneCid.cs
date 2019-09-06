// -----------------------------------------------------------------------
// <copyright file="DatiAutorizzazioneCid.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Security.Cryptography;
	using log4net;
	using Init.Utils;
	using System.Globalization;
	using Init.Sigepro.FrontEnd.Bari.Core.Autorizzazione;


	public class DatiAutorizzazioneCid : DatiAutorizzazioneBase
	{
		private static class Constants
		{
			public const string NomeServizio = "getPinCid";
		}

		datiAutorizzazioneTypeIdentificativoServizio _identificativoServizio = datiAutorizzazioneTypeIdentificativoServizio.S00;

		ILog _log = LogManager.GetLogger(typeof(DatiAutorizzazioneCid));

		public DatiAutorizzazioneCid(string identificativoUtente, string passphrase, DateTime? dataRichiesta = null)
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
