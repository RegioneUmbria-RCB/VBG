using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.TipiMezzo
{
    public class TipiMezzoServiceWrapper : BaseServiceWrapper
    {
        public TipiMezzoServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url)
            : base(logs, serializer, username, password, url)
        {

        }

        public xapirestTypeTipiMezzo GetTipiMezzo()
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    client.QueryString.Add(_nomeParametroMetodo, "getElencoTipiMezzo");
                    _logs.InfoFormat("RICHIESTA DEI TIPI MEZZO, REQUEST: {0}", Utility.NameValueCollectionToString(client.QueryString));
                    var res = client.DownloadString(_url);
                    var mezzi = (xapirestTypeTipiMezzo)_serializer.Deserialize(res, typeof(xapirestTypeTipiMezzo));
                    _logs.Info("RICHIESTA DEI TIPI MEZZO AVVENUTA CORRETTAMENTE");
                    return mezzi;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI RELATIVE AI TIPI MEZZO, ERRORE: {0}", ex.Message), ex);
            }
        }

    }
}
