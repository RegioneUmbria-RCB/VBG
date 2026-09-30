using log4net;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace VBG.Backend.SIT.AppLogic.Ravenna2
{
    public class Ravenna3RestClient
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SitRavenna2));
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

        public string GetListaCivici(string codVia)
        {
            if (string.IsNullOrEmpty(codVia))
            {
                throw new ArgumentException($"'{nameof(codVia)}' non può essere null o vuoto.", nameof(codVia));
            }

            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia } }, false, true, Constants.TabellaRA012.CampoCivico, true, "GetListaCivici").Result;
        }

        public string GetListaVie()
        {
            this._log.Debug("GetListaVie: Inizio ricerca");
            var campiOut = $"{Constants.TabellaRA012.CampoDug},{Constants.TabellaRA012.CampoNomeVia},{Constants.TabellaRA012.CampoCodiceVia}";
            this._log.Debug($"GetListaVie: Campi out {campiOut}");
            var filtri = new Dictionary<string, string> { { Constants.TabellaRA012.CampoDug, "is not null" } };
            this._log.Debug($"GetListaVie: Filtro {Constants.TabellaRA012.CampoDug} is not null");
            var retVal = GetData(this._urlWsVbgRA012, filtri, true, true, campiOut, false, "GetListaVie").Result;
            this._log.Debug("GetListaVie: Fine ricerca");
            return retVal;
        }

        public Ravenna3ResultSet GetListaEsponenti(string codVia, string civico)
        {
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia }, { Constants.TabellaRA012.CampoCivico, civico } }, false, true, Constants.TabellaRA012.CampoEsponente, true, "GetListaEsponenti").Result, Constants.TabellaRA012.CampoEsponente, "STRING");
        }

        public Ravenna3ResultSet GetListaSezioni()
        {
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, [], false, true, Constants.TabellaRA012.CampoSezione, true, "GetListaSezioni").Result, Constants.TabellaRA012.CampoSezione, "STRING");
        }

        public Ravenna3ResultSet GetListaSezioni(string codVia, string civico, string esponente)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente))
            {
                return this.GetListaSezioni();
            }
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia }, { Constants.TabellaRA012.CampoCivico, civico }, { Constants.TabellaRA012.CampoEsponente, "'" + esponente + "'" } }, false, true, Constants.TabellaRA012.CampoSezione, true, "GetListaSezioni").Result, Constants.TabellaRA012.CampoSezione, "STRING");
        }

        public Ravenna3ResultSet GetListaFogli(string sezione)
        {
            if (String.IsNullOrEmpty(sezione))
            {
                throw new ArgumentException("Sezione non può essere nulla");
            }
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" } }, false, true, Constants.TabellaRA012.CampoFoglio, false, "GetListaFogli").Result, Constants.TabellaRA012.CampoFoglio, "NUMBER");
        }

        public Ravenna3ResultSet GetListaFogli(string codVia, string civico, string esponente, string sezione)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione))
            {
                return this.GetListaFogli(sezione);
            }
            return new Ravenna3ResultSet(GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia }, { Constants.TabellaRA012.CampoCivico, civico }, { Constants.TabellaRA012.CampoEsponente, "'" + esponente + "'" }, { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" } }, false, true, Constants.TabellaRA012.CampoFoglio, true, "GetListaFogli").Result, Constants.TabellaRA012.CampoFoglio, "STRING");
        }

        public string GetListaParticelle(string sezione, string foglio)
        {
            if (string.IsNullOrEmpty(sezione))
            {
                throw new ArgumentException("Sezione non può essere nulla");
            }
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" }, { Constants.TabellaRA012.CampoFoglio, foglio } }, false, true, Constants.TabellaRA012.CampoCivico, true, "GetListaParticelle").Result;
        }

        public string GetListaParticelle(string codVia, string civico, string esponente, string sezione, string foglio)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione) && !String.IsNullOrEmpty(foglio))
            {
                return this.GetListaParticelle(sezione, foglio);
            }
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia }, { Constants.TabellaRA012.CampoCivico, civico }, { Constants.TabellaRA012.CampoEsponente, "'" + esponente + "'" }, { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" }, { Constants.TabellaRA012.CampoFoglio, foglio } }, false, true, Constants.TabellaRA012.CampoParticella, true, "GetListaParticelle").Result;
        }

        public string GetCivico(string codVia, string civico)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia }, { Constants.TabellaRA012.CampoCivico, civico } }, false, false, "*", false, "GetCivico").Result;
        }

        public string GetEsponente(string codVia, string civico, string esponente)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia }, { Constants.TabellaRA012.CampoCivico, civico }, { Constants.TabellaRA012.CampoEsponente, "'" + esponente + "'" } }, false, false, "*", true, "GetEsponente").Result;
        }

        public string GetSezione(string sezione)
        {
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" } }, false, true, Constants.TabellaRA012.CampoSezione, false, "GetSezione").Result;
        }

        public string GetFoglio(string sezione, string foglio)
        {
            var campiOut = $"{Constants.TabellaRA012.CampoSezione},{Constants.TabellaRA012.CampoFoglio},{Constants.TabellaRA012.CampoCivico}";
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" }, { Constants.TabellaRA012.CampoFoglio, "'" + foglio + "'" } }, false, true, campiOut, false, "GetFoglio").Result;
        }

        public string GetParticella(string sezione, string foglio, string particella)
        {
            var campiOut = $"{Constants.TabellaRA012.CampoSezione},{Constants.TabellaRA012.CampoFoglio},{Constants.TabellaRA012.CampoCivico}";
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" }, { Constants.TabellaRA012.CampoFoglio, "'" + foglio + "'" }, { Constants.TabellaRA012.CampoParticella, "'" + particella + "'" } }, false, true, campiOut, false, "GetParticella").Result;
        }

        public string GetParticella(String codVia, String civico, String esponente, String sezione, String foglio, String particella)
        {
            if (String.IsNullOrEmpty(codVia) || codVia == "0" || String.IsNullOrEmpty(civico))
            {
                return this.GetParticella(sezione, foglio, particella);
            }
            return GetData(this._urlWsVbgRA012, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceVia, codVia }, { Constants.TabellaRA012.CampoCivico, civico }, { Constants.TabellaRA012.CampoEsponente, "'" + esponente + "'" }, { Constants.TabellaRA012.CampoSezione, "'" + sezione + "'" }, { Constants.TabellaRA012.CampoFoglio, "'" + foglio + "'" }, { Constants.TabellaRA012.CampoParticella, particella } }, false, false, "*", false, "GetParticella").Result;
        }

        internal string GetFrazione(string codiceSezione)
        {
            return GetData(this._urlWsVbgRA147, new Dictionary<string, string> { { Constants.TabellaRA012.CampoCodiceSezione, codiceSezione } }, false, true, Constants.TabellaRA147.CampoDescrizioneFrazione, false, "GetFrazione").Result;
        }

        private async Task<string> GetData(string serviceUrl, Dictionary<string, string> parameters, bool isNotNull, bool distinct, string outField, bool orderData, string functionName)
        {
            string queryString = BuildQueryString(parameters, isNotNull);
            string fields = EncodeFields(outField);

            var request = new RestRequest(serviceUrl, Method.Get);
            request.AddParameter("where", queryString, false);
            request.AddParameter("geometryType", "esriGeometryEnvelope");
            request.AddParameter("spatialRel", "esriSpatialRelIntersects");
            request.AddParameter("featureEncoding", "esriDefault");
            request.AddParameter("f", "pjson");
            request.AddParameter("outFields", fields);
            request.AddParameter("returnDistinctValues", distinct.ToString().ToLower());
            if (orderData)
            {
                if ("GetEsponente".Equals(functionName))
                {
                    request.AddParameter("orderByFields", "PARTE desc");
                }
                else
                {
                    request.AddParameter("orderByFields", fields);
                }

            }
            var response = await _client.ExecuteAsync(request);


            if (!response.IsSuccessful)
                throw new Exception("Errore nella richiesta API: " + response.ErrorMessage);

            return response.Content;
        }

        private string CustomUrlEncode(string input)
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

    }
}