using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Pal.Organigramma;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.LeggiProtocollo
{
    public class LeggiProtocolloFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(ProtocollazioneType response, OrganigrammaServiceWrapper organigrammaService)
        {
            if (response.Intestazione.TipoProtocollo == ProtocolloConstants.COD_ARRIVO)
            {
                return new LeggiProtocolloArrivo(response, organigrammaService);
            }
            else if (response.Intestazione.TipoProtocollo == ProtocolloConstants.COD_PARTENZA)
            {
                return new LeggiProtocolloPartenza(response, organigrammaService);
            }
            else if (response.Intestazione.TipoProtocollo == ProtocolloConstants.COD_INTERNO)
            {
                return new LeggiProtocolloInterno(response, organigrammaService);
            }
            else
            {
                throw new Exception($"FLUSSO {response.Intestazione.TipoProtocollo} NON GESTITO");
            }
        }
    }
}
