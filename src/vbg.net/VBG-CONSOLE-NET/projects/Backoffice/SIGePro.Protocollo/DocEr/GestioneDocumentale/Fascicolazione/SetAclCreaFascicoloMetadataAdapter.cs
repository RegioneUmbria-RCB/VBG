using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Fascicolazione
{
    public class SetAclCreaFascicoloMetadataAdapter
    {
        private static class Constants
        {
            public const string PROGRESSIVO_FASCICOLO = "PROGR_FASCICOLO";
            public const string ANNO_FASCICOLO = "ANNO_FASCICOLO";
            public const string CLASSIFICA = "CLASSIFICA";
            public const string COD_AOO = "COD_AOO";
            public const string COD_ENTE = "COD_ENTE";
            public const string DES_FASCICOLO = "DES_FASCICOLO";
            public const string ENABLED = "ENABLED";
        }

        string _codiceEnte;
        string _codiceAoo;
        Fascicolo _fascicolo;

        public SetAclCreaFascicoloMetadataAdapter(Fascicolo datiFascicolo, string codiceEnte, string codiceAoo)
        {
            _codiceEnte = codiceEnte;
            _codiceAoo = codiceAoo;
            _fascicolo = datiFascicolo;
        }

        public KeyValuePair[] Adatta()
        {
            return new KeyValuePair[]
            {
                new KeyValuePair { key = Constants.ANNO_FASCICOLO, value = _fascicolo.AnnoFascicolo.ToString() },
                new KeyValuePair { key = Constants.PROGRESSIVO_FASCICOLO, value = _fascicolo.NumeroFascicolo },
                new KeyValuePair { key = Constants.CLASSIFICA, value = _fascicolo.Classifica },
                new KeyValuePair { key = Constants.DES_FASCICOLO, value = _fascicolo.Oggetto },
                new KeyValuePair { key = Constants.COD_ENTE, value = _codiceEnte },
                new KeyValuePair { key = Constants.COD_AOO, value = _codiceAoo }
            };
        }
    }
}
