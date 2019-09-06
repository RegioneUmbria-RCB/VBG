using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf.MittenteDestinatario
{
    public interface IMittenteDestinatario
    {
        string GetMittente();
        string GetDestinatario();
    }
}
