using System.Net;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiAllegati
{
    public class LeggiAllegatiServiceWrapper : BaseProtocolloServiceWrapper
    {
        private class Constants
        {
            public const string ModelKey = "recuperaAllegato";
            public const string Id = "id";
        }

        string _token;

        public LeggiAllegatiServiceWrapper(string token, string baseUrlWs, ProtocolloLogs logs) : base(logs, baseUrlWs)
        {
            this._token = token;
        }

        public AllegatoResponseType GetAllegato(string id)
        {
            try
            {
                var uri = new Uri(new Uri(this._baseUrlWs), Constants.ModelKey);

                using (var client = GetHttpClient(_token))
                {
                    try
                    {
                        client.QueryString.Add(Constants.Id, id);
                        _logs.Info($"CHIAMATA A LEGGI ALLEGATO ID: {id}");
                        var buffer = client.DownloadData(uri);
                        var nomeFile = client.ResponseHeaders["Content-Disposition"].Split('=')[1];
                        var contentType = client.ResponseHeaders["Content-Type"];
                        return new AllegatoResponseType { Image = buffer, Serial = nomeFile, ContentType = contentType };
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
                throw new Exception($"ERRORE GENERATO DURANTE IL DOWNLOAD DELL'ALLEGATO CON ID: {id}, {ex.Message}", ex);
            }
        }
    }
}
