using System.Configuration;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Infrastructure
{
    public class FrameworkLogOptionsProvider : ILogOptionsProvider
    {
        // Usati nel web.config per i parametri del protocollo
        public const string ConfigurationMaxSizeRollBackupsKey = "ProtocolloLog.MaxSizeRollBackups";
        public const string ConfigurationMaximumFileSizeKey = "ProtocolloLog.MaximumFileSize";
        public const string ConfigurationConversionPatternKey = "ProtocolloLog.ConversionPattern";
        public const string ConfigurationMinLevelKey = "ProtocolloLog.MinLevel";
        public const string ConfigurationMantieniLogKey = "ProtocolloLog.MantieniLogSuProtocollazioneRiuscita";

        public ProtocolloLogsOptions GetOptions()
        {
            return new ProtocolloLogsOptions()
            {
                MaxSizeRollBackups = ConfigurationManager.AppSettings[ConfigurationConversionPatternKey],
                MaximumFileSize = ConfigurationManager.AppSettings[ConfigurationMaximumFileSizeKey],
                ConversionPattern = ConfigurationManager.AppSettings[ConfigurationConversionPatternKey],
                MinLevel = ConfigurationManager.AppSettings[ConfigurationMinLevelKey],
                MantieniLog = ConfigurationManager.AppSettings[ConfigurationMantieniLogKey]
            };
        }
    }
}