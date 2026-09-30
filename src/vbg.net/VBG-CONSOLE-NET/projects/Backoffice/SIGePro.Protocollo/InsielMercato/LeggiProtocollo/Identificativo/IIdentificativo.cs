using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;

namespace Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo.Identificativo
{
    public interface IRecordIdentifier
    {
        recordIdentifier GetRecordIdentifier();
        previous GetPrevious(direction flusso);
    }
}
