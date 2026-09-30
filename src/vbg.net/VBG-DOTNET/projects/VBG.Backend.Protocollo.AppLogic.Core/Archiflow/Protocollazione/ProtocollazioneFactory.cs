

using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Archiflow.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneArchiflow Create(IDatiProtocollo datiProto, IEnumerable<IAnagraficaAmministrazione> anagraficaAmministrazione, ProtocollazioneServiceWrapper wrapper, VerticalizzazioniWrapper vert, string fkidProtoIstanza)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(datiProto, anagraficaAmministrazione, wrapper, vert, fkidProtoIstanza);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazionePartenza(datiProto, anagraficaAmministrazione, wrapper, vert, fkidProtoIstanza);
            else
                throw new Exception(String.Format("IL FLUSSO CODICE {0} NON E' GESTITO DAL SISTEMA DI PROTOCOLLAZIONE", datiProto.Flusso));
        }
    }
}
