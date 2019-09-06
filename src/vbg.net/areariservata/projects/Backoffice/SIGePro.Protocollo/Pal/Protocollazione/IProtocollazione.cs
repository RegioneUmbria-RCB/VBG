using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public interface IProtocollazione
    {
        string Flusso { get; }
        MittenteType[] GetMittenti();
        DestinatariType GetDestinatari();
        AssegnatariType GetAssegnatari();
    }
}
