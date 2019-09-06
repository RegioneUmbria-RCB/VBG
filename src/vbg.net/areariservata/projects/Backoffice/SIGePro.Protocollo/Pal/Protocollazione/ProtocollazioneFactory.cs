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
        public static IProtocollazione Create(IEnumerable<IAnagraficaAmministrazione> anagrafiche, IDatiProtocollo datiProto)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
            {
                return new ProtocollazioneArrivo(anagrafiche, datiProto.Uo);
            }
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                return new ProtocollazionePartenza(anagrafiche, datiProto.Uo);
            }
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                return new ProtocollazioneInterna(datiProto.Uo, datiProto.AmministrazioniProtocollo[0].PROT_UO);
            }
            else
            {
                throw new Exception(String.Format("FLUSSO - {0} - NON GESTITO", datiProto.Flusso));
            }
        }
    }
}
