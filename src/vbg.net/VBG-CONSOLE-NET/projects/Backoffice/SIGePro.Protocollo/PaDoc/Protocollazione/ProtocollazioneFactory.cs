using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(ResolveDatiProtocollazioneService resolveDatiProto, VerticalizzazioniConfiguration vert)
        {
            if (resolveDatiProto.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new ProtocollazioneIstanza(resolveDatiProto, vert);
            
            if (resolveDatiProto.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                return new ProtocollazioneMovimento(resolveDatiProto, vert);
            
            throw new Exception("AMBITO -NESSUNO- NON GESTITO");
        }
    }
}
