using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;

namespace Init.SIGePro.Protocollo.Sigedo.Smistamenti
{


    public class SmistamentoConfiguration
    {
        internal readonly string Url;
        internal readonly ProtocolloEnum.TipoProvenienza Provenienza;
        internal readonly string Operatore;
        internal readonly ProtocolloLogs ProtocolloLogs;
        internal readonly ProtocolloSerializer Serializer;
        internal IDatiProtocollo DatiProto { get; set; }
        internal readonly ResolveDatiProtocollazioneService DatiProtocollazione;

        public SmistamentoConfiguration(string url, ProtocolloEnum.TipoProvenienza provenienza, string operatore,
                                        ResolveDatiProtocollazioneService datiProtocollazione, ProtocolloLogs protocolloLogs, 
                                        ProtocolloSerializer serializer, IDatiProtocollo datiProto)
        {
            Url = url;
            Provenienza = provenienza;
            Operatore = operatore;
            DatiProtocollazione = datiProtocollazione;
            ProtocolloLogs = protocolloLogs;
            Serializer = serializer;
            DatiProto = datiProto;
        }

    }
}
