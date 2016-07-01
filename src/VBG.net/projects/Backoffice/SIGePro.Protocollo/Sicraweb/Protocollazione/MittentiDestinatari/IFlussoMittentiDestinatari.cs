using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public interface IFlussoMittentiDestinatari
    {
        MittenteMulti[] GetMittenti();
        DestinataroMulti[] GetDestinatari();
        Mittente MittentePrincipale { get; }
        Destinatario DestinatarioPrincipale { get; }
        Flusso FlussoProtocollo { get; }
    }
}
