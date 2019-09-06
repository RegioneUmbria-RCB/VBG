using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Anagrafiche
{
    public class SearchAnagraficheMetadatiRequestAdapter
    {
        public static KeyValuePair[] Adatta(string codice, VerticalizzazioniConfiguration vert)
        {
            return new KeyValuePair[] 
            { 
                new KeyValuePair { key = MetadatiAnagraficheConstants.CodiceEnte, value = vert.CodiceEnte }, 
                new KeyValuePair { key = MetadatiAnagraficheConstants.CodiceAoo, value = vert.CodiceAoo },
                new KeyValuePair { key = vert.AnagCustomCodice, value = codice }

            };
        }
    }
}
