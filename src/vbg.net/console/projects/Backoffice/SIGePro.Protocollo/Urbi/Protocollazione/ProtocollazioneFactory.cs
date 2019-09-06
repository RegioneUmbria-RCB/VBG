using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(IDatiProtocollo datiProtocollo, VerticalizzazioniWrapper vert, string operatore, ProtocolloLogs logs, ProtocolloSerializer serializer, IEnumerable<IAnagraficaAmministrazione> mittDest)
        {
            if (datiProtocollo.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(datiProtocollo, vert, operatore, logs, serializer, mittDest);
            else if (datiProtocollo.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazionePartenza(datiProtocollo, vert, operatore, logs, serializer, mittDest);
            else if (datiProtocollo.Flusso == ProtocolloConstants.COD_INTERNO)
                return new ProtocollazioneInterna(datiProtocollo, vert, operatore, logs, serializer);
            else
                throw new Exception(String.Format("FLUSSO {0} NON TROVATO", datiProtocollo.Flusso));
        }
    }
}
