using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class EliosSerializer : ProtocolloSerializer
    {

        public EliosSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation protocolloValidation) : base(protocolloLog, protocolloValidation)
        {
        }


        public override string Serialize(string sFileName, object pProtocollo, string messaggio)
        {
            var newFileNAme = $"{DateTime.Now:HHmmss}-{sFileName}";

            return base.Serialize(newFileNAme, pProtocollo, messaggio);
        }

    }
}
