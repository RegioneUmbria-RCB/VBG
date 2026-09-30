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

namespace Init.SIGePro.Protocollo.Urbi.TipiDocumento
{
    public class TipiDocumentoServiceWrapper : BaseServiceWrapper
    {

        public TipiDocumentoServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url)
            : base(logs, serializer, username, password, url)
        {

        }

        public xapirestTypeTipiDocumento GetTipiDocumento()
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    client.QueryString.Add(_nomeParametroMetodo, "getElencoTipiDocumento");
                    _logs.InfoFormat("RICHIESTA DEI TIPI DOCUMENTO, REQUEST {0}", Utility.NameValueCollectionToString(client.QueryString));
                    var res = client.DownloadString(_url);
                    _logs.InfoFormat("RICHIESTA DEI TIPI DOCUMENTO AVVENUTA CORRETTAMENTE");
                    var tipiDoc = (xapirestTypeTipiDocumento)_serializer.Deserialize(res, typeof(xapirestTypeTipiDocumento));
                    return tipiDoc;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI RELATIVE ALLA TIPOLOGIE DI DOCUMENTO, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
