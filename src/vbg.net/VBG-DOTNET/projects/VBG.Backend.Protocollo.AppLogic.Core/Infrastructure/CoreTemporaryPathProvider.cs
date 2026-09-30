using Microsoft.Extensions.Hosting;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;

namespace VBG.Backend.Protocollo.AppLogic.Core.Infrastructure
{
    internal class CoreTemporaryPathProvider : ITemporaryPathProvider
    {
        private readonly IHostEnvironment _environment;

        public CoreTemporaryPathProvider(IHostEnvironment environment)
        {
            _environment = environment;
        }

        public string GetTemporaryRootPath()
        {
            var path = Path.Combine(
                _environment.ContentRootPath,
                "temp");

            if (!Directory.Exists(path))
                Directory.CreateDirectory(path);

            return path;
        }
    }
}
