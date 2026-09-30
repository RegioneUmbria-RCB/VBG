using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static IMittentiDestinatari Create(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, GestioneDocumentaleService wrapperGestDoc)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new MittentiDestinatariArrivo(datiProto, vert, wrapperGestDoc);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new MittentiDestinatariPartenza(datiProto, vert, wrapperGestDoc);
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                return new MittentiDestinatariInterno(datiProto, vert);
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", datiProto.Flusso));
        }
    }
}
