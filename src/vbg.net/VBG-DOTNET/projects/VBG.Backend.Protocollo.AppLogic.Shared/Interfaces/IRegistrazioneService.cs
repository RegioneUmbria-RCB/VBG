
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    internal interface IRegistrazioneService
    {
        DatiProtocolloResponseType Registrazione(string registro, DatiProtocolloIn request);
    }
}
