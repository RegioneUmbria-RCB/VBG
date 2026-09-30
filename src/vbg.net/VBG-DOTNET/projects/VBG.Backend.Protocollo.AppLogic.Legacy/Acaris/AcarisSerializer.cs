using System;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris
{
    public class AcarisSerializer : ProtocolloSerializer
    {
        public AcarisSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation protocolloValidation) : base(protocolloLog, protocolloValidation)
        {
        }

        public override XmlAttributeOverrides GetOverrides()
        {
            return new OverrideXml()
                    .Override<Init.SIGePro.Protocollo.AcarisDocumentServicePort.acarisContentStreamType>()
                    .Member("streamMTOM").XmlIgnore()
                    .Override<Init.SIGePro.Protocollo.AcarisObjectServicePort.acarisContentStreamType>()
                    .Member("streamMTOM").XmlIgnore()
                    .Commit();
        }

        public override string GetFileName(string fileNameOrig) => $"{DateTime.Now:HHmmssfff}-{fileNameOrig}";
    }
}
