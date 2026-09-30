using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtocolloInsielService2;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.LeggiProtocollo
{
    public interface ILeggiProtoInputAdapter
    {
        DettagliProtocolloRequest Adatta();
    }
}
