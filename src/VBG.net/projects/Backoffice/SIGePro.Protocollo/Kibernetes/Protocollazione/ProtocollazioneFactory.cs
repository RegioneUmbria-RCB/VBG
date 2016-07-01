using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Kibernetes.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Kibernetes.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(VerticalizzazioniConfiguration vert, List<IAnagraficaAmministrazione> anagrafiche, IDatiProtocollo datiProto, string funzionario)
        {
            if (datiProto.Flusso.Equals(ProtocolloConstants.COD_ARRIVO))
                return new ProtocollazioneArrivo(vert, anagrafiche, datiProto, funzionario);
            else if (datiProto.Flusso.Equals(ProtocolloConstants.COD_PARTENZA))
                return new ProtocollazionePartenza(vert, anagrafiche, datiProto, funzionario);
            else if (datiProto.Flusso.Equals(ProtocolloConstants.COD_INTERNO))
                return new ProtocollazioneInterna(vert, datiProto, funzionario);
            else
                throw new Exception(String.Format("FLUSSO {0} NON CODIFICATO", datiProto.Flusso));
        }
    }
}
