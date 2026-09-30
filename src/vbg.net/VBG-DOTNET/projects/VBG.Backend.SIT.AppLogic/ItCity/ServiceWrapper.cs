using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.Json;
using VBG.Backend.SIT.AppLogic.Utils;

namespace VBG.Backend.SIT.AppLogic.ItCity
{
    public class ServiceWrapper<T>
    {
        private string _url;
        private readonly string _username;
        private readonly string _password;
        private string _credentials;


        private string Credentials
        {
            get
            {
                if (String.IsNullOrEmpty(this._credentials))
                    this._credentials = Convert.ToBase64String(Encoding.UTF8.GetBytes(this._username + ":" + this._password));

                return this._credentials;
            }
        }

        public ServiceWrapper(string url, string username, string password)
        {
            this._url = url;
            this._username = username;
            this._password = password;
        }

        public ResponseCivici GetCivici(string codVia)
        {
            try
            {
                if (!this._url.EndsWith("/"))
                    this._url += "/";

                var uribase = new Uri(new Uri(this._url), codVia);

                var client = new RestClient
                {
                    EndPoint = uribase.ToString(),
                    Method = HttpVerb.GET,
                    ContentType = "application/json",
                    Headers = new WebHeaderCollection()
                };

                client.Headers.Add(HttpRequestHeader.Authorization, "Basic " + this.Credentials);

                var jsonResponse = client.MakeRequest();

                var listacivici = JsonSerializer.Deserialize<List<CiviciJSON>>(jsonResponse);

                var response = new ResponseCivici();
                response.Esito = true;
                response.MessaggioErrore = "";
                response.Dati = listacivici;

                if (!response.Dati.Any())
                {
                    response.Esito = false;
                    response.MessaggioErrore = "Dati non trovati";
                }

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA COMUNICAZIONE CON IL SISTEMA SIT, {ex.Message}", ex);
            }
        }

        public ResponseCivici VerificaCivico(string codVia, string civico)
        {
            var respCivici = this.GetCivici(codVia);

            if (!respCivici.Esito) { return respCivici; }

            var trovato = respCivici.Dati.Where(x => x.Numero == civico).Any();

            if (!trovato)
            {
                respCivici.Esito = false;
                respCivici.MessaggioErrore = $"Il civico {civico} non è stato trovato";
            }
            else
            {
                respCivici.Esito = true;
                respCivici.MessaggioErrore = "";
            }

            return respCivici;
        }

        public ResponseCivici GetEsponenti(string codVia, string civico)
        {
            try
            {
                if (!this._url.EndsWith("/"))
                    this._url += "/";

                var uribase = new Uri(new Uri(this._url), codVia);

                var client = new RestClient
                {
                    EndPoint = uribase.ToString(),
                    Method = HttpVerb.GET,
                    ContentType = "application/json",
                    Headers = new WebHeaderCollection()
                };

                client.Headers.Add(HttpRequestHeader.Authorization, "Basic " + this.Credentials);

                var jsonResponse = client.MakeRequest();

                var listacivici = JsonSerializer.Deserialize<List<CiviciJSON>>(jsonResponse);

                var response = new ResponseCivici();
                response.Esito = true;
                response.MessaggioErrore = "";
                response.Dati = listacivici;


                if (response.Dati.Count() == 0)
                {
                    response.Esito = false;
                    response.MessaggioErrore = "Dati non trovati";
                }

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA COMUNICAZIONE CON IL SISTEMA SIT, {ex.Message}", ex);
            }
        }

        public ResponseCivici VerificaEsponente(string codVia, string civico, string esponente)
        {
            var respEsponenti = this.GetEsponenti(codVia, civico);

            if (!respEsponenti.Esito) { return respEsponenti; }

            var trovato = respEsponenti.Dati.Where(x => !String.IsNullOrEmpty(x.Sub) && x.Sub.ToLower() == esponente.ToLower()).Any();

            if (!trovato)
            {
                respEsponenti.Esito = false;
                respEsponenti.MessaggioErrore = $"L'esponente {esponente} non è stato trovato";
            }
            else
            {
                respEsponenti.Esito = true;
                respEsponenti.MessaggioErrore = "";
            }

            return respEsponenti;
        }
    }
}
