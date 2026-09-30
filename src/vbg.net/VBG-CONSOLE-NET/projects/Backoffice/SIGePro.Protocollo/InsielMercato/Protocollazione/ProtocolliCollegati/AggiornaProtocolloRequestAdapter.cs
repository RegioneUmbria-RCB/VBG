using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;
using Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo;

namespace Init.SIGePro.Protocollo.InsielMercato.Protocollazione.ProtocolliCollegati
{
    public class AggiornaProtocolloRequestAdapter
    {
        public static protocolUpdateRequest Adatta(protocolDetail dettaglioProto, previous[] protoCollegato)
        {
            return new protocolUpdateRequest
            {
                recordIdentifier = dettaglioProto.recordIdentifier,
                previousList = protoCollegato,
                documentList = dettaglioProto.documentList,
                filingList = dettaglioProto.filingList,
                officeList = dettaglioProto.officeList,
                subjectProtocol = dettaglioProto.subjectProtocol,
                receptionSendingDate = dettaglioProto.receptionSendingDate,
                receptionSendingDateSpecified = true
            };
        }
    }
}
