using VBG.Backend.Protocollo.AppLogic.Core.Pal.Organigramma;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiProtocollo
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
