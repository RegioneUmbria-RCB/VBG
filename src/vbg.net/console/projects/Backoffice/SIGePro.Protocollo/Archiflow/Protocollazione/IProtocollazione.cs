using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Archiflow.Protocollazione
{
    public interface IProtocollazione
    {
        DatiProtocolloRes Protocolla();
        Guid GuidCardProtocollo { get; }
    }
}
