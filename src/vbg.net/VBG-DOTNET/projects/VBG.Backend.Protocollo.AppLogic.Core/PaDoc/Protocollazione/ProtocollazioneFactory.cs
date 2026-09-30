using VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazionePaDoc Create(ResolveDatiProtocollazioneService resolveDatiProto, VerticalizzazioniConfiguration vert)
        {
            if (resolveDatiProto.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new ProtocollazioneIstanza(resolveDatiProto, vert);
            
            if (resolveDatiProto.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                return new ProtocollazioneMovimento(resolveDatiProto, vert);
            
            throw new Exception("AMBITO -NESSUNO- NON GESTITO");
        }
    }
}
