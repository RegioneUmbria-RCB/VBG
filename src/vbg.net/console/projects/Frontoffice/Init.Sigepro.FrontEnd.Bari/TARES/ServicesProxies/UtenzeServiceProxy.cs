using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.Bari.TARES.Crypto;
using AutoMapper;
using log4net;
using Init.Utils;
using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.Bari.Core.Config;

namespace Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies
{
	public class UtenzeServiceProxy : IUtenzeServiceProxy
	{
		ILog _log = LogManager.GetLogger(typeof(UtenzeServiceProxy));
		ParametriServizioTributi _configurazione;

		public UtenzeServiceProxy(TributiConfigService configReader)
		{
			this._configurazione = configReader.Read();
		}


		public IEnumerable<DatiContribuenteDto> GetUtenzeByCodiceFiscale(string codiceFiscaleCaf, string indirizzoPec, string codiceFiscale)
		{
			return GetUtenze(codiceFiscaleCaf, indirizzoPec,codiceFiscale);
		}

		public IEnumerable<DatiContribuenteDto> GetUtenzeByIdentificativoUtenza(string codiceFiscaleCaf, string indirizzoPec, int identificativoUtenza)
		{
			return GetUtenze( codiceFiscaleCaf, indirizzoPec,identificativoUtenza);
		}

		private IEnumerable<DatiContribuenteDto> GetUtenze(string codiceFiscaleCaf, string indirizzoPec,object valore)
		{
			using (var ws = CreaClientWsComune())
			{
				try
				{
					var dataRichiesta = new TaresDate();
					var password = new TaresServicePassword(this._configurazione.IdentificativoUtente, this._configurazione.Password, dataRichiesta);
					var idRichiesta = new TaresIdRichiesta(this._configurazione.IdentificativoUtente, TaresIdRichiesta.NomeServizio.getUtenze, dataRichiesta);

					var datiAutorizzazione = new datiAutorizzazioneType
					{
						dataRichiesta = dataRichiesta.ToDateString(),
						identificativoServizio = datiAutorizzazioneTypeIdentificativoServizio.S01,
						identificativoUtente = this._configurazione.IdentificativoUtente,
						idRichiesta = idRichiesta.ToString(),
						oraRichiesta = dataRichiesta.ToTimeString(),
						password = password.ToString()
					};

					var datiIdentificativi = new datiComuniType
					{
						//codiceFiscaleCAF = codiceFiscaleCaf,
						indirizzoPEC = indirizzoPec,
						Item1 = valore,
						Item = codiceFiscaleCaf
					};

					if( Regex.IsMatch(codiceFiscaleCaf,"[a-zA-Z]{6}[0-9]{2}[a-zA-Z]{1}[0-9]{2}[a-zA-Z]{1}[0-9]{3}[a-zA-Z]{1}" ))
						datiIdentificativi.ItemElementName = ItemChoiceType.codiceFiscaleCAF;
					else
						datiIdentificativi.ItemElementName = ItemChoiceType.partitaIVACAF;

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tares \"getUtenze\" con datiAutorizzazione: {0}\r\n e datiIdentificativi: {1}", StreamUtils.SerializeClass(datiAutorizzazione), StreamUtils.SerializeClass(datiIdentificativi));

					var utenze = ws.getUtenze( new getUtenzeType
					{
						datiAutorizzazione = datiAutorizzazione,
						datiIdentificativi = datiIdentificativi					
					});

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione del servizio tares \"getUtenze\" terminato con successo: {0}", StreamUtils.SerializeClass(utenze));

					if (utenze.@return.dettagliContribuente == null && !String.IsNullOrEmpty(utenze.@return.messaggio))
						throw new Exception(utenze.@return.messaggio);

					var utenzeDto = Mapper.Map<UtenzeDto>(utenze.@return);

					if (utenzeDto == null)
						return new DatiContribuenteDto[0];

					return utenzeDto.DettagliContribuente;
				}
				catch (Exception ex)
				{
					ws.Abort();

					_log.ErrorFormat("Errore durante l'invocazione del servizio tares \"getUtenze\":  {0}", ex.ToString());

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
	}

}
