using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public interface IRequestAdapter
    {
        infoProtocollo Adatta();
        string Token { get; }
        string Service { get; }
    }
}

