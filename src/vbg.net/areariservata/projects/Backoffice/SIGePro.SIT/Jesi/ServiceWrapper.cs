using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Sit.Data;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public class ServiceWrapper<T>
    {
        string _url;

        public ServiceWrapper(string url)
        {
            this._url = url;
        }

        public ResponseJSON<T> GetDati(RequestJSON jsonPostData)
        {
            try
            {
                var jsonRequest = JsonConvert.SerializeObject(jsonPostData);

                var client = new RestClient
                {
                    EndPoint = _url,
                    Method = HttpVerb.POST,
                    PostData = jsonRequest,
                    ContentType = "application/json"
                };

                var jsonResponse = client.MakeRequest();

                var response = JsonConvert.DeserializeObject<ResponseJSON<T>>(jsonResponse);

                response.Esito = Convert.ToBoolean(response.Dettaglio[0]);
                response.MessaggioErrore = response.Dettaglio[1].ToString();

                var dati = JsonConvert.DeserializeObject<IEnumerable<T>>(response.Dettaglio[2].ToString());
                response.Dati = dati;

                if (dati.Count() == 0)
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

        

    }
}
