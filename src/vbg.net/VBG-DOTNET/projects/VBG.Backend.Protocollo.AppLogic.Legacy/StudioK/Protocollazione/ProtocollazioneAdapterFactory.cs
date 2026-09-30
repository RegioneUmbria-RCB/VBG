using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.Protocollazione
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
