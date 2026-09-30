using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class SearchDocumentsMetadatiRequestAdapter
    {
        private static class Constants
        {
            public const string CodiceEnte = "COD_ENTE";
            public const string CodiceAoo = "COD_AOO";
            public const string NumeroProtocollo = "NUM_PG";
            public const string AnnoProtocollo = "ANNO_PG";
        }

        public static KeyValuePair[] Adatta(string numeroProtocollo, string annoProtocollo, string codiceAoo, string codiceEnte)
        {
            return new KeyValuePair[] 
            { 
                new KeyValuePair { key = Constants.CodiceEnte, value = codiceEnte },
                new KeyValuePair { key = Constants.CodiceAoo, value = codiceAoo },
                new KeyValuePair { key = Constants.NumeroProtocollo, value = numeroProtocollo },
                new KeyValuePair { key = Constants.AnnoProtocollo, value = annoProtocollo }
            };
        }
    }
}
