using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public interface IProtocollazione
    {
        Mittente[] GetMittente();
        Destinatario[] GetDestinatario();
        string Flusso { get; }
        string Uo { get; }
        string Smistamento { get; }
    }
}
