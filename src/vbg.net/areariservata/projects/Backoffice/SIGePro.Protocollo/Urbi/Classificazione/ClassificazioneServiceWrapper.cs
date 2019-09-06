using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Urbi.Classificazione;
using Init.SIGePro.Protocollo.Urbi.Protocollazione;
using Init.SIGePro.Protocollo.Urbi.TipiDocumento;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
namespace Init.SIGePro.Protocollo.Urbi.Classificazione
{
    public class ClassificazioneServiceWrapper : BaseServiceWrapper
    {
        public ClassificazioneServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url)
            : base(logs, serializer, username, password, url)
        {

        }

        public xapirestTypeTitolario GetTitolario(string codiceAoo)
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    client.QueryString.Add(_nomeParametroMetodo, "getElencoTitolario");
                    client.QueryString.Add("PRCORE03_99991008_IDAOO", codiceAoo);

                    _logs.InfoFormat("RICHIESTA DEL TITOLARIO, REQUEST: {0}", Utility.NameValueCollectionToString(client.QueryString));
                    var res = client.DownloadString(_url);
                    _logs.InfoFormat("RICHIESTA DEL TITOLARIO AVVENUTA CORRETTAMENTE");
                    var titolario = _serializer.Deserialize<xapirestTypeTitolario>(res);
                    return titolario;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI RELATIVE ALLA TIPOLOGIE DI DOCUMENTO, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
