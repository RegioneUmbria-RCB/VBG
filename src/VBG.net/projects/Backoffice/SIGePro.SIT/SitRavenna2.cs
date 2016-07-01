using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.Ravenna2;
using Init.SIGePro.Sit.ValidazioneFormale;
using log4net;
using PersonalLib2.Data;

namespace Init.SIGePro.Sit
{
	public class SIT_RAVENNA2 : SitBaseV2
	{
		private static class Constants
		{
			public const string ErroreLocalizzazioneNonUnivoca = "I dati immessi sono corretti ma non permettono di identificare univocamente una localizzazione. Immettere un numero maggiore di filtri e riprovare.";
		}


		Ravenna2DbClient _dbClient;
		string _urlZoomDaCivico;
		string _urlZoomDaMappale;
		ILog _log = LogManager.GetLogger(typeof(SIT_RAVENNA2));


		public SIT_RAVENNA2()
			: base(new ValidazioneFormaleTramiteCodiceCivicoService())
		{
		}


		public override void SetupVerticalizzazione()
		{
			var v = CaricaParametriVerticalizzazione();

			if (!v.Attiva)
			{
				throw new Exception(String.Format("La verticalizzazione " + VerticalizzazioneSitRavenna2.Constants.NomeVerticalizzazione + " non è attiva per l'alias {0} e il software {1}", this.Alias, this.Software));
			}

			var db = new DataBase(v.ConnectionString, ProviderType.OracleClient);

			this._dbClient = new Ravenna2DbClient(db, v.PrefissoTabelle);
			this._urlZoomDaCivico = v.UrlCartografiaDaCivico;
			this._urlZoomDaMappale = v.UrlCartografiaDaMappale;
		}

		protected virtual VerticalizzazioneSitRavenna2 CaricaParametriVerticalizzazione()
		{
			return new VerticalizzazioneSitRavenna2(this.Alias, this.Software);
		}

		public override Data.RetSit ElencoCivici()
		{
			var codVia = this.DataSit.CodVia;
			//var codVia = this.DataSit.CodVia;

			if (String.IsNullOrEmpty(codVia))
			{
				throw new Exception("Dati non sufficienti per enumerare i civici. Selezionare almeno una via");
			}

			var resultSet = this._dbClient.GetListaCivici(codVia);

			return resultSet.ToRetSit();
		}

		public override Data.RetSit ElencoEsponenti()
		{
			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;

			if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
			{
				throw new Exception("Dati non sufficienti per enumerare gli esponenti. Selezionare almeno una via e un civico");
			}

			var resultSet = this._dbClient.GetListaEsponenti(codVia, civico);

			return resultSet.ToRetSit();
		}

		public override Data.RetSit ElencoSezioni()
		{

			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;
			var esponente = this.DataSit.Esponente;

			var resultSet = this._dbClient.GetListaSezioni(codVia, civico, esponente);

			return resultSet.ToRetSit();
		}

		public override Data.RetSit ElencoFogli()
		{
			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;
			var esponente = this.DataSit.Esponente;
			var sezione = this.DataSit.Sezione;

			if (String.IsNullOrEmpty(sezione))
			{
				throw new Exception("Dati non sufficienti per enumerare i fogli. Selezionare almeno una sezione");
			}

			var resultSet = this._dbClient.GetListaFogli(codVia, civico, esponente, sezione);

			return resultSet.ToRetSit();
		}

		public override Data.RetSit ElencoParticelle()
		{
			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;
			var esponente = this.DataSit.Esponente;
			var sezione = this.DataSit.Sezione;
			var foglio = this.DataSit.Foglio;

			if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio))
			{
				throw new Exception("Dati non sufficienti per enumerare le particelle. Selezionare almeno una sezione e un foglio");
			}

			var resultSet = this._dbClient.GetListaParticelle(codVia, civico, esponente, sezione, foglio);

			return resultSet.ToRetSit();
		}


		public override RetSit CivicoValidazione()
		{
			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;

			var result = this._dbClient.GetCivico(codVia, civico);

			if (result.PiuDiUnElementoTrovato)
			{
				_log.DebugFormat(String.Format("Validazione del civico fallita: {0}", Constants.ErroreLocalizzazioneNonUnivoca));
			}

			if (!result.ElementoTrovato)
			{
				return RetSit.Errore(MessageCode.CivicoValidazione, "Il civico inserito non è valido oppure i dati immessi non sono sufficienti", false);
			}

			this.DataSit.ExtendWith(result);

			return new RetSit(true);
		}

		public override RetSit EsponenteValidazione()
		{
			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;
			var esponente = this.DataSit.Esponente;

			var result = this._dbClient.GetEsponente(codVia, civico, esponente);

			if (result.PiuDiUnElementoTrovato)
			{
				_log.DebugFormat(String.Format("Validazione dell'esponente fallita: {0}", Constants.ErroreLocalizzazioneNonUnivoca));
			}

			if (!result.ElementoTrovato)
			{
				return RetSit.Errore(MessageCode.EsponenteValidazione, "L'esponente inserito non è valido", false);
			}

			this.DataSit.ExtendWith(result);

			return new RetSit(true);
		}

		public override RetSit SezioneValidazione()
		{
			var sezione = this.DataSit.Sezione;

			var result = this._dbClient.GetSezione(sezione);

			if (result.PiuDiUnElementoTrovato)
			{
				_log.DebugFormat(String.Format("Validazione della sezione fallita: {0}", Constants.ErroreLocalizzazioneNonUnivoca));
			}

			if (!result.ElementoTrovato)
			{
				return RetSit.Errore(MessageCode.SezioneValidazione, "La sezione inserita non è valida", false);
			}

			this.DataSit.ExtendWith(result);

			return new RetSit(true);
		}

		public override RetSit FoglioValidazione()
		{
			var sezione = this.DataSit.Sezione;
			var foglio = this.DataSit.Foglio;

			var result = this._dbClient.GetFoglio(sezione, foglio);

			if (result.PiuDiUnElementoTrovato)
			{
				_log.DebugFormat(String.Format("Validazione del foglio fallita: {0}", Constants.ErroreLocalizzazioneNonUnivoca));
			}

			this.DataSit.ExtendWith(result);

			return new RetSit(true);
		}

		public override RetSit ParticellaValidazione()
		{
			var sezione = this.DataSit.Sezione;
			var foglio = this.DataSit.Foglio;
			var particella = this.DataSit.Particella;

			var codVia = this.DataSit.CodVia;
			var civico = this.DataSit.Civico;
			var esponente = this.DataSit.Esponente;

			Ravenna2Result result = new Ravenna2EmptyResult();

			if (!String.IsNullOrEmpty(codVia) && !String.IsNullOrEmpty(civico))
			{
				result = this._dbClient.GetParticella(codVia, civico, esponente, sezione, foglio, particella);
			}
			else
			{
				result = this._dbClient.GetParticella(sezione, foglio, particella);
			}

			if (result.PiuDiUnElementoTrovato)
			{
				_log.DebugFormat(String.Format("Validazione della particella fallita: {0}", Constants.ErroreLocalizzazioneNonUnivoca));
			}

			if (!result.ElementoTrovato)
			{
				return RetSit.Errore(MessageCode.ParticellaValidazione, "La particella inserita non è valida", false);
			}

			this.DataSit.ExtendWith(result);

			return new RetSit(true);
		}

		public override RetSit ElencoFrazioni()
		{
			return RetSit.Errore(MessageCode.ElencoFabbricati, "Il SIT in uso non supporta la ricerca per fabbricati", false);
		}

		public override RetSit FrazioneValidazione()
		{
			return new RetSit(true);
		}

		public override RetSit ElencoSub()
		{
			return RetSit.Errore(MessageCode.ElencoFabbricati, "Il SIT in uso non supporta la ricerca per subalterni", false);
		}

		public override RetSit SubValidazione()
		{
			return new RetSit(true);
		}


		public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
		{
			var l = new List<BaseDto<SitFeatures.TipoVisualizzazione, string>>();

			if (!String.IsNullOrEmpty(this._urlZoomDaCivico))
			{
				l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaCivico));
			}

			if (!String.IsNullOrEmpty(this._urlZoomDaMappale))
			{
				l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlZoomDaMappale));
			}

			return l.ToArray();
		}

		public override string[] GetListaCampiGestiti()
		{
			return new[]{
				SitIntegrationService.NomiCampiSit.CodiceCivico,		
				//SitIntegrationService.NomiCampiSit.CodiceVia,
				SitIntegrationService.NomiCampiSit.Civico,
				SitIntegrationService.NomiCampiSit.Esponente,
				SitIntegrationService.NomiCampiSit.Sezione,
				SitIntegrationService.NomiCampiSit.Foglio,
				SitIntegrationService.NomiCampiSit.Particella,
				// SitIntegrationService.NomiCampiSit.Sub
			};
		}
	}
}
