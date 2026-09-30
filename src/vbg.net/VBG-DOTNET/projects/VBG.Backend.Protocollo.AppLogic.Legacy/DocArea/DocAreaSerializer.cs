using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea
{
    public class DocAreaSerializer : ProtocolloSerializer
    {

        public DocAreaSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation validation) : base(protocolloLog, validation)
        {
        }

        public override string Serialize(string sFileName, object pProtocollo, string messaggio)
        {
            var newFileNAme = $"{DateTime.Now.ToString("HHmmss")}-{sFileName}";

            return base.Serialize(newFileNAme, pProtocollo, messaggio);
        }

    }
}
