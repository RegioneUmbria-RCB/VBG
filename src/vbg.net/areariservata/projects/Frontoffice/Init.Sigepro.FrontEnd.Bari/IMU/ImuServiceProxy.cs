// -----------------------------------------------------------------------
// <copyright file="ImuServiceProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
	using Init.Sigepro.FrontEnd.Bari.IMU.wsdl;
	using Init.Utils;
	using Init.Sigepro.FrontEnd.Bari.IMU.Autorizzazione;
	using System.ServiceModel;
	using log4net;
	using Init.Sigepro.FrontEnd.Bari.Core.Config;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ImuServiceProxy : IImuServiceProxy
	{
		ILog _log = LogManager.GetLogger(typeof(ImuServiceProxy));
		ParametriServizioTributi _configurazione;
		IMapTo<datiContribuenteImuResponseType, DatiContribuenteImuDto> _resultMapper;

		internal ImuServiceProxy(ITributiConfigService configService, IMapTo<datiContribuenteImuResponseType, DatiContribuenteImuDto> resultMapper)
		{
			this._configurazione = configService.Read();
			this._resultMapper = resultMapper;
		}

		public DatiContribuenteImuDto GetDatiContribuenteByCodiceFiscale(string codiceFiscaleCaf, string indirizzoPecCaf, string codiceFiscaleUtenza)
		{
			return GetDatiContribuente(codiceFiscaleCaf, indirizzoPecCaf, codiceFiscaleUtenza);
		}

		public DatiContribuenteImuDto GetDatiContribuenteByPartitaIva(string codiceFiscaleCaf, string indirizzoPecCaf, int partitaIvaUtente)
		{
			var partitaIva = partitaIvaUtente.ToString().PadLeft(11, '0');

			return GetDatiContribuente(codiceFiscaleCaf, indirizzoPecCaf, partitaIva);
		}

		private DatiContribuenteImuDto GetDatiContribuente(string codiceFiscaleCaf, string indirizzoPecCaf,string pivaOCf)
		{
			using (var ws = CreateClient())
			{
				try
				{
					var outTmp = 0.0m;

                    if (String.IsNullOrEmpty(codiceFiscaleCaf))
                        codiceFiscaleCaf = this._configurazione.CodiceFiscaleCafFittizio;

                    if (String.IsNullOrEmpty(indirizzoPecCaf))
                        indirizzoPecCaf = this._configurazione.EmailCafFittizio;

					var datiAutorizzazione = new DatiAutorizzazioneImu(this._configurazione.IdentificativoUtente, this._configurazione.Password).ToDatiAutorizzazioneType();
					var datiIdentificativi = new datiIdentificativiImuType
					{
						indirizzoPec = indirizzoPecCaf,

						Item = codiceFiscaleCaf,
						ItemElementName = decimal.TryParse(codiceFiscaleCaf, out outTmp) ? ItemChoiceType.partitaIVA: ItemChoiceType.codiceFiscale,

						Item1 = pivaOCf.ToString(),
						Item1ElementName = decimal.TryParse(pivaOCf, out outTmp) ? Item1ChoiceType.partitaIVAContribuente : Item1ChoiceType.codiceFiscaleContribuente
					};

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio Imu \"getDatiImmobili\" con datiAutorizzazione: {0}\r\n e datiIdentificativi: {1}", StreamUtils.SerializeClass(datiAutorizzazione), StreamUtils.SerializeClass(datiIdentificativi));


					var result = ws.getDatiContribuente(datiAutorizzazione, datiIdentificativi);

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio Imu \"getDatiContribuente\" terminato con successo: {0}", StreamUtils.SerializeClass(result));

                    if (result.code == 120)
                    {
                        throw new ImuServiceInvocationException("Contribuente non trovato");
                    }
                    
					if (result.code != 0 && !String.IsNullOrEmpty(result.messaggio))
                    {
                        throw new ImuServiceInvocationException(result.messaggio);
                    }

					if (result.datiContribuente == null)
                    {
                        throw new ImuServiceInvocationException("Contribuente non trovato");
                    }

					return this._resultMapper.Map(result.datiContribuente);
				}
				catch (ImuServiceInvocationException ex)
				{
					this._log.ErrorFormat("Si è verificato un errore gestito durante l'invocazione del servizio Imu: {0}", ex.ToString());

					throw;
				}
				catch (Exception ex)
				{
					ws.Abort();

					this._log.ErrorFormat("Si è verificato un errore imprevisto durante l'invocazione del servizio Imu: {0}", ex.ToString());


					throw;
				}
			}
		}

		protected virtual IImuServicePortTypeClient CreateClient()
		{
			var binding = new BasicHttpBinding("defaultServiceBinding");
			var endpoint = new EndpointAddress(this._configurazione.UrlServizioImu);

			return new ImuServicePortTypeClient(binding, endpoint);
		}
	}
}
