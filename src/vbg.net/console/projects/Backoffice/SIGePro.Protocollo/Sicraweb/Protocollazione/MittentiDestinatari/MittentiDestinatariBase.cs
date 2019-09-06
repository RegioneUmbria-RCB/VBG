using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariBase
    {
        protected readonly IDatiProtocollo DatiProto;
        protected readonly string CodiceAmministrazione;
        protected readonly string CodiceAoo;

        public MittentiDestinatariBase(IDatiProtocollo datiProto, string codiceAmministrazione, string codiceAoo)
        {
            DatiProto = datiProto;
            CodiceAmministrazione = codiceAmministrazione;
            CodiceAoo = codiceAoo;
        }
    }
}
