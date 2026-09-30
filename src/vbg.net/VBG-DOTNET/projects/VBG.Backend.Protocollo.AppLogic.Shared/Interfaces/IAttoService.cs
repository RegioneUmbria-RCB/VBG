using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    internal interface IAttoService
    {
        DatiAttoLetto LeggiAtto(string idAtto, int? annoAtto, string numeroAtto);
        void CheckAttoLetto(string idAtto, int? annoAtto, string numeroAtto, DatiAttoLetto attoLetto);
    }
}
