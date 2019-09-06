using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(IDatiProtocollo datiProto, List<IAnagraficaAmministrazione> anagrafiche)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(datiProto, anagrafiche);
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", datiProto.Flusso));
        }
    }
}
