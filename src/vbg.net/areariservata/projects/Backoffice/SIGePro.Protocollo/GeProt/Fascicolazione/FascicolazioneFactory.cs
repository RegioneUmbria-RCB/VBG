using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.GeProt.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.GeProt.Fascicolazione
{
    public class FascicolazioneFactory
    {
        public static IFascicolazioneIstanzaMovimento Create(DatiFascicolazioneConfiguration conf, ProtocolloLogs logs, Istanze istanza, IEnumerable<Movimenti> movimentiProtocollati)
        {
            if (conf.Ambito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new FascicolazioneIstanza(conf, logs, movimentiProtocollati);
            else if (conf.Ambito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                return new FascicolazioneMovimento(conf, istanza, logs);
            else
                throw new Exception("AMBITO ISTANZA / MOVIMENTO NON DEFINITO");
        }
    }
}
