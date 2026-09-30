using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection
{
    public interface IProtocolloFactory
    {
        ProtocolloValidation CreateValidation();
        ProtocolloSerializer CreateSerializer(ProtocolloLogs logs);
        ProtocolloLogs CreateLogs(
            ResolveDatiProtocollazioneService dati,
            Type protocolType);
        string GetTempFolder();
    }
}
