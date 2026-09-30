using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public interface ILeggiProtocolloService
    {
        DatiProtocolloLettoResponseType LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, DateTime? dataProtocollo);
    }
}
