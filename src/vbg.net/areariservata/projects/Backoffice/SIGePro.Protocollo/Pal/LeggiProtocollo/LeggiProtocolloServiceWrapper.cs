using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.Pal.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper : BaseProtocolloServiceWrapper
    {
        private class Constants
        {
            public const string ModelKey = "leggiProtocollo";
            public const string AnnoProtocollo = "anno";
            public const string NumeroProtocollo = "numero";
        }

        string _token;
        ProtocolloSerializer _serializer;

        public LeggiProtocolloServiceWrapper(string baseUrlWs, string token, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(logs, baseUrlWs)
        {
            this._token = token;
            this._serializer = serializer;
        }

        public ProtocollazioneType LeggiProtocollo(string anno, string numero)
        {
            try
            {
                var uri = new Uri(new Uri(this._baseUrlWs), Constants.ModelKey);

                using (var client = GetHttpClient(_token))
                {
                    try
                    {
                        client.QueryString.Add(Constants.AnnoProtocollo, anno);
                        client.QueryString.Add(Constants.NumeroProtocollo, numero);
                        _logs.Info($"CHIAMATA A LEGGI PROTOCOLLO NUMERO: {numero}, ANNO: {anno}");
                        var xmlResponse = client.DownloadString(uri);

                        var response = this._serializer.Deserialize<ProtocollazioneType>(xmlResponse);
                        return response;
                    }
                    catch (WebException e)
                    {
                        using (WebResponse response = e.Response)
                        {
                            HttpWebResponse httpResponse = (HttpWebResponse)response;
                            using (Stream data = response.GetResponseStream())
                            using (var reader = new StreamReader(data))
                            {
                                string text = reader.ReadToEnd();
                                throw new Exception(text, e);
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA PROTOCOLLO NUMERO: {numero}, ANNO: {anno}: {1}, {ex.Message}", ex);
            }
        }
    }
}
