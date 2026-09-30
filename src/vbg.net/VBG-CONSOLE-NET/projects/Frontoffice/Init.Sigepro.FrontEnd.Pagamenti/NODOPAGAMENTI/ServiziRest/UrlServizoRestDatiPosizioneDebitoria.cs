using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.ServiziRest
{
    public class UrlServizoRestDatiPosizioneDebitoria
    {
        private readonly string baseUrl;

        public UrlServizoRestDatiPosizioneDebitoria(string soapUrl)
        {
            if (String.IsNullOrEmpty(soapUrl))
            {
                this.baseUrl = String.Empty;

                return;
            }

            this.baseUrl = soapUrl.Substring(0, soapUrl.IndexOf("/services/") + 9) + "/rest";
        }

        public string GetUrl(string identecreditore, string idposizionedebitoria)
        {
            if (string.IsNullOrEmpty(this.baseUrl))
            {
                return String.Empty;
            }

            return $"{this.baseUrl}/pagamenti/posizionidebitorie/{identecreditore}/{idposizionedebitoria}";
        }
    }
}
