using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Classifiche
{
    public class LeggiTitolarioMetadataAdapter
    {
        private static class Constants
        {
            public const string CLASSIFICA = "CLASSIFICA";
            public const string COD_ENTE = "COD_ENTE";
            public const string COD_AOO = "COD_AOO";
            public const string PARENT_CLASSIFICA = "PARENT_CLASSIFICA";
            public const string DES_TITOLARIO = "DES_TITOLARIO";
            public const string ENABLED = "ENABLED";
        }

        string _codiceEnte;
        string _codiceAoo;

        public LeggiTitolarioMetadataAdapter(string codiceEnte, string codiceAoo)
        {
            _codiceEnte = codiceEnte;
            _codiceAoo = codiceAoo;
        }

        public KeyValuePair[] Adatta()
        {
            return new KeyValuePair[]
            {
                new KeyValuePair { key = Constants.COD_ENTE, value = _codiceEnte },
                new KeyValuePair { key = Constants.COD_AOO, value = _codiceAoo },
                new KeyValuePair { key = Constants.ENABLED, value = "true" }
            };
        }
    }
}
