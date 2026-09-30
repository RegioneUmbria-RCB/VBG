using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo.Identificativo
{
    public interface IRecordIdentifier
    {
        recordIdentifier GetRecordIdentifier();
        previous GetPrevious(direction flusso);
    }
}
