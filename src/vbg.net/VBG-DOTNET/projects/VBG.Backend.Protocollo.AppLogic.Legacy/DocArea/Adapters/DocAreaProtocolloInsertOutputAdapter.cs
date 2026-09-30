

using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Adapters
{
    public class DocAreaProtocolloInsertOutputAdapter
    {
        public static DatiProtocolloResponseType Adatta(ProtocollazioneRet response, ProtocolloLogs logs)
        {
            var datiRes = new DatiProtocolloResponseType();

            datiRes.NumeroProtocollo = response.lngNumPG.ToString();
            datiRes.DataProtocollo = response.strDataPG;
            datiRes.AnnoProtocollo = response.lngAnnoPG.ToString();
            datiRes.Warning = logs.Warnings.WarningMessage;

            return datiRes;
        }
    }
}
