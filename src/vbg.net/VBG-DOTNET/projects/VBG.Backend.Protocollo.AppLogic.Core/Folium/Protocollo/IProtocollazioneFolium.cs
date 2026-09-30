using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Protocollo
{
    public interface IProtocollazioneFolium
    {
        DatiProtocolloResponseType Protocolla();
        void InserisciAllegati(long idProtocollo);
        void InviaMail(long idProtocollo);
        void Assegna(long idProtocollo);
    }
}
