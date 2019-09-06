using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class MittentiDestinatariFactory
    {
        private static class Constants
        {
            public const string Arrivo = "E";
            public const string Partenza = "U";
            public const string Interno = "I";
        }

        public static ILeggiProtoMittentiDestinatari Create(string flusso, IEnumerable<MittDestType> mittenti, IEnumerable<MittDestType> destinatari)
        {
            if (flusso == Constants.Arrivo)
                return new MittentiDestinatariArrivoAdapter(mittenti, destinatari == null ? null : destinatari.First());
            else if (flusso == Constants.Partenza)
                return new MittentiDestinatariPartenzaAdapter(mittenti == null ? null : mittenti.First(), destinatari);
            else if (flusso == Constants.Interno)
                return new MittentiDestinatariInternoAdapter(mittenti == null ? null : mittenti.First(), destinatari == null ? null : destinatari.First());
            else
                throw new Exception(String.Format("FLUSSO {0} RESTITUITO DAL WEB SERVICE NON GESTITO", flusso));
        }
    }
}
