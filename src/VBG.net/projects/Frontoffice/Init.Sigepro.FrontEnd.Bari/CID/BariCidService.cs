// -----------------------------------------------------------------------
// <copyright file="BariCidService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy;
	using Init.Sigepro.FrontEnd.Bari.CID.DTOs;
	using System.ServiceModel;
	using log4net;
	using Init.Utils;
using Init.Sigepro.FrontEnd.Bari.Core.Config;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BariCidService : IBariCidService
	{
		DatiConfigurazioneDto _configurazione;
		ILog _log = LogManager.GetLogger(typeof(BariCidService));
        ITributiConfigService _tributiConfig;

		public BariCidService(ICidConfigService configurazione, ITributiConfigService tributiConfig)
		{
			this._configurazione = configurazione.GetConfigurazioneCid();
            this._tributiConfig = tributiConfig;
		}



		public DatiCidDto GetDatiCid(DatiOperatoreDto operatore, DatiRichiestaDto richiesta)
		{
			using (var ws = CreateClient())
			{
				try
				{
					var request = new getPinCidRequestType
					{
						datiAutorizzazione = new DatiAutorizzazioneCid(this._configurazione.Username, this._configurazione.Password).ToDatiAutorizzazioneType(),
						datiIdentificativi = richiesta.ToDatiIdentificativiCidRequestType(),
						datiTracciamento = operatore.ToDatiTracciamentoRequestType()
					};

					if (_log.IsDebugEnabled)
					{
						_log.DebugFormat("Richiesta al servizio CID: {0}", StreamUtils.SerializeClass(request));
					}

					var result = ws.getPinCid(request);

					if (_log.IsDebugEnabled)
					{
						_log.DebugFormat("Risposta del servizio CID: {0}", StreamUtils.SerializeClass(result));
					}

					if (result.@return.code != 0 && result.@return.code != 1977745626)
					{
						throw new Exception(result.@return.messaggio);
					}

					var cf = result.@return.datiContribuente.codiceFiscale;
					var pin = result.@return.datiContribuente.pin;
					var cid = result.@return.datiContribuente.cid;
					var dataNascita = result.@return.datiContribuente.dataNascita;
					var nominativo = result.@return.datiContribuente.nominativo;

					return new DatiCidDto(cf, nominativo, dataNascita, pin, cid);
				}
				catch (Exception ex)
				{
					ws.Abort();

					_log.ErrorFormat("Errore durante la chiamata al servizio Cid: {0}", ex.ToString());

					throw;
				}
			}

		}

        public DatiCidDto ValidaCidPin(string cid, string pin)
        {
            try
            {
                this._log.DebugFormat("Validazione del cid {0}", cid);

                var config = this._tributiConfig.Read();

                var operatore = new DatiOperatoreDto("operatore fittizio", config.EmailCafFittizio, config.CodiceFiscaleCafFittizio);
                var richiesta = new DatiRichiestaDto(cid, null);

                var dati = GetDatiCid(operatore, richiesta);

                if (dati == null)
                {
                    this._log.ErrorFormat("Validazione del cid {0} fallita, codice contribuente non valido", cid);

                    return null;
                }

                if (dati.Pin != pin)
                {
                    this._log.ErrorFormat("Validazione del cid {0} fallita, il pin immesso non coincide con quello del contribuente ({1})", cid, pin);

                    return null;
                }

                return dati;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Validazione del cid {0} fallita, {1}", cid, ex.ToString());

                return null;
            }
        }

		private CidServicePortTypeClient CreateClient()
		{
			var binding = new BasicHttpBinding("defaultServiceBinding");
			var endpoint = new EndpointAddress(this._configurazione.ServiceUrl);

			return new CidServicePortTypeClient(binding, endpoint);
		}
	}
}
