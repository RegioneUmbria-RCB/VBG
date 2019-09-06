using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.PEC
{
    public interface IPecService
    {
        string InviaPec(string url, ProtocollazioneRet responseProtocollo);
    }
}
