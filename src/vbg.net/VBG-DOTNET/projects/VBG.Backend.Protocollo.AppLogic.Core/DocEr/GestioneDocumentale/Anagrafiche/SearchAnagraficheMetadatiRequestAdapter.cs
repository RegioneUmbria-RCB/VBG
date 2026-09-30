using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Verticalizzazioni;
using KeyValuePair = ProtocolloDocErGestioneDocumentaleService.KeyValuePair;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.Anagrafiche
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
