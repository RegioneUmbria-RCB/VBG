using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Fascicolazione
{
    public interface IFascicolazione
    {
        DatiFascicoloResponseType Fascicola(FascicolazioneRequestAdapter requestAdapter);
    }
}
