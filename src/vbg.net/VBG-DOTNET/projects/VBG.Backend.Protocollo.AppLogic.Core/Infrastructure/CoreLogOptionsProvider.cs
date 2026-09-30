using Microsoft.Extensions.Options;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Infrastructure
{
    public class CoreLogOptionsProvider : ILogOptionsProvider
    {
        private readonly IOptions<ProtocolloLogsOptions> _options;

        public CoreLogOptionsProvider(IOptions<ProtocolloLogsOptions> options)
        {
            this._options = options;
        }

        public ProtocolloLogsOptions GetOptions()
        {
            return this._options.Value;
        }
    }
}
