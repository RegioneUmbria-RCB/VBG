using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Tinn.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Tinn.Protocollazione.MittentiDestinatari
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
