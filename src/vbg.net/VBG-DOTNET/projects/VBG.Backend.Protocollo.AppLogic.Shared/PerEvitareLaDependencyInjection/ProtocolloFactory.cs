using Microsoft.Extensions.DependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection
{
    public class ProtocolloFactory : IProtocolloFactory, IDisposable
    {
        private readonly IServiceScope _scope;

        public ProtocolloFactory(IServiceProvider provider)
        {
            _scope = provider.CreateScope();
        }

        public ProtocolloValidation CreateValidation()
        {
            return _scope.ServiceProvider.GetRequiredService<ProtocolloValidation>();
        }

        public ProtocolloSerializer CreateSerializer(ProtocolloLogs logs)
        {
            var validation = CreateValidation();
            return new ProtocolloSerializer(logs, validation);
        }

        public ProtocolloLogs CreateLogs(
            ResolveDatiProtocollazioneService dati,
            Type protocolType)
        {
            var logPathFactory = _scope.ServiceProvider.GetRequiredService<ILogPathResolverFactory>();
            var logPathResolver = logPathFactory.Create(dati);

            var logOptionsProvider = _scope.ServiceProvider.GetRequiredService<ILogOptionsProvider>();
            var logOptionsResolver = logOptionsProvider.GetOptions();

            return new ProtocolloLogs(
                dati,
                protocolType,
                logPathResolver,
                logOptionsResolver);
        }

        public string GetTempFolder()
        {
            var temporaryPathProvider = _scope.ServiceProvider.GetRequiredService<ITemporaryPathProvider>();

            return temporaryPathProvider.GetTemporaryRootPath();
        }

        public void Dispose()
        {
            _scope.Dispose();
        }
    }
}
