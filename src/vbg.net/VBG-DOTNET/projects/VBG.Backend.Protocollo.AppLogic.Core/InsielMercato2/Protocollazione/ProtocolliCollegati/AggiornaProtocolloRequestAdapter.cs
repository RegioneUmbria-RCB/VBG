using ProtocolloInsielMercatoService2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione.ProtocolliCollegati
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
