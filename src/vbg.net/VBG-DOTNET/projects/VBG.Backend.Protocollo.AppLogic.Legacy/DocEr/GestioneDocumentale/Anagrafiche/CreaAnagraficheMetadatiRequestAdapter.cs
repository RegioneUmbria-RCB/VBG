using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.Anagrafiche
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
