using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.JProtocollo2.Protocollazione
{
    public interface IProtocollazioneJProtocollo2
    {
        DatiProtocolloRes Protocolla();
    }
}
