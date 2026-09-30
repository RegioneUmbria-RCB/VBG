using RestSharp;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.SIGePro.Sit.Ravenna2
{
    public class Ravenna3RestClient
    {
        private readonly RestClient _client;
        private readonly string _codViarioNonDefinito = "0";
        private readonly string _urlWsVbgRA147;
        private readonly string _urlWsVbgRA012;

        public Ravenna3RestClient(string urlWsVbgRA147, string urlWsVbgRA012, string urlWsVbgParticelleCatastali)
        {
            if (string.IsNullOrWhiteSpace(urlWsVbgRA147))
            {
                throw new ArgumentException($"'{nameof(urlWsVbgRA147)}' non può essere Null o uno spazio vuoto.", nameof(urlWsVbgRA147));
            }

            if (string.IsNullOrWhiteSpace(urlWsVbgRA012))
            {
                throw new ArgumentException($"'{nameof(urlWsVbgRA012)}' non può essere Null o uno spazio vuoto.", nameof(urlWsVbgRA012));
            }

            if (string.IsNullOrWhiteSpace(urlWsVbgParticelleCatastali))
            {
                throw new ArgumentException($"'{nameof(urlWsVbgParticelleCatastali)}' non può essere Null o uno spazio vuoto.", nameof(urlWsVbgParticelleCatastali));
            }

            _client = new RestClient();
            this._urlWsVbgRA147 = urlWsVbgRA147;
            this._urlWsVbgRA012 = urlWsVbgRA012;
        }

        private async Task<string> GetData(string serviceUrl, Dictionary<string, string> parameters, bool isNotNull, bool distinct, string outField, bool orderData, string functionName)
        {
            string queryString = BuildQueryString(parameters, isNotNull);
            string fields = EncodeFields(outField);

            var request = new RestRequest(serviceUrl, Method.GET);
            request.AddParameter("where", queryString, ParameterType.QueryStringWithoutEncode);
            request.AddParameter("geometryType", "esriGeometryEnvelope");
            request.AddParameter("spatialRel", "esriSpatialRelIntersects");
            request.AddParameter("featureEncoding", "esriDefault");
            request.AddParameter("f", "pjson");
            request.AddParameter("outFields", fields);
            request.AddParameter("returnDistinctValues", distinct.ToString().ToLower());
            if (orderData) { 
                if("GetEsponente".Equals(functionName))
                {
                    request.AddParameter("orderByFields", "PARTE desc");
                }
                else
                {
                    request.AddParameter("orderByFields", fields);
                }
            
            }
            //var uri = _client.BuildUri(request);
            //Console.WriteLine(uri);
            var response = await _client.ExecuteAsync(request);

           
            if (!response.IsSuccessful)
                throw new Exception("Errore nella richiesta API: " + response.ErrorMessage);

            return response.Content;
        }


        private  string CustomUrlEncode(string input)
        {
            string temp = input.Replace(" AND ", "+AND+");
            return Uri.EscapeDataString(temp).Replace("%2B", "+");
        }

        private string BuildQueryString(Dictionary<string, string> parameters, bool isNotNull)
        {
            string query = "";
            foreach (var param in parameters)
            {
                if (isNotNull)
                {
                    query += param.Key + " IS NOT NULL ";
                    break;
                }
                if (!string.IsNullOrWhiteSpace(param.Value) && param.Value.Trim() != "''")
                {
                    query += param.Key + "=" + param.Value + " AND ";
                }
            }
            if (query.EndsWith(" AND "))
                query = query.Remove(query.Length - 4).Trim();

            return CustomUrlEncode(query);
        }

        private string EncodeFields(string fields)
        {
            return fields;
        }

        public string GetListaCivici(string codVia)
        {
            if (string.IsNullOrEmpty(codVia))
            {
                throw new ArgumentException($"'{nameof(codVia)}' non può essere null o vuoto.", nameof(codVia));
            }

            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia } }, false, true, "NUMERO", true, "GetListaCivici").Result;
        }

        public string GetListaVie()
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "toponimo", "is not null" } }, true, true, "TOPONIMO,INDIRIZZO,COD_VIA", false, "GetListaVie").Result;
        }

        public Ravenna3ResultSet GetListaEsponenti(string codVia, string civico)
        {
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia }, { "numero", civico } }, false, true, "PARTE", true, "GetListaEsponenti").Result, "PARTE", "STRING");
        }
        public Ravenna3ResultSet GetListaSezioni()
        {
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string>(), false, true, "SEZIONE", true, "GetListaSezioni").Result, "SEZIONE", "STRING");
        }
        public Ravenna3ResultSet GetListaSezioni(string codVia, string civico, string esponente)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente))
            {
                return this.GetListaSezioni();
            }
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia }, { "numero", civico }, { "parte", "'" + esponente + "'" } }, false, true, "SEZIONE", true, "GetListaSezioni").Result, "SEZIONE", "STRING");
        }

        public Ravenna3ResultSet GetListaFogli(string sezione)
        {
            if (String.IsNullOrEmpty(sezione))
            {
                throw new ArgumentException("Sezione non può essere nulla");
            }
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "sezione", "'" + sezione + "'" } }, false, true, "FOGLIO", false, "GetListaFogli").Result, "FOGLIO", "NUMBER");
        }
        public Ravenna3ResultSet GetListaFogli(string codVia, string civico, string esponente, string sezione)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione))
            {
                return this.GetListaFogli(sezione);
            }
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia }, { "numero", civico }, { "parte", "'" + esponente + "'" }, { "sezione", "'" + sezione + "'" } }, false, true, "FOGLIO", true, "GetListaFogli").Result, "FOGLIO", "STRING");
        }
        public string GetListaParticelle(string sezione, string foglio)
        {
            if (string.IsNullOrEmpty(sezione))
            {
                throw new ArgumentException("Sezione non può essere nulla");
            }
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "sezione", "'" + sezione + "'" }, { "foglio", foglio } }, false, true, "NUMERO", true, "GetListaParticelle").Result;
        }

        public string GetListaParticelle(string codVia, string civico, string esponente, string sezione, string foglio)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione) && !String.IsNullOrEmpty(foglio))
            {
                return this.GetListaParticelle(sezione, foglio);
            }
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia }, { "numero", civico }, { "parte", "'" + esponente + "'" }, { "sezione", "'" + sezione + "'" }, { "foglio", foglio } }, false, true, "PARTICELLA", true, "GetListaParticelle").Result;
        }

        public string GetCivico(string codVia, string civico)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia }, { "numero", civico } }, false, false, "*", false, "GetCivico").Result;
        }

        public string GetEsponente(string codVia, string civico, string esponente)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia }, { "numero", civico }, { "parte", "'" + esponente + "'" } }, false, false, "*", true, "GetEsponente").Result;
        }

        public string GetSezione(string sezione)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "sezione", "'" + sezione + "'" } }, false, true, "SEZIONE", false, "GetSezione").Result;
        }

        public string GetFoglio(string sezione, string foglio)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "sezione", "'" + sezione + "'" }, { "foglio", "'" + foglio + "'" } }, false, true, "SEZIONE,FOGLIO,NUMERO", false, "GetFoglio").Result;
        }

        public string GetParticella(string sezione, string foglio, string particella)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "sezione", "'" + sezione + "'" }, { "foglio", "'" + foglio + "'" }, { "particella", "'" + particella + "'" } }, false, true, "SEZIONE,FOGLIO,NUMERO", false, "GetParticella").Result;
        }
        public string GetParticella(String codVia, String civico, String esponente, String sezione, String foglio, String particella)
        {
            if (String.IsNullOrEmpty(codVia) || codVia == "0" || String.IsNullOrEmpty(civico))
            {
                return this.GetParticella(sezione, foglio, particella);
            }
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { "cod_via", codVia }, { "numero", civico }, { "parte", "'" + esponente + "'" }, { "sezione", "'" + sezione + "'" }, { "foglio", "'" + foglio + "'" }, { "particella", particella } }, false, false, "*", false, "GetParticella").Result;
        }

        internal string GetFrazione(string codiceSezione)
        {
            return GetData(this._urlWsVbgRA147, new Dictionary<string, string> { { "cod_sez", codiceSezione } }, false, true, "DESCRIZION", false, "GetFrazione").Result;
        }
    }
}