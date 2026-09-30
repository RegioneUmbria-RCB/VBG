using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public interface IJIrideDocInRequestBuilder<T>
    {
        T Build();
    }
}
