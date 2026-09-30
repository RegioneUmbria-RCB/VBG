using System;
using System.ServiceModel;
using TriesteModificaResponsabili.WebServiceClient;

namespace TriesteModificaResponsabili
{
    public class ModificaResponsabiliClient
    {
        private readonly string _endpointUrl;
        private readonly string _token;

        public ModificaResponsabiliClient(string endpointUrl, string token)
        {
            _endpointUrl = endpointUrl;
            _token = token;
        }

        public EsitoOperazioneType IstanzeRuoli(int codiceIstanza, int codiceRuolo)
        {
            return Execute(ws =>
            {
                var ret = ws.IstanzeRuoli(new IstanzeRuoliRequest
                {
                    codiceIstanza = codiceIstanza,
                    codiceRuolo = codiceRuolo,
                    token = this._token
                });

                return ret.EsitoOperazioneType;
            });
        }

        public EsitoOperazioneType IstanzeResponsabili(int codiceIstanza, int codiceResponsabile)
        {
            return Execute(ws =>
            {
                var ret = ws.IstanzeResponsabili(new IstanzeResponsabiliRequest
                {
                    codiceIstanza = codiceIstanza,
                    codiceResponsabile = codiceResponsabile,
                    token = this._token
                });

                return ret.EsitoOperazioneType;
            });
        }

        private T Execute<T>(Func<IstanzeClient, T> callback)
        {
            var binding = new BasicHttpBinding();
            var endpoint = new EndpointAddress(_endpointUrl);

            using (var ws = new IstanzeClient(binding, endpoint))
            {
                try
                {
                    return callback(ws);
                }
                catch (Exception ex)
                {
                    ws.Abort();

                    throw;
                }
            }
        }
    }
}
