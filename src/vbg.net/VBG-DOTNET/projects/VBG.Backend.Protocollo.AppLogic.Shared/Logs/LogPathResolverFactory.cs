using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs
{
    public class LogPathResolverFactory : ILogPathResolverFactory
    {
        private readonly IRequestContextStorage _contextStorage;
        private readonly ITemporaryPathProvider _temporaryPathProvider;

        public LogPathResolverFactory(
            IRequestContextStorage contextStorage,
            ITemporaryPathProvider temporaryPathProvider)
        {
            _contextStorage = contextStorage;
            _temporaryPathProvider = temporaryPathProvider;
        }

        public ILogPathResolverService Create(
            ResolveDatiProtocollazioneService dati)
        {
            return new LogPathResolverService(
                dati,
                _contextStorage,
                _temporaryPathProvider);
        }
    }
}
