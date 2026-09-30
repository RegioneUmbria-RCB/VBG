using System;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft
{
    public class ItalSoftSerializer : ProtocolloSerializer
    {
        public ItalSoftSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation protocolloValidation) : base(protocolloLog, protocolloValidation)
        {
        }

        public override XmlAttributeOverrides GetOverrides()
        {
            return new OverrideXml()
                    .Override<AllegatoProtocollo>()
                    .Member("Content").XmlIgnore()
                    .Commit();
        }

        public override string GetFileName(string fileNameOrig)
        {
            return $"{DateTime.Now.ToString("HHmmssfff")}-{fileNameOrig}";
        }
    }
}
