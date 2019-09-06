using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione;

namespace Init.SIGePro.Protocollo.EGrammata2.Fascicolazione
{
    public class FascicolazioneFactory
    {
        public static IFascicolazione Create(ProtocollazioneRequestConfiguration conf)
        {
            if (conf.DatiProtoService.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new FascicolazioneIstanza(conf.DatiProtoService);
            else if (conf.DatiProtoService.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                return new FascicolazioneMovimento(conf);
            else
                return new FascicolazioneDefault();
        }
    }
}
