using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static IFlussoMittentiDestinatari Create(string flusso, IDatiProtocollo datiProto, string codiceAmministrazione, string codiceAoo)
        {
            IFlussoMittentiDestinatari mittentiDestinatari = null;

            if (flusso == ProtocolloConstants.COD_ARRIVO)
                mittentiDestinatari = new MittentiDestinatariArrivo(datiProto, codiceAmministrazione, codiceAoo);
            else if(flusso == ProtocolloConstants.COD_INTERNO)
                mittentiDestinatari = new MittentiDestinatariInterno(datiProto, codiceAmministrazione, codiceAoo);
            else if(flusso == ProtocolloConstants.COD_PARTENZA)
                mittentiDestinatari = new MittentiDestinatariPartenza(datiProto, codiceAmministrazione, codiceAoo);

            return mittentiDestinatari;
        }
    }
}
