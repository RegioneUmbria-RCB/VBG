using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariBase
    {
        protected IDatiProtocollo DatiProto { get; private set; }
        protected VerticalizzazioniConfiguration Vert { get; private set; }

        public MittentiDestinatariBase(IDatiProtocollo datiProto, VerticalizzazioniConfiguration verticalizzazione)
        {
            DatiProto = datiProto;
            Vert = verticalizzazione;
        }
    }
}
