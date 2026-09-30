using System;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Core.ProtocolloHalley2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2
{
    public class Halle2Serializer : ProtocolloSerializer
    {
        public Halle2Serializer(ProtocolloLogs protocolloLog, ProtocolloValidation protocolloValidation) : base(protocolloLog, protocolloValidation)
        {

        }
        public override XmlAttributeOverrides GetOverrides()
        {
            return new OverrideXml()
                    .Override<FileType>()
                    .Commit();
        }

        public override string Serialize(string sFileName, object pProtocollo, string messaggio)
        {
            var newFileNAme = $"{DateTime.Now.ToString("HHmmss")}-{sFileName}";

            return base.Serialize(newFileNAme, pProtocollo, messaggio);
        }
    }
}
