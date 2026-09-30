using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.InsielMercato.Services;
using Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo.Identificativo;

namespace Init.SIGePro.Protocollo.InsielMercato.Protocollazione.ProtocolliCollegati
{
    public class ProtocolliCollegatiFactory
    {
        public static IProtocolliCollegati Create(ResolveDatiProtocollazioneService dati, ProtocollazioneService wrapper)
        {
            if (dati.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new ProtocolliCollegatiIstanza();
            else if (dati.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if(String.IsNullOrEmpty(dati.Istanza.NUMEROPROTOCOLLO) ||  !dati.Istanza.DATAPROTOCOLLO.HasValue)
                    return null;

                var identificativo = IdentificativoFactory.Create(dati.Istanza.FKIDPROTOCOLLO, Convert.ToInt32(dati.Istanza.NUMEROPROTOCOLLO), dati.Istanza.DATAPROTOCOLLO.Value.Year, dati);

                return new ProtocolliCollegatiMovimenti(identificativo, wrapper);
            }
            else
                throw new Exception("AMBITO NON GESTITO");
        }
    }
}
