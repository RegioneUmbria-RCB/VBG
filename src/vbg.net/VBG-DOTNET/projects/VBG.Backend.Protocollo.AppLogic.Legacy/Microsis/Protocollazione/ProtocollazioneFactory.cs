using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Microsis.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneMicrosis Create(IDatiProtocollo datiProto, List<IAnagraficaAmministrazione> anagrafiche)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(datiProto, anagrafiche);
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", datiProto.Flusso));
        }
    }
}
