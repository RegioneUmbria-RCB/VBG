using System;
using vbg.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneJProtocollo2 Create(IDatiProtocollo datiProto, ProtocolloService wrapper, VerticalizzazioniConfiguration vert, string operatore)
        {
            var conf = new ProtocollazioneConfiguration(datiProto, wrapper, vert, operatore);

            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(conf);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazionePartenza(conf);
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                return new ProtocollazioneInterno(conf);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", datiProto.Flusso));
        }
    }
}
