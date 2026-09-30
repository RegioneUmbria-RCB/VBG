using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
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
