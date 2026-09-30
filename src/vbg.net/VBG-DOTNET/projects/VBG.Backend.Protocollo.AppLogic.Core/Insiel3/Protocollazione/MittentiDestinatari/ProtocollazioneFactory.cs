using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneInsiel3 Create(string flusso, IDatiProtocollo datiProto, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            IProtocollazioneInsiel3 rVal = null;
            
            if (flusso == ProtocolloConstants.COD_ARRIVO)
                rVal = new ProtocollazioneArrivo(datiProto, srv, vert, logs);
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                rVal = new ProtocollazionePartenza(datiProto, srv, vert, logs);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));

            return rVal;
        }
    }
}
