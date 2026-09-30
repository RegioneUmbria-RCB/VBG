
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    internal interface IFascicolazioneService
    {
        ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo);
        DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo);
        DatiFascicoloResponseType Fascicola(Fascicolo fascicolo);
        DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo);
    }
}
