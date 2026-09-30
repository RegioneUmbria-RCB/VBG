using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.EProt.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneEprot Create(IDatiProtocollo datiProtocollo, VerticalizzazioniConfiguration vert, IAnagraficaAmministrazione destinatario)
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
