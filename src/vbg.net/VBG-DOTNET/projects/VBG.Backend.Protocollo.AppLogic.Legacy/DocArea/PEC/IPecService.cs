using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.PEC
{
    public interface IPecService
    {
        string InviaPec(string url, ProtocollazioneRet responseProtocollo);
    }
}
