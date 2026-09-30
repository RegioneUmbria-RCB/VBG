using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Tinn.Segnatura;

namespace Init.SIGePro.Protocollo.Tinn.Protocollazione.MittentiDestinatari
{
    public interface IMittentiDestinatariFlusso
    {
        Mittente[] GetMittenti();
        Destinatario[] GetDestinatari();
        string Flusso { get; }
    }
}
