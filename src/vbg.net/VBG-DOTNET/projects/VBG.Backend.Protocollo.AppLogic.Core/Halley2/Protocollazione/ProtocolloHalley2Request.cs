
using System.Collections.Generic;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Protocollazione
{
    public class ProtocolloHalley2Request
    {
        public byte[] Segnatura { get; set; }
        public List<Halle2FileRequest> Allegati { get; set; }

    }
}