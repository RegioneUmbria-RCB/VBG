using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;
using Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo;

namespace Init.SIGePro.Protocollo.InsielMercato.Protocollazione.ProtocolliCollegati
{
    public interface IProtocolliCollegati
    {
        previous[] GetProtocolliCollegati();
    }
}
