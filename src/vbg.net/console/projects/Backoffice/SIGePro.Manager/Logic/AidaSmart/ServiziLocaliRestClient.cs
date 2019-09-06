using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;

namespace Init.SIGePro.Manager.Logic.AidaSmart
{
    public class ServiziLocaliRestClient
    {
        public T QueryItem<T>(string serviceUrl)
        {
            using (var wc = new WebClient())
            {
                var responsebytes = wc.DownloadData(serviceUrl);

                var risposta = Encoding.UTF8.GetString(responsebytes);

                return JsonConvert.DeserializeObject<T>(risposta);
            }
        }


        public string UrlEncode(string val)
        {
            return HttpContext.Current.Server.UrlEncode(val);
        }
    }
}
