using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs
{
    public class ProtocolloLogsOptions
    {
        public string MaxSizeRollBackups { get; set; }
        public string MaximumFileSize { get; set; }
        public string ConversionPattern { get; set; }
        public string MinLevel { get; set; }
        public string MantieniLog { get; set; }
    }
}
