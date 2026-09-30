using Microsoft.Extensions.DependencyInjection;
using Ninject;
using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class ProtocolloFactoryLegacy : IProtocolloFactory, IDisposable
    {
        private readonly IKernel _kernel;

        public ProtocolloFactoryLegacy(IKernel kernel)
        {
            _kernel = kernel;
        }

        public ProtocolloValidation CreateValidation()
        {
            return _kernel.Get<ProtocolloValidation>();
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
            var logPathFactory = _kernel.Get<ILogPathResolverFactory>();
            var logPathResolver = logPathFactory.Create(dati);

            var logOptionsProvider = _kernel.Get<ILogOptionsProvider>();
            var logOprionsResolver = logOptionsProvider.GetOptions();

            return new ProtocolloLogs(
                dati,
                protocolType,
                logPathResolver,
                logOprionsResolver);
        }

        public string GetTempFolder()
        {
            var temporaryPathProvider = _kernel.Get<ITemporaryPathProvider>();

            return temporaryPathProvider.GetTemporaryRootPath();
        }

        public void Dispose()
        {
            _kernel.Dispose();
        }
    }
}
