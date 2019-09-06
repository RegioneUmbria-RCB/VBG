using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.Protocollazione.MittentiDestinatari
{
    public interface IProtocollazioneMittentiDestinatari
    {
        MittenteInsProto[] GetMittenti();
        DestinatarioIOPInsProto[] GetDestinatari();
        verso Flusso { get; }
        UfficioInsProto[] GetUffici();





























































    }
}
