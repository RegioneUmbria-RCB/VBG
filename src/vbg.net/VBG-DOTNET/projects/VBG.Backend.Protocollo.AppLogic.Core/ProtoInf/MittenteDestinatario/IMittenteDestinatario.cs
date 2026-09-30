using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.MittenteDestinatario
{
    public interface IMittenteDestinatario
    {
        string GetMittente();
        string GetDestinatario();
    }
}
