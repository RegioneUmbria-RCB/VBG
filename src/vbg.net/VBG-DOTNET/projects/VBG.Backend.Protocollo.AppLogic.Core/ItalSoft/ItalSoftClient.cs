using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft
{
    public class ItalSoftClient
    {
        private readonly string _urlProtocollazione;
        private readonly string _urlFascicolazione;
        private readonly ILog _logger;
        private readonly ProtocolloSerializer _serializer;

        public ItalSoftClient(ILog logger, ProtocolloSerializer serializer, string urlProtocollazione, string urlFascicolazione)
        {
            this._logger = logger;
            this._serializer = serializer;
            this._urlProtocollazione = urlProtocollazione;
            this._urlFascicolazione = urlFascicolazione;
        }

        protected void LogDebug(string message)
        {
            this._logger.Debug(message);
        }

        protected void LogInfo(string message)
        {
            this._logger.Info(message);
        }

        protected void Serialize(string fileName, object classToSerialize)
        {
            this._serializer.LogAndValidate(fileName, classToSerialize);
        }
    }
}
