using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Delta.Builders
{
    public class DeltaSegnaturaBuilder
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        DeltaSegnaturaParamConfiguration _conf;

        public readonly ProtocolloDeltaService.Protocollo SegnaturaRequest;

        internal DeltaSegnaturaBuilder(ProtocolloLogs logs, ProtocolloSerializer serializer, DeltaSegnaturaParamConfiguration conf)
        {
            _logs = logs;
            _serializer = serializer;
            _conf = conf;

            SegnaturaRequest = new ProtocolloDeltaService.Protocollo
            {
                CodiceRegistro = _conf.CodiceRegistro,
                Mittente = _conf.Mittente,
                Destinatario = _conf.Destinatario,
                Conoscenza = _conf.Conoscenza,
                Oggetto = _conf.Oggetto,
                Tipo = _conf.Tipo,
                TipoLettera = _conf.TipoLettera,
                ClassificazioneLiv1 = _conf.Classifica1,
                ClassificazioneLiv2 = _conf.Classifica2
            };
        }
    }
}
