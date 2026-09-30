using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloFactories
{
    public class AmbitoFactory
    {
        public static IAmbito Create (ResolveDatiProtocollazioneService dati)
        {
            if (dati.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new AmbitoIstanza(dati.Istanza);
            else if (dati.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                return new AmbitoMovimento(dati.Movimento);
            else if (dati.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_PANNELLO_PEC)
                return new AmbitoPec(dati.DatiPec);
            else if (dati.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
                return new AmbitoNessuno();
            else
                return new AmbitoDefault();
        }
    }
}
