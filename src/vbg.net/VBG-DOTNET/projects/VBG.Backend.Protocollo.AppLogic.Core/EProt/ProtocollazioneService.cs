using VBG.Backend.Protocollo.AppLogic.Core.EProt.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Core.EProt.Titolario;
using System.Collections.Specialized;
using System.Net;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.EProt
{
    public class ProtocollazioneService
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly string _username;
        private readonly string _password;

        public ProtocollazioneService(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._username = username;
            this._password = password;
        }

        private WebClient GetHttpClient()
        {
            var client = new WebClient();

            var credentials = this._username + ":" + this._password;
            string authInfo = Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
            client.Headers.Add(HttpRequestHeader.Authorization, "Basic " + authInfo);
            client.Headers.Add(HttpRequestHeader.ContentType, "application/x-www-form-urlencoded");
            client.Headers.Remove(HttpRequestHeader.Expect);
            client.Headers.Remove(HttpRequestHeader.KeepAlive);
            client.Credentials = new NetworkCredential(this._username, this._password);

            return client;
        }

        public TitolarioListType GetTitolario(string url)
        {
            try
            {
                using (var client = this.GetHttpClient())
                {
                    this._logs.InfoFormat("RICHIESTA DEL TITOLARIO");
                    var data = client.UploadValues(url, "POST", new NameValueCollection());
                    string res = Encoding.UTF8.GetString(data);

                    this._logs.InfoFormat("RICHIESTA DEL TITOLARIO AVVENUTA CORRETTAMENTE");
                    var titolario = (TitolarioListType)this._serializer.Deserialize(res, typeof(TitolarioListType));
                    return titolario;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI RELATIVE AL TITOLARIO, ERRORE: {0}", ex.Message), ex);
            }
        }


        public TipiDocumentoListType GetTipiDocumento(string url)
        {
            try
            {
                using (var client = this.GetHttpClient())
                {
                    this._logs.InfoFormat("RICHIESTA DEI TIPI DOCUMENTO");
                    var data = client.UploadValues(url, "POST", new NameValueCollection());
                    string res = Encoding.UTF8.GetString(data);

                    this._logs.InfoFormat("RICHIESTA DEI TIPI DOCUMENTO AVVENUTA CORRETTAMENTE");
                    var tipiDoc = (TipiDocumentoListType)this._serializer.Deserialize(res, typeof(TipiDocumentoListType));
                    return tipiDoc;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI RELATIVE ALLA TIPOLOGIE DI DOCUMENTO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public string[] Protocolla(IEnumerable<KeyValuePair<string, string>> metadati, string url)
        {
            try
            {
                using (var client = this.GetHttpClient())
                {
                    var nvc = new NameValueCollection();
                    metadati.ToList().ForEach(x => nvc.Add(x.Key, x.Value));
                    this._logs.InfoFormat("INVIO RICHIESTA A PROTOCOLLAZIONE");

                    var data = client.UploadValues(url, "POST", nvc);
                    string res = Encoding.UTF8.GetString(data);
                    this._logs.InfoFormat("INVIO RICHIESTA A PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    return res.Split(';');
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }
    }
}
