using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public class MetadatiGetGroupResponseAdapter
    {
        private static class Constants
        {
            public const string Enabled = "ENABLED";
            public const string EnabledValueTrue = "true";
        }


        public static bool IsGroupEnabled(KeyValuePair[] response)
        {
            if (response == null)
                return false;

            var dic = response.ToDictionary(x => x.key, y => y.value);

            return dic[Constants.Enabled] == Constants.EnabledValueTrue;
            
        }
    }
}
