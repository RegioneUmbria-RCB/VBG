using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride
{
    public class JIrideSerializer : ProtocolloSerializer
    {
        public JIrideSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation protocolloValidation) : base(protocolloLog, protocolloValidation){}

        public override string Serialize(string sFileName, object pProtocollo, string messaggio)
        {
            return base.Serialize($"{DateTime.Now:HHmmss}-{sFileName}", pProtocollo, messaggio);
        }
    }
}
