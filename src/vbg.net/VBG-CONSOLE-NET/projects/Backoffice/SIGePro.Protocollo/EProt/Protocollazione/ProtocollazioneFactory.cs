using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(IDatiProtocollo datiProtocollo, VerticalizzazioniConfiguration vert, IAnagraficaAmministrazione destinatario)
        {
            if (datiProtocollo.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(datiProtocollo, vert, destinatario);
            else if (datiProtocollo.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazionePartenza(datiProtocollo, vert, destinatario);
            else
                throw new Exception(String.Format("IL FLUSSO CHE SI STA UTILIZZANDO ({0}) NON E' STATO IMPLEMENTATO"));
        }
    }
}
