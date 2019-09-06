using Init.SIGePro.Protocollo.Logs;
using System.Text;
using System;
using System.Linq;
using Init.SIGePro.Protocollo.Serialize;
using System.Net.Http;
using System.Net.Http.Headers;
using Init.Utils;
using Newtonsoft.Json;
using System.IO;
using System.Net;
using System.Web;
using System.Net.Mime;
using System.Collections.Generic;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazioneServiceWrapper : BaseProtocolloServiceWrapper
    {
        string _token;
        ProtocolloSerializer _serializer;
        const string _metodoCreaProtocollo = "creaProtocollo";
        const string _metodoInviaProtocollo = "inviaProtocollo";

        public ProtocollazioneServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string baseUrlWs, string token) : base(logs, baseUrlWs)
        {
            _token = token;
            _serializer = serializer;
        }

        public void InviaPec(string idProtocollo)
        {
            try
            {
                var uri = new Uri(new Uri(_baseUrlWs), _metodoInviaProtocollo);

                using (var client = GetHttpClient(_token))
                {
                    client.QueryString.Add("idProtocollo", idProtocollo);
                    _logs.InfoFormat("CHIAMATA A INVIO PEC del PROTOCOLLO ID: {0}", idProtocollo);
                    var errore = client.DownloadString(uri);

                    if (!String.IsNullOrEmpty(errore))
                    {
                        throw new Exception(errore);
                    }

                    _logs.InfoFormat("CHIAMATA A INVIO PEC del PROTOCOLLO ID: {0}, AVVENUTA CON SUCCESSO", idProtocollo);
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE AVVENUTO DURANTE L'INVIO PEC, ERRORE: {1}", ex.Message);
            }
        }

        public RootObject Protocolla(ProtocollazioneType request)
        {
            string wsCall = "http://cw2.gruppoapra.com/cw2/services/creaProtocollo";
            HttpWebRequest requestToServerEndpoint = (HttpWebRequest)WebRequest.Create(wsCall);

            string boundaryString = "----------------------------" + DateTime.Now.Ticks.ToString("x");

            // Set the http request header \\
            requestToServerEndpoint.Method = WebRequestMethods.Http.Post;
            requestToServerEndpoint.ContentType = "multipart/form-data; boundary=" + boundaryString;
            requestToServerEndpoint.KeepAlive = true;
            requestToServerEndpoint.Credentials = System.Net.CredentialCache.DefaultCredentials;
            // token generato
            requestToServerEndpoint.Headers["authorization"] = _token;

            // Use a MemoryStream to form the post data request,
            // so that we can get the content-length attribute.
            MemoryStream postDataStream = new MemoryStream();
            StreamWriter postDataWriter = new StreamWriter(postDataStream);

            // Include the file in the post data
            postDataWriter.Write("\r\n--" + boundaryString + "\r\n");
            postDataWriter.Write("Content-Disposition: form-data;"
                                    + "name=\"{0}\";"
                                    + "filename=\"{1}\""
                                    + "\r\nContent-Type: {2}\r\n\r\n",
                                    "datiProtocollo",
                                     "datiProtocollo",
                                    "application/octet-stream");
            postDataWriter.Flush();
            var xmlRequest = _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);

            _logs.InfoFormat("REQUEST: {0}", xmlRequest);
            var xml = HttpContext.Current.Server.UrlEncode(xmlRequest);
            _logs.InfoFormat("REQUEST ENCODED: {0}", xml);


            byte[] content = Encoding.ASCII.GetBytes(xml);
            postDataStream.Write(content, 0, content.Length);

            postDataWriter.Flush();

            if (request.Documenti != null && request.Documenti.Documento != null)
            {
                _logs.InfoFormat("DOCUMENTO PRINCIPALE, RIFERIMENTO: {0}, NOME: {1}", request.Documenti.Documento.Riferimento, request.Documenti.Documento.Nome);
                // allegato princ.
                postDataWriter.Write("\r\n--" + boundaryString + "\r\n");
                postDataWriter.Write("Content-Disposition: form-data;"
                                        + "name=\"{0}\";"
                                        + "filename=\"{1}\""
                                        + "\r\nContent-Type: {2}\r\n\r\n",
                                        request.Documenti.Documento.Riferimento,
                                         request.Documenti.Documento.Nome,
                                        "application/octet-stream");
                postDataWriter.Flush();

                postDataStream.Write(request.Documenti.Documento.Oggetto, 0, request.Documenti.Documento.Oggetto.Length);
                postDataWriter.Flush();

                if (request.Documenti.Allegati != null)
                {
                    foreach (var allegato in request.Documenti.Allegati.Documento)
                    {
                        _logs.InfoFormat("ALLEGATO, RIFERIMENTO: {0}, NOME: {1}", allegato.Riferimento, allegato.Nome);
                        // allegato
                        postDataWriter.Write("\r\n--" + boundaryString + "\r\n");
                        postDataWriter.Write("Content-Disposition: form-data;"
                                                + "name=\"{0}\";"
                                                + "filename=\"{1}\""
                                                + "\r\nContent-Type: {2}\r\n\r\n",
                                                allegato.Riferimento,
                                                 allegato.Nome,
                                                "application/octet-stream");
                        postDataWriter.Flush();
                        postDataStream.Write(allegato.Oggetto, 0, allegato.Oggetto.Length);
                        postDataWriter.Flush();
                    }
                }
            }

            postDataWriter.Write("\r\n--" + boundaryString + "--\r\n");

            postDataWriter.Flush();

            // Set the http request body content length
            requestToServerEndpoint.ContentLength = postDataStream.Length;

            // Dump the post data from the memory stream to the request stream
            using (Stream s = requestToServerEndpoint.GetRequestStream())
            {
                postDataStream.WriteTo(s);
            }
            postDataStream.Close();

            // Grab the response from the server. WebException will be thrown
            // when a HTTP OK status is not returned
            try
            {
                _logs.Info("CHIAMATA A PROTOCOLLAZIONE");
                WebResponse response = requestToServerEndpoint.GetResponse();
                StreamReader responseReader = new StreamReader(response.GetResponseStream());
                string replyFromServer = responseReader.ReadToEnd();

                _logs.InfoFormat("RISPOSTA JSON DAL WS: {0}", replyFromServer);

                var result = JsonConvert.DeserializeObject<RootObject>(replyFromServer);

                _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, NUMERO PROTOCOLLO: {0}, ANNO: {1}", result.numeroProtocollo, result.annoProtocollo);

                return result;
            }
            catch (WebException e)
            {
                using (WebResponse response = e.Response)
                {
                    HttpWebResponse httpResponse = (HttpWebResponse)response;
                    Console.WriteLine("Error code: {0}", httpResponse.StatusCode);
                    using (Stream data = response.GetResponseStream())
                    using (var reader = new StreamReader(data))
                    {
                        string text = reader.ReadToEnd();
                        throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, {0}", text), e);
                    }
                }
            }
        }
    }
}
