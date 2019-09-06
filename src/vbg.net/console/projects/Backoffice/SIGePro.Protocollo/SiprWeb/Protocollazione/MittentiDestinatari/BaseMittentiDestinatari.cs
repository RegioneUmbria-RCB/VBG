using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.SiprWeb.Protocollazione.MittentiDestinatari
{
    public class BaseMittentiDestinatari
    {
        protected IDatiProtocollo DatiProto { get; private set; }

        public BaseMittentiDestinatari(IDatiProtocollo datiProto)
        {
            DatiProto = datiProto;
        }
    }
}
