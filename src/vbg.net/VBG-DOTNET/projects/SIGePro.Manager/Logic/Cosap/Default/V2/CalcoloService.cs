using Init.SIGePro.Manager.Utils;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.Cosap.Default.V2
{
    public class CalcoloService
    {
        private readonly string _url;
        public CalcoloService(string url)
        {
            this._url = url;
        }

        public CalcoloResponse Calcola(CalcoloV2Request request)
        {
            var client = new RestClient
            {
                EndPoint = _url,
                Method = HttpVerb.POST,
                ContentType = "application/json",
                PostData = JsonConvert.SerializeObject(request)
            };

            var jsonResponse = client.MakeRequest();

            return JsonConvert.DeserializeObject<CalcoloResponse>(jsonResponse);

        }
    }
}
