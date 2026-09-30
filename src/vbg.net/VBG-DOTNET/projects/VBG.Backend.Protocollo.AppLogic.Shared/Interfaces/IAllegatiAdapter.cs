using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    public interface IAllegatiAdapter
    {
        IEnumerable<AllegatoResponseType> Adatta();
    }
}
