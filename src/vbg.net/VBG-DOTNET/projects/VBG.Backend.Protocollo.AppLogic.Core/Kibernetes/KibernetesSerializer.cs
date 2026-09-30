using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public class KibernetesSerializer : ProtocolloSerializer
    {
        public KibernetesSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation protocolloValidation) : base(protocolloLog, protocolloValidation)
        {
        }

        public override XmlAttributeOverrides GetOverrides()
        {
            return new OverrideXml()
                    .Override<ProtocolloKibernetesV2Service.ExportResult>()
                    .Member("Buffer").XmlIgnore()
                    .Override<ProtocolloKibernetesV2Service.DataFile>()
                    .Member("Buffer").XmlIgnore()
                    .Commit();
        }

        public override string GetFileName(string fileNameOrig)
        {
            return $"{DateTime.Now.ToString("HHmmssfff")}-{fileNameOrig}";
        }
    }
}
