using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    public interface IProtocolloStorico
    {
        DatiProtocolloLettoResponseType LeggiProtocolloStorico(string idProtocollo, string annoProtocollo, string numeroProtocollo);
        AllegatoResponseType LeggiAllegatoStorico();
        AllegatoResponseType LeggiAllegatoStoricoDaLeggiProtocolloStorico();
    }
}
