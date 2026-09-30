using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.Anagrafiche
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
