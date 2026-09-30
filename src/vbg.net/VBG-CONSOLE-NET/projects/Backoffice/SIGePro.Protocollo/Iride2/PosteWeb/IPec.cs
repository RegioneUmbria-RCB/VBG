using Init.SIGePro.Protocollo.Iride2.Proxies;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.Iride2.PosteWeb
{
    public interface IPec
    {
        string Invia(string url, string proxyAddress, string idDocumento, string oggetto, string corpo, string mittente, string utente, string ruolo, string codiceAmministrazione);
    }
}
