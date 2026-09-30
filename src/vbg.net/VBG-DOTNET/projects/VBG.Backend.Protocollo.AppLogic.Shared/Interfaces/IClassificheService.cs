using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    internal interface IClassificheService
    {
        int GetIdClassificaByCodice(string codiceClassifica);

        ListaTipiClassificaType GetClassifiche();
    }
}
