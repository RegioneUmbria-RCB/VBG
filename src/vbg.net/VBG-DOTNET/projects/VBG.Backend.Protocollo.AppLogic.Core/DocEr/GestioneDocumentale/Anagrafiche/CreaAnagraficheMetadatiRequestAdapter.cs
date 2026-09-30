using VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Verticalizzazioni;
using KeyValuePair = ProtocolloDocErGestioneDocumentaleService.KeyValuePair;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.Anagrafiche
{
    public class CreaAnagraficheMetadatiRequestAdapter
    {
        public static KeyValuePair[] Adatta(IAmministrazioneAnagraficaVbg anagrafica, VerticalizzazioniConfiguration vert)
        {
            return new KeyValuePair[] 
            { 
                new KeyValuePair { key = MetadatiAnagraficheConstants.TypeId, value = vert.TypeIdAnagraficaCustom },
                new KeyValuePair { key = MetadatiAnagraficheConstants.CodiceEnte, value = vert.CodiceEnte },
                new KeyValuePair { key = MetadatiAnagraficheConstants.CodiceAoo, value = vert.CodiceAoo },
                new KeyValuePair { key = vert.AnagCustomCodice, value = anagrafica.CodiceFiscalePartitaIva },
                new KeyValuePair { key = vert.AnagCustomDescrizione, value = anagrafica.Nominativo }
            };
        }
    }
}
