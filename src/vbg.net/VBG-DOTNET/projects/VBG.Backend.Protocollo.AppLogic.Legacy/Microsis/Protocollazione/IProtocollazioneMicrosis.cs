using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Microsis.Protocollazione
{
    public interface IProtocollazioneMicrosis
    {
        DatiProtocolloResponseType Protocolla(ProtocolloServiceWrapper wrapper);
    }
}
