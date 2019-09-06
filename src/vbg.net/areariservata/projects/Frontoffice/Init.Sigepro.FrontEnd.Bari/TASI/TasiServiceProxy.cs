// -----------------------------------------------------------------------
// <copyright file="TasiServiceProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI
{
	using System;
	using System.ServiceModel;
	using Init.Sigepro.FrontEnd.Bari.Core.Config;
	using Init.Sigepro.FrontEnd.Bari.TASI.Autorizzazione;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
	using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;
	using log4net;
	using AutoMapper;
	using Init.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.Mapping;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	internal class TasiServiceProxy : ITasiServiceProxy
	{
		ILog _log = LogManager.GetLogger(typeof(TasiServiceProxy));
		ParametriServizioTributi _configurazione;
		IMapTo<datiImmobiliResponseType, DatiContribuenteTasiDto> _resultMapper;

		internal TasiServiceProxy(ITributiConfigService configService, IMapTo<datiImmobiliResponseType,DatiContribuenteTasiDto> resultMapper)
		{
			this._configurazione = configService.Read();
			this._resultMapper = resultMapper;
		}

		public DatiContribuenteTasiDto GetDatiContribuenteByCodiceFiscale(string codiceFiscaleCaf, string indirizzoPecCaf, string codiceFiscaleUtenza)
		{
			return GetDatiContribuente(codiceFiscaleCaf, indirizzoPecCaf, codiceFiscaleUtenza);
		}

		public DatiContribuenteTasiDto GetDatiContribuenteByPartitaIva(string codiceFiscaleCaf, string indirizzoPecCaf, int partitaIvaUtente)
		{
			var partitaIva = partitaIvaUtente.ToString().PadLeft(11, '0');

			return GetDatiContribuente(codiceFiscaleCaf, indirizzoPecCaf, partitaIva);
		}

		private DatiContribuenteTasiDto GetDatiContribuente(string codiceFiscaleCaf, string indirizzoPecCaf,string pivaOCf)
		{
			using (var ws = CreateClient())
			{
				try
				{
					var outTmp = 0.0m;

					var datiAutorizzazione = new DatiAutorizzazioneTasi(this._configurazione.IdentificativoUtente, this._configurazione.Password).ToDatiAutorizzazioneType();
					var datiIdentificativi = new datiIdentificativiType
					{
						indirizzoPecCAF = indirizzoPecCaf,

						Item = codiceFiscaleCaf,
						ItemElementName = decimal.TryParse(codiceFiscaleCaf, out outTmp) ? ItemChoiceType.partitaIVACAF : ItemChoiceType.codiceFiscaleCAF,

						Item1 = pivaOCf.ToString(),
						Item1ElementName = decimal.TryParse(pivaOCf, out outTmp) ? Item1ChoiceType.partitaIVAContribuente : Item1ChoiceType.codiceFiscaleContribuente
					};

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tasi \"getDatiImmobili\" con datiAutorizzazione: {0}\r\n e datiIdentificativi: {1}", StreamUtils.SerializeClass(datiAutorizzazione), StreamUtils.SerializeClass(datiIdentificativi));

					var result = ws.getDatiImmobili(datiAutorizzazione, datiIdentificativi);

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tasi \"getDatiImmobili\" terminato con successo: {0}", StreamUtils.SerializeClass(result));

                    if (result.code != 0 && !String.IsNullOrEmpty(result.messaggio))
                    {
                        throw new TasiServiceInvocationException(result.messaggio);
                    }

                    if (result.datiImmobiliContribuente == null)
                    {
                        throw new TasiServiceInvocationException("Contribuente non trovato");
                    }

					return this._resultMapper.Map(result.datiImmobiliContribuente);
				}
				catch (TasiServiceInvocationException ex)
				{
					this._log.ErrorFormat("Si è verificato un errore gestito durante l'invocazione del servizio TASI: {0}", ex.ToString());

					throw;
				}
				catch (Exception ex)
				{
					ws.Abort();

					this._log.ErrorFormat("Si è verificato un errore imprevisto durante l'invocazione del servizio TASI: {0}", ex.ToString());


					throw;
				}
			}
		}

		protected virtual ITasiServicePortTypeClient CreateClient()
		{
			var binding = new BasicHttpBinding("defaultServiceBinding");
			var endpoint = new EndpointAddress(this._configurazione.UrlServizioTasi);

			return new TasiServicePortTypeClient(binding, endpoint);
		}
	}
}
