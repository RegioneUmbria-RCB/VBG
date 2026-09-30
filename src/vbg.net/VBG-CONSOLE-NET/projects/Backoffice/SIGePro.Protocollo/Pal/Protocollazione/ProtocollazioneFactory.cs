using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(IEnumerable<IAnagraficaAmministrazione> anagrafiche, string uo, string flusso)
        {
            if (flusso == ProtocolloConstants.COD_ARRIVO)
            {
                return new ProtocollazioneArrivo(anagrafiche);
            }
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
            {
                return new ProtocollazionePartenza(anagrafiche, uo);
            }
            else
            {
                throw new Exception(String.Format("FLUSSO - {0} - NON GESTITO", flusso));
            }
        }
    }
}
