// -----------------------------------------------------------------------
// <copyright file="TributiConfigService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Core.Config
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public class TributiConfigService : ConfigServiceBase, Init.Sigepro.FrontEnd.Bari.Core.Config.ITributiConfigService
	{
		internal TributiConfigService(IConfigServiceUrl sigeproService, IToken token, ISoftware software)
			: base(sigeproService, token, software)
		{
		}

		public ParametriServizioTributi Read()
		{
			using (var ws = CreaClientWsSigepro())
			{
				var configurazione = ws.GetConfigurazioneTares(base.GetToken(), base.GetSoftware());

				if (configurazione == null)
					return new ParametriServizioTributi();

				return new ParametriServizioTributi
				{
					IdentificativoUtente = configurazione.IdentificativoUtente,
					Password = configurazione.Password,
					UrlServizioTares = configurazione.UrlServizioAgevolazioniTares,
					UrlServizioTasi = configurazione.UrlServizioAgevolazioniTasi,
					UrlServizioImu = configurazione.UrlServizioAgevolazioniImu,
                    CodiceFiscaleCafFittizio = configurazione.CodiceFiscaleCafFittizio,
                    EmailCafFittizio = configurazione.EmailCafFittizio,
					UrlServizioFirmaCidPin = configurazione.UrlServizioFirmaCid
				};
			}
		}

	}
}
