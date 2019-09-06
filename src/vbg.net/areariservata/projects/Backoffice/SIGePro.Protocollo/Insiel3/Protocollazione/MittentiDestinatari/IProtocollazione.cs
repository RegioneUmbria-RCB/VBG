using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public interface IProtocollazione
    {
        MittenteInsProto[] GetMittenti();
        DestinatarioIOPInsProto[] GetDestinatari();
        verso Flusso { get; }
        bool InvioTelematicoAttivo { get; }
        UfficioInsProto[] GetUffici();




























































    }
}
