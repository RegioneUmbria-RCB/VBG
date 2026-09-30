using KeyValuePair = ProtocolloDocErGestioneDocumentaleService.KeyValuePair;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.Fascicolazione
{
    public class CambiaFascicoloMetadatiAdapter
    {
        private static class Constants
        {
            public const string NumeroFascicolo = "NUM_FASCICOLO";
            public const string AnnoFascicolo = "ANNO_FASCICOLO";
        }

        public static KeyValuePair[] Adatta(string numeroFascicolo, string annoFascicolo)
        {
            return new KeyValuePair[] 
            { 
                new KeyValuePair { key = Constants.NumeroFascicolo, value = numeroFascicolo }, 
                new KeyValuePair { key = Constants.AnnoFascicolo, value = annoFascicolo } 
            };
        }
    }
}
