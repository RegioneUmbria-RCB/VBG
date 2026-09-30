using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo;

namespace Init.SIGePro.Protocollo.InsielMercato2.Protocollazione.ProtocolliCollegati
{
    public interface IProtocolliCollegati
    {
        previous[] GetProtocolliCollegati();
    }
}
