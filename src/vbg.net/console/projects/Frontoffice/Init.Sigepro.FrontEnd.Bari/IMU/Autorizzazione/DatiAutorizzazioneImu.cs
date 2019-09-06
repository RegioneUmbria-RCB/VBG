// -----------------------------------------------------------------------
// <copyright file="DatiAutorizzazioneImu.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU.Autorizzazione
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.IMU.wsdl;
	using log4net;
	using Init.Sigepro.FrontEnd.Bari.Core.Autorizzazione;
	using Init.Utils;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiAutorizzazioneImu : DatiAutorizzazioneBase
	{
		private static class Constants
		{
			public const string NomeServizio = "getDatiContribuente";
		}

		datiAutorizzazioneImuTypeIdentificativoServizio _identificativoServizio = datiAutorizzazioneImuTypeIdentificativoServizio.I01;

		ILog _log = LogManager.GetLogger(typeof(DatiAutorizzazioneImu));

		public DatiAutorizzazioneImu(string identificativoUtente, string passphrase, DateTime? dataRichiesta = null)
			: base(identificativoUtente, passphrase, Constants.NomeServizio, dataRichiesta)
		{
		}

		public datiAutorizzazioneImuType ToDatiAutorizzazioneType()
		{
			var autorizzazione = new datiAutorizzazioneImuType
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
				_log.DebugFormat("DatiAutorizzazioneImu: dati autorizzazione IMU= {0}", StreamUtils.SerializeClass(autorizzazione));
			}

			return autorizzazione;
		}
	}
}
