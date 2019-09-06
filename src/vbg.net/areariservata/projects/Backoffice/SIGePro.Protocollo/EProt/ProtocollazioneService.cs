using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.EProt.TipiDocumento;
using Init.SIGePro.Protocollo.EProt.Titolario;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.Utils;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt
{
    public class ProtocollazioneService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _username;
        string _password;

        public ProtocollazioneService(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password)
        {
            _logs = logs;
            _serializer = serializer;
            _username = username;
            _password = password;
        }

        private WebClient GetHttpClient()
        {
            var client = new WebClient();

            var credentials = _username + ":" + _password;
            string authInfo = Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
            client.Headers.Add(HttpRequestHeader.Authorization, "Basic " + authInfo);
            client.Headers.Add(HttpRequestHeader.ContentType, "application/x-www-form-urlencoded");
            client.Headers.Remove(HttpRequestHeader.Expect);
            client.Headers.Remove(HttpRequestHeader.KeepAlive);
            client.Credentials = new NetworkCredential(_username, _password);

            return client;
        }

        public TitolarioListType GetTitolario(string url)
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    _logs.InfoFormat("RICHIESTA DEL TITOLARIO");
                    var data = client.UploadValues(url, "POST", new NameValueCollection());
                    string res = Encoding.UTF8.GetString(data);

                    _logs.InfoFormat("RICHIESTA DEL TITOLARIO AVVENUTA CORRETTAMENTE");
                    var titolario = (TitolarioListType)_serializer.Deserialize(res, typeof(TitolarioListType));
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
                using (var client = GetHttpClient())
                {
                    _logs.InfoFormat("RICHIESTA DEI TIPI DOCUMENTO");
                    var data = client.UploadValues(url, "POST", new NameValueCollection());
                    string res = Encoding.UTF8.GetString(data);

                    _logs.InfoFormat("RICHIESTA DEI TIPI DOCUMENTO AVVENUTA CORRETTAMENTE");
                    var tipiDoc = (TipiDocumentoListType)_serializer.Deserialize(res, typeof(TipiDocumentoListType));
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
                using (var client = GetHttpClient())
                {
                    var nvc = new NameValueCollection();
                    metadati.ToList().ForEach(x => nvc.Add(x.Key, x.Value));
                    _logs.InfoFormat("INVIO RICHIESTA A PROTOCOLLAZIONE");

                    var data = client.UploadValues(url, "POST", nvc);
                    string res = Encoding.UTF8.GetString(data);
                    _logs.InfoFormat("INVIO RICHIESTA A PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    return res.Split(';');
                }
            }
            catch(Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }
    }
}
