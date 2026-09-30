using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione
{
    public interface IJIrideRequestBuilder<T>
    {
        T Build();
    }
}
