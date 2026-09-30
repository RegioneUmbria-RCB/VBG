using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.DocEr.Fascicolazione;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Fascicolazione
{
    public class GestioneDocumentaleFascicoloMetadataAdapter
    {
        string _codiceEnte;
        string _codiceAoo;
        Fascicolo _fascicolo;

        public GestioneDocumentaleFascicoloMetadataAdapter(Fascicolo datiFascicolo, string codiceEnte, string codiceAoo)
        {
            _codiceEnte = codiceEnte;
            _codiceAoo = codiceAoo;
            _fascicolo = datiFascicolo;
        }

        public KeyValuePair[] Adatta()
        {
            var res = new List<KeyValuePair>();

            if (_fascicolo.AnnoFascicolo.HasValue)
                res.Add(new KeyValuePair{ key = FascicolazioneMetadataConstants.ANNO_FASCICOLO, value = _fascicolo.AnnoFascicolo.ToString()});

            if (!String.IsNullOrEmpty(_fascicolo.NumeroFascicolo))
                res.Add(new KeyValuePair { key = FascicolazioneMetadataConstants.PROGRESSIVO_FASCICOLO, value = _fascicolo.NumeroFascicolo });

            if (!String.IsNullOrEmpty(_fascicolo.Classifica))
                res.Add(new KeyValuePair { key = FascicolazioneMetadataConstants.CLASSIFICA, value = _fascicolo.Classifica });

            //if (!String.IsNullOrEmpty(_fascicolo.Oggetto))
            //    res.Add(new KeyValuePair { key = FascicolazioneMetadataConstants.DES_FASCICOLO, value = _fascicolo.Oggetto });

            res.Add(new KeyValuePair { key = FascicolazioneMetadataConstants.COD_ENTE, value = _codiceEnte });
            res.Add(new KeyValuePair { key = FascicolazioneMetadataConstants.COD_AOO, value = _codiceAoo });

            return res.ToArray();
        }
    }
}
