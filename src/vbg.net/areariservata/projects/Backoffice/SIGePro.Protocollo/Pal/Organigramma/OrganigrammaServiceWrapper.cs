using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Newtonsoft.Json;

namespace Init.SIGePro.Protocollo.Pal.Organigramma
{
    public class OrganigrammaServiceWrapper : BaseProtocolloServiceWrapper
    {
        const string _modelKey = "it.pal.cw2.model.base.bor.Organigramma";
        string _token;

        public OrganigrammaServiceWrapper(string token, string baseUrlWs, ProtocolloLogs logs) : base(logs, baseUrlWs)
        {
            this._token = token;
        }

        public Result GetOrganigramma(string id)
        {
            try
            {
                var uri = new Uri(new Uri(_baseUrlWs), _modelKey);

                using (var client = GetHttpClient(_token))
                {
                    client.QueryString.Add("skipPagination", "true");
                    _logs.Info("CHIAMATA A GETORGANIGRAMMA");
                    var jsonResponse = client.DownloadString(uri);
                    _logs.Info($"RISPOSTA DA GETORGANIGRAMMA: {jsonResponse}");
                    var response = JsonConvert.DeserializeObject<RootObjectOrganigramma>(jsonResponse);

                    var result = response.results.Where(x => x.id == id);

                    if (result.Count() == 0)
                    {
                        this._logs.Info($"ORGANIGRAMMA CON ID: {id} NON TROVATO");
                        return null;
                    }

                    if (result.Count() > 1)
                    {
                        this._logs.Info($"ORGANIGRAMMA CON ID: {id} TROVATO PIU' VOLTE");
                        return null;
                    }

                    return result.First();
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE AVVENUTO DURANTE LA LETTURA DELL'ORGANIGRAMMA, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
