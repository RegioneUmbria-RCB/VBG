using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses;
using Init.SIGePro.Manager.Utils;
using Newtonsoft.Json;

namespace Init.SIGePro.Sit.SilverBrowser
{
	internal class SilverBrowserClient
	{
		private static class Constants
		{
			public const string ListaVie = "ListaVie";
			public const string ListaCiviciVia = "ListaCiviciVia?CODICEVIARIO={0}";
			public const string ListaEsponenti = "ListaEsponentiCivico?CODICEVIARIO={0}&NUMEROCIVICO={1}";
			public const string VerificaCivico = "VerificaCivico?CODICEVIARIO={0}&NUMEROCIVICO={1}";
			public const string VerificaCivicoConEsponente = "VerificaCivico?CODICEVIARIO={0}&NUMEROCIVICO={1}&ESPONENTE={2}";
			public const string ListaSezioni = "ListaSezioni";
			public const string ListaFogli = "ListaFogli";
			public const string ListaParticelle = "ListaParticelle?SEZ={0}&FOGLIO={1}";
			// public const string ListaParticelle = "ListaParticelle?FOGLIO={0}";
			public const string VerificaParticella = "VerificaParticella?FOGLIO={0}&NUMERO={1}";
			public const string ListaSub = "ListaSub?SEZ={0}&FOGLIO={1}&NUMERO={2}";
			public const string VerificaSub = "VerificaSub?SEZ={0}&FOGLIO={1}&NUMERO={2}&SUB={3}";
		}

		string _baseUrl;
		ILog _log = LogManager.GetLogger(typeof(SilverBrowserClient));

		internal SilverBrowserClient(string baseUrl)
		{
			this._baseUrl = baseUrl;
		}

        public IEnumerable<Civico> ListaCivici(CodiceViario codiceViario)
		{
			var url = String.Format(Constants.ListaCiviciVia, codiceViario.ToString());

			return GetJson <IEnumerable<Civico>>(url);
		}

        public IEnumerable<Esponente> ListaEsponenti(CodiceViario codiceViario, string civico)
		{
			var url = String.Format(Constants.ListaEsponenti, codiceViario.ToString(), civico);

			return GetJson<IEnumerable<Esponente>>(url);
		}

        public RisultatoVerificaCivico VerificaCivico(CodiceViario codiceViario, string civico)
		{
			var url = String.Format(Constants.VerificaCivico, codiceViario.ToString(), civico);

			return GetJson<RisultatoVerificaCivico>(url);
		}

        public RisultatoVerificaCivico VerificaCivicoConEsponente(CodiceViario codiceViario, string civico, string esponente)
		{
			var url = String.Format(Constants.VerificaCivicoConEsponente, codiceViario.ToString(), civico, esponente);

			return GetJson<RisultatoVerificaCivico>(url);
		}

		public IEnumerable<Sezione> ListaSezioni()
		{
			return GetJson<IEnumerable<Sezione>>(Constants.ListaSezioni);
		}

		public IEnumerable<Foglio> ListaFogli()
		{
			return GetJson<IEnumerable<Foglio>>(Constants.ListaFogli);
		}

		public IEnumerable<Particella> ListaParticelle(string sezione, string foglio)
		{
			var url = String.Format(Constants.ListaParticelle, sezione, foglio);

			return GetJson<IEnumerable<Particella>>(url);
		}

		public RisultatoVerificaParticella VerificaParticella(string foglio, string particella)
		{
			var url = String.Format(Constants.VerificaParticella, foglio, particella);

			return GetJson<RisultatoVerificaParticella>(url);
		}

		public IEnumerable<Sub> ListaSub(string sezione, string foglio, string particella)
		{
			var url = String.Format(Constants.ListaSub, sezione, foglio, particella);

			return GetJson<Sub[]>(url);
		}

		public RisultatoVerificaSub VerificaSub(string sezione, string foglio, string particella, string sub)
		{
			var url = String.Format(Constants.VerificaSub, sezione, foglio, particella, sub);

			return GetJson<RisultatoVerificaSub>(url);
		}


		private T GetJson<T>(string urlPart)
		{
			var url = this._baseUrl + urlPart;

			this._log.DebugFormat("Richiesta all'url {0}", url);

			var result = new RestClient(url, HttpVerb.GET).MakeRequest();

			if (this._log.IsDebugEnabled)
			{
				this._log.DebugFormat("Risultato della chiamata: {0}", result);
			}
			
			return JsonConvert.DeserializeObject<T>(result);
		}
	}
}
