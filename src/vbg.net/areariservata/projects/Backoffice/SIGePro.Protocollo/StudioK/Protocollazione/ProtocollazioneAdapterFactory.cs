using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.Protocollazione
{
    public class ProtocollazioneAdapterFactory
    {
        public static IProtocollazioneAdapter Create(IEnumerable<IAnagraficaAmministrazione> mittDest, IDatiProtocollo datiProto, VerticalizzazioniWrapper vert)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneAdapterArrivo(mittDest.First(), vert, datiProto.Uo, datiProto.ProtoIn.Classifica);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazioneAdapterPartenza(mittDest, vert, datiProto.Uo, datiProto.ProtoIn.Classifica);
            else
                throw new Exception(String.Format("FLUSSO {0} NON PREVISTO", datiProto.Flusso));
        }
    }
}
