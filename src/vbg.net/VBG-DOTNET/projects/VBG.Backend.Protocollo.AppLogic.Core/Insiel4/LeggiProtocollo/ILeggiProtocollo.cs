using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo
{
    public interface ILeggiProtocollo
    {
        DettaglioProtocolloResponse Leggi();
    }
}
