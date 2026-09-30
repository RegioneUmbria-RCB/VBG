using ProtocolloInsielMercatoService2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo.Identificativo
{
    public interface IRecordIdentifier
    {
        recordIdentifier GetRecordIdentifier();
        previous GetPrevious(direction flusso);
    }
}
