using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea
{
    public interface ILeggiProtocolloService
    {
        DatiProtocolloLettoResponseType Leggi(int anno, int numero, string tipoRegistro);
    }
}
