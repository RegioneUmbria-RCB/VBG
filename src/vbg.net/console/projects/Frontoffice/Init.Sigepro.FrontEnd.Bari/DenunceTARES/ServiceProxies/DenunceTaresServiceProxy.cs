// -----------------------------------------------------------------------
// <copyright file="DenunceTaresServiceProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.ServiceProxies
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
	using System.ServiceModel;
using log4net;
using Init.Sigepro.FrontEnd.Bari.Core.Config;
	using Init.Sigepro.FrontEnd.Bari.TARES;
	using Init.Sigepro.FrontEnd.Bari.TARES.Crypto;
	using System.Text.RegularExpressions;
	using Init.Utils;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DenunceTaresServiceProxy : IDenunceTaresServiceProxy
	{
		ILog _log = LogManager.GetLogger(typeof(DenunceTaresServiceProxy));
		ParametriServizioTributi _configurazione;

		public DenunceTaresServiceProxy(TributiConfigService configReader)
		{
			this._configurazione = configReader.Read();
		}

		public datiContribuenteResponseType GetUtenze(DatiOperatore operatore, DatiUtenza utenza)
		{
			using (var ws = CreaClientWsComune())
			{
				try
				{
					var dataRichiesta = new TaresDate();
					var password = new TaresServicePassword(this._configurazione.IdentificativoUtente, this._configurazione.Password, dataRichiesta);
					var idRichiesta = new TaresIdRichiesta(this._configurazione.IdentificativoUtente, TaresIdRichiesta.NomeServizio.getUtenzeStatoPratiche, dataRichiesta);

					var datiAutorizzazione = new datiAutorizzazioneType
					{
						dataRichiesta = dataRichiesta.ToDateString(),
						identificativoServizio = datiAutorizzazioneTypeIdentificativoServizio.S03,
						identificativoUtente = this._configurazione.IdentificativoUtente,
						idRichiesta = idRichiesta.ToString(),
						oraRichiesta = dataRichiesta.ToTimeString(),
						password = password.ToString()
					};

					var datiTracciamento = new datiTracciamentoRequestType
					{
						nomeOperatore = operatore.Nome,
						codFiscaleOperatore = String.IsNullOrEmpty(operatore.CodiceFiscale) ? this._configurazione.CodiceFiscaleCafFittizio : operatore.CodiceFiscale,
						emailOperatore = String.IsNullOrEmpty(operatore.Email) ? this._configurazione.EmailCafFittizio : operatore.Email
					};

					var datiIdentificativi = new datiIdentificativiRequestType
					{
						idContribuente = utenza.IdentificativoContribuente,
						Item = utenza.CodiceFiscaleOPartitIva,
						ItemElementName = utenza.Tipodato
					};

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tares \"getUtenzeStatoPratiche\" con datiAutorizzazione: {0}\r\n e datiIdentificativi: {1}", StreamUtils.SerializeClass(datiAutorizzazione), StreamUtils.SerializeClass(datiIdentificativi));

					var utenze = ws.getUtenzeStatoPratiche( new getUtenzeStatoPraticheRequestType
					{
						datiAutorizzazione = datiAutorizzazione,
						datiTracciamento = datiTracciamento,
						datiIdentificativi = datiIdentificativi
					});

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tares \"getUtenzeStatoPratiche\" terminato con successo: {0}", StreamUtils.SerializeClass(utenze));

					if (utenze.@return.datiContribuente == null && !String.IsNullOrEmpty(utenze.@return.messaggio))
					{
						_log.ErrorFormat("Il servizio ha restituito il seguente codice errore: {0} - {1}", utenze.@return.code, utenze.@return.messaggio);
						throw new Exception(utenze.@return.messaggio);
					}

					return utenze.@return.datiContribuente;
				}
				catch (Exception ex)
				{
					ws.Abort();

					_log.ErrorFormat("Errore durante l'invocazione del servizio tares \"getUtenzeStatoPratiche\":  {0}", ex.ToString());

					throw;
				}
			}
		}

		private TaresServicePortTypeClient CreaClientWsComune()
		{
			var binding = new BasicHttpBinding("defaultServiceBinding");
			var endpoint = new EndpointAddress(this._configurazione.UrlServizioTares);

			return new TaresServicePortTypeClient(binding, endpoint);
		}



		public bool IsContribuenteEsistente(DatiOperatore operatore, DatiUtenza utenza)
		{
			using (var ws = CreaClientWsComune())
			{
				try
				{
					var dataRichiesta = new TaresDate();
					var password = new TaresServicePassword(this._configurazione.IdentificativoUtente, this._configurazione.Password, dataRichiesta);
					var idRichiesta = new TaresIdRichiesta(this._configurazione.IdentificativoUtente, TaresIdRichiesta.NomeServizio.isContribuenteEsistente, dataRichiesta);

					var datiAutorizzazione = new datiAutorizzazioneType
					{
						dataRichiesta = dataRichiesta.ToDateString(),
						identificativoServizio = datiAutorizzazioneTypeIdentificativoServizio.S10,
						identificativoUtente = this._configurazione.IdentificativoUtente,
						idRichiesta = idRichiesta.ToString(),
						oraRichiesta = dataRichiesta.ToTimeString(),
						password = password.ToString()
					};

					var datiTracciamento = new datiTracciamentoRequestType
					{
						nomeOperatore = operatore.Nome,
						codFiscaleOperatore = operatore.CodiceFiscale,
						emailOperatore = operatore.Email,
					};

					var datiIdentificativi = new datiControlloContribuenteRequestType
					{
						Item = utenza.CodiceFiscaleOPartitIva,
						ItemElementName = utenza.Tipodato2
					};

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tares \"isContribuenteEsistente\" con datiAutorizzazione: {0}\r\n e datiIdentificativi: {1}", StreamUtils.SerializeClass(datiAutorizzazione), StreamUtils.SerializeClass(datiIdentificativi));

					var utenze = ws.isContribuenteEsistente(new isContribuenteEsistenteRequestType
					{
						datiAutorizzazione = datiAutorizzazione,
						datiTracciamento = datiTracciamento,
						datiIdentificativi = datiIdentificativi
					});

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tares \"isContribuenteEsistente\" terminato con successo: {0}", StreamUtils.SerializeClass(utenze));

					return utenze.@return.code == TaresServiceReturnCodes.CONTRIBUENTE_ESISTENTE;
				}
				catch (Exception ex)
				{
					ws.Abort();

					_log.ErrorFormat("Errore durante l'invocazione del servizio tares \"isContribuenteEsistente\":  {0}", ex.ToString());

					throw;
				}
			}
		}
	}
}
