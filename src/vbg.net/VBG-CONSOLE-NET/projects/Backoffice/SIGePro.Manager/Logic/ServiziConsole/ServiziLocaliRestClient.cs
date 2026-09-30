using log4net;
using Newtonsoft.Json;
using System.Net;
using System.Text;
using System.Web;

namespace Init.SIGePro.Manager.Logic.ServiziConsole
{
    public class ServiziLocaliRestClient
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ServiziLocaliRestClient));

        public T QueryItem<T>(string serviceUrl)
        {
            this._log.Debug($"Lettura dati da bo locale all'indirizzo {serviceUrl}");

            using (var wc = new WebClient())
            {
                var responsebytes = wc.DownloadData(serviceUrl);

                var risposta = Encoding.UTF8.GetString(responsebytes);

                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"risposta dal servizio {risposta}");
                }

                return JsonConvert.DeserializeObject<T>(risposta);
            }
        }


        public string UrlEncode(string val)
        {
            return HttpContext.Current.Server.UrlEncode(val);
        }
    }
}
