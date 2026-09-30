using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtocolloInsielService2;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione.MittentiDestinatari
{
    public interface IProtocollazioneMittentiDestinatari
    {
        MittenteInsProto[] GetMittenti();
        DestinatarioIOPInsProto[] GetDestinatari();
        verso Flusso { get; }
        UfficioInsProto[] GetUffici();





























































    }
}
