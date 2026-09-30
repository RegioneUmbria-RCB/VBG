using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Protocollazione
{
    public class ProtocollazioneServiceWrapper : BaseServiceWrapper
    {
        public ProtocollazioneServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, VerticalizzazioniWrapper vert)
            : base(logs, serializer, vert.Username, vert.Password, vert.Url)
        {

        }

        public xapirestTypeInsProtocollo Protocolla(IEnumerable<ProtocolloAllegati> allegati, NameValueCollection values)
        {
            try
            {
                values.Add(_nomeParametroMetodo, "insProtocollo");

                _logs.InfoFormat("REQUEST: {0}", Utility.NameValueCollectionToString(values));

                if (allegati.Count() == 0)
                    return Protocolla(values);

                var files = allegati.Select(x => new UploadFile { ContentType = x.MimeType, Filename = x.NOMEFILE, Name = "file", Stream = new MemoryStream(x.OGGETTO) });
                var result = UploadFiles(files, values);

                var response = _serializer.Deserialize<xapirestTypeInsProtocollo>(result);

                if (!String.IsNullOrEmpty(response.insProtocollo_Result.ERRORCODE))
                    throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", response.insProtocollo_Result.ERRORCODE, response.insProtocollo_Result.MESSAGE));

                _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE AVVENUTA CON SUCCESSO. NUMERO PROTOCOLLO {0}, DATA PROTOCOLLO {1}, ID PROTOCOLLO: {2}", response.insProtocollo_Result.Numero, response.insProtocollo_Result.DataProtocollo, response.insProtocollo_Result.IdProto);

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, ERRORE {0}", ex.Message), ex);
            }
        }

        private xapirestTypeInsProtocollo Protocolla(NameValueCollection values)
        {

            using (var client = GetHttpClient())
            {
                client.QueryString = values;
                _logs.InfoFormat("RICHIESTA PROTOCOLLO SENZA ALLEGATI");
                var res = client.DownloadString(_url);
                var retVal = (xapirestTypeInsProtocollo)_serializer.Deserialize(res, typeof(xapirestTypeInsProtocollo));

                if (!String.IsNullOrEmpty(retVal.insProtocollo_Result.ERRORCODE))
                    throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", retVal.insProtocollo_Result.ERRORCODE, retVal.insProtocollo_Result.MESSAGE));

                _logs.InfoFormat("PROTOCOLLAZIONE SENZA ALLEGATI AVVENUTA CORRETTAMENTE. NUMERO PROTOCOLLO {0}, DATA PROTOCOLLO {1}, ID PROTOCOLLO: {2}", retVal.insProtocollo_Result.Numero, retVal.insProtocollo_Result.DataProtocollo, retVal.insProtocollo_Result.IdProto);

                return retVal;
            }

        }

        public byte[] UploadFiles(IEnumerable<UploadFile> files, NameValueCollection values)
        {
            var request = WebRequest.Create(_url);

            string credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(_username + ":" + _password));
            request.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;

            request.Method = "POST";
            var boundary = "---------------------------" + DateTime.Now.Ticks.ToString("x", NumberFormatInfo.InvariantInfo);
            request.ContentType = "multipart/form-data; boundary=" + boundary;
            boundary = "--" + boundary;

            using (var requestStream = request.GetRequestStream())
            {
                // Write the values
                foreach (string name in values.Keys)
                {
                    var buffer = Encoding.ASCII.GetBytes(boundary + Environment.NewLine);
                    requestStream.Write(buffer, 0, buffer.Length);
                    buffer = Encoding.ASCII.GetBytes(string.Format("Content-Disposition: form-data; name=\"{0}\"{1}{1}", name, Environment.NewLine));
                    requestStream.Write(buffer, 0, buffer.Length);
                    buffer = Encoding.UTF8.GetBytes(values[name] + Environment.NewLine);
                    requestStream.Write(buffer, 0, buffer.Length);
                }
                int i = 0;
                // Write the files
                foreach (var file in files)
                {
                    var buffer = Encoding.ASCII.GetBytes(boundary + Environment.NewLine);
                    requestStream.Write(buffer, 0, buffer.Length);
                    buffer = Encoding.UTF8.GetBytes(string.Format("Content-Disposition: form-data; name=\"{0}\"; filename=\"{1}\"{2}", String.Format("PRCORE03_99991009_{0}_Allegato_PathFile", i), file.Filename, Environment.NewLine));
                    requestStream.Write(buffer, 0, buffer.Length);
                    buffer = Encoding.ASCII.GetBytes(string.Format("Content-Type: {0}{1}{1}", file.ContentType, Environment.NewLine));
                    requestStream.Write(buffer, 0, buffer.Length);
                    file.Stream.CopyTo(requestStream);
                    buffer = Encoding.ASCII.GetBytes(Environment.NewLine);
                    requestStream.Write(buffer, 0, buffer.Length);
                    i++;
                }

                var boundaryBuffer = Encoding.ASCII.GetBytes(boundary + "--");
                requestStream.Write(boundaryBuffer, 0, boundaryBuffer.Length);
            }

            using (var response = request.GetResponse())
            using (var responseStream = response.GetResponseStream())
            using (var stream = new MemoryStream())
            {
                responseStream.CopyTo(stream);
                return stream.ToArray();
            }
        }
    }
}
