using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Smistamenti
{


    public class SmistamentoConfiguration
    {
        internal readonly string Url;
        internal readonly TipoProvenienza Provenienza;
        internal readonly string Operatore;
        internal readonly ProtocolloLogs ProtocolloLogs;
        internal readonly ProtocolloSerializer Serializer;
        internal IDatiProtocollo DatiProto { get; set; }
        internal readonly ResolveDatiProtocollazioneService DatiProtocollazione;

        public SmistamentoConfiguration(string url, TipoProvenienza provenienza, string operatore,
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
