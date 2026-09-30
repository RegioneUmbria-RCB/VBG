using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Classificazione
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
