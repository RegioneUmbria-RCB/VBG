using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariFlussoFactory
    {
        public static IMittentiDestinatariFlusso Create(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new MittentiDestinatariArrivo(datiProto, vert);
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                return new MittentiDestinatariInterno(datiProto, vert);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new MittentiDestinatariPartenza(datiProto, vert);
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", datiProto.Flusso));
        }
    }
}
