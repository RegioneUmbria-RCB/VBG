using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione.Documenti
{
    public interface IDocumenti
    {
        Documento DocPrincipale { get; }
        Documento[] Allegati { get; }
    }
}
