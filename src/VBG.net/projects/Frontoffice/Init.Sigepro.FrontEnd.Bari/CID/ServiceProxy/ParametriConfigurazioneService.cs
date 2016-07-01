// -----------------------------------------------------------------------
// <copyright file="ParametriConfigurazioneService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.ServiceModel;
	using Init.Sigepro.FrontEnd.Bari.CID.DTOs;
	using Init.Sigepro.FrontEnd.Bari.Core;
	using Init.Sigepro.FrontEnd.Bari.BariConfigServiceReference;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ParametriConfigurazioneService : ConfigServiceBase, ICidConfigService
	{
		IConfigServiceUrl _webServiceUrl;
		IToken _token;
		ISoftware _software;

		public ParametriConfigurazioneService(IConfigServiceUrl webServiceUrl, IToken token, ISoftware software)
			:base(webServiceUrl, token, software)
		{
			this._webServiceUrl = webServiceUrl;
			this._token = token;
			this._software = software;
		}

		public DatiConfigurazioneDto GetConfigurazioneCid()
		{
			using (var ws = CreaClientWsSigepro())
			{
				var cfg = ws.GetConfigurazioneCid(this._token.Get(), this._software.Get());

				if (cfg == null)
				{
					throw new Exception("Impossibile leggere la configurazione del servizio CID, Verificare che la verticalizzaizone CID_BARI sia attiva");
				}

				return new DatiConfigurazioneDto(new Uri(cfg.UrlServizio), cfg.IdentificativoUtente, cfg.Password);					
			}
		}


	}
}
