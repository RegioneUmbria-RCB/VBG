using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.DocArea;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocArea.Adapters
{
    public class DocAreaProtocolloInsertOutputAdapter
    {
        public static DatiProtocolloRes Adatta(ProtocollazioneRet response, ProtocolloLogs logs)
        {
            var datiRes = new DatiProtocolloRes();

            datiRes.NumeroProtocollo = response.lngNumPG.ToString();
            datiRes.DataProtocollo = response.strDataPG;
            datiRes.AnnoProtocollo = response.lngAnnoPG.ToString();
            datiRes.Warning = logs.Warnings.WarningMessage;

            return datiRes;
        }
    }
}
