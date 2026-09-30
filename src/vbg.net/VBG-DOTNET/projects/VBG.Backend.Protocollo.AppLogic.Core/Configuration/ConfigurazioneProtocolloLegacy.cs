using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Configuration
{
    public class ConfigurazioneProtocolloLegacy
    {
        public static string SectionName => "ProtocolloLegacy";
        public string Url { get; set; } = "";
    }
}
