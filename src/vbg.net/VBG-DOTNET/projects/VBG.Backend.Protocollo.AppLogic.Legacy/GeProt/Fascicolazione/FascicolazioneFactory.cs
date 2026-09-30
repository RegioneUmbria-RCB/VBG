using Init.SIGePro.Data;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Fascicolazione
{
    public static class FascicolazioneFactory
    {
        public static IFascicolazioneIstanzaMovimento Create(DatiFascicolazioneConfiguration conf, ProtocolloLogs logs, IIstanzaDaProtocollare istanza, IEnumerable<Movimenti> movimentiProtocollati)
        {

            switch (conf.Ambito)
            {
                case AmbitoProtocollazioneEnum.DA_ISTANZA:
                    {
                        return new FascicolazioneIstanza(conf, logs, movimentiProtocollati);
                    }
                case AmbitoProtocollazioneEnum.DA_MOVIMENTO:
                    {
                        return new FascicolazioneMovimento(conf, istanza, logs);
                    }
                case AmbitoProtocollazioneEnum.DA_PANNELLO_PEC:
                    {
                        if (!movimentiProtocollati.Any())
                        {
                            return new FascicolazioneIstanza(conf, logs, movimentiProtocollati);
                        }

                        return new FascicolazioneMovimento(conf, istanza, logs);
                    }
                default:
                    {
                        throw new ProtocolloException("AMBITO ISTANZA / MOVIMENTO NON DEFINITO");
                    }
            }
        }
    }
}
