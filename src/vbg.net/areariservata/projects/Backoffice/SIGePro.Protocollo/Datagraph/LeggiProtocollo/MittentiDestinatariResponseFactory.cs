using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDatagraphService;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo
{
    public class MittentiDestinatariResponseFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(string flusso, Mittente mittente, Destinatario destinatario)
        {
            if (flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
            {
                return new MittentiDestinatariResponseArrivo(flusso, mittente, destinatario);
            }
            else if (flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
            {
                return new MittentiDestinatariResponsePartenza(flusso, mittente, destinatario);
            }
            else
            {
                throw new Exception($"FLUSSO {flusso} NON GESTITO");
            }
        }
    }
}
