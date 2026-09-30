using log4net;
using System;
using System.Collections.Generic;
using System.Text.Json;
using VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses;
using VBG.Backend.SIT.AppLogic.Utils;

namespace VBG.Backend.SIT.AppLogic.SilverBrowser
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

        private readonly string _baseUrl;
        private readonly ILog _log = LogManager.GetLogger(typeof(SilverBrowserClient));

        internal SilverBrowserClient(string baseUrl)
        {
            this._baseUrl = baseUrl;
        }

        public IEnumerable<Via> ListaVie()
        {
            return this.GetJson<IEnumerable<Via>>(Constants.ListaVie);
        }

        public IEnumerable<Civico> ListaCivici(CodiceViario codiceViario)
        {
            var url = String.Format(Constants.ListaCiviciVia, codiceViario.ToString());

            return this.GetJson<IEnumerable<Civico>>(url);
        }

        public IEnumerable<Esponente> ListaEsponenti(CodiceViario codiceViario, string civico)
        {
            var url = String.Format(Constants.ListaEsponenti, codiceViario.ToString(), civico);

            return this.GetJson<IEnumerable<Esponente>>(url);
        }

        public RisultatoVerificaCivico VerificaCivico(CodiceViario codiceViario, string civico)
        {
            var url = String.Format(Constants.VerificaCivico, codiceViario.ToString(), civico);

            return this.GetJson<RisultatoVerificaCivico>(url);
        }

        public RisultatoVerificaCivico VerificaCivicoConEsponente(CodiceViario codiceViario, string civico, string esponente)
        {
            var url = String.Format(Constants.VerificaCivicoConEsponente, codiceViario.ToString(), civico, esponente);

            return this.GetJson<RisultatoVerificaCivico>(url);
        }

        public IEnumerable<Sezione> ListaSezioni()
        {
            return this.GetJson<IEnumerable<Sezione>>(Constants.ListaSezioni);
        }

        public IEnumerable<Foglio> ListaFogli()
        {
            return this.GetJson<IEnumerable<Foglio>>(Constants.ListaFogli);
        }

        public IEnumerable<Particella> ListaParticelle(string sezione, string foglio)
        {
            var url = String.Format(Constants.ListaParticelle, sezione, foglio);

            return this.GetJson<IEnumerable<Particella>>(url);
        }

        public RisultatoVerificaParticella VerificaParticella(string foglio, string particella)
        {
            var url = String.Format(Constants.VerificaParticella, foglio, particella);

            return this.GetJson<RisultatoVerificaParticella>(url);
        }

        public IEnumerable<Sub> ListaSub(string sezione, string foglio, string particella)
        {
            var url = String.Format(Constants.ListaSub, sezione, foglio, particella);

            return this.GetJson<Sub[]>(url);
        }

        public RisultatoVerificaSub VerificaSub(string sezione, string foglio, string particella, string sub)
        {
            var url = String.Format(Constants.VerificaSub, sezione, foglio, particella, sub);

            return this.GetJson<RisultatoVerificaSub>(url);
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

            return JsonSerializer.Deserialize<T>(result);
            //return JsonConvert.DeserializeObject<T>(result);
        }
    }
}
