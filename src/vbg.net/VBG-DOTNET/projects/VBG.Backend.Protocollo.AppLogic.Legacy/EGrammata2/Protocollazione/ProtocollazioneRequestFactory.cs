using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Anagrafiche;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestFactory
    {
        public static IRequestProtocollo Create(ProtocollazioneRequestConfiguration conf, string flusso, AnagraficheService wrapper)
        {
            if (flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneRequestArrivo(conf, wrapper);
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazioneRequestPartenza(conf, wrapper);
            else if (flusso == ProtocolloConstants.COD_INTERNO)
                return new ProtocollazioneRequestInterno(conf);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));
        }
    }
}
