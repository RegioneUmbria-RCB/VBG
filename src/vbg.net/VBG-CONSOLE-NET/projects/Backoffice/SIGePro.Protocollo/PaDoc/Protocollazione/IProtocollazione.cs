using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione
{
    public interface IProtocollazione
    {
        string Codice { get; }
        string UrlUpdate { get; }
        string UrlError { get; }
    }
}
