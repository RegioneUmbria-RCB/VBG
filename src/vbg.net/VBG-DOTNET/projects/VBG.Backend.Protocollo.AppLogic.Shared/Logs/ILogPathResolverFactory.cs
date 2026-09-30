using VBG.Backend.Protocollo.AppLogic.Shared.Logs.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs
{
    public interface ILogPathResolverFactory
    {
        ILogPathResolverService Create(
            ResolveDatiProtocollazioneService dati);
    }
}
