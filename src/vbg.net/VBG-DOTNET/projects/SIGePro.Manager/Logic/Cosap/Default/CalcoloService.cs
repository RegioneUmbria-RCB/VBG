using Init.SIGePro.Manager.Utils;
using Newtonsoft.Json;

namespace Init.SIGePro.Manager.Logic.Cosap.Default
{
    public class CalcoloService
    {
        private readonly string _url;
        public CalcoloService(string url)
        {
            this._url = url;
        }

        public CalcoloResponse Calcola()
        {
            var client = new RestClient
            {
                EndPoint = _url,
                Method = HttpVerb.POST,
                ContentType = "application/json"
            };

            var jsonResponse = client.MakeRequest();

            return JsonConvert.DeserializeObject<CalcoloResponse>(jsonResponse);

        }
    }
}
