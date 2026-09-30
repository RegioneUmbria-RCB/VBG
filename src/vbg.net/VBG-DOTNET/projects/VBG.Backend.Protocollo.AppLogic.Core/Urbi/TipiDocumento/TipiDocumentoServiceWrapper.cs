using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.TipiDocumento
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
