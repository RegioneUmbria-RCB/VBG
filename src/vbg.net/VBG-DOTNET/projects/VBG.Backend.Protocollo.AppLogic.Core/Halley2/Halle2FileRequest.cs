using VBG.Backend.Protocollo.AppLogic.Core.ProtocolloHalley2Service;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2
{
    public class Halle2FileRequest
    {
        public bool Principale { get; set; }

        public FileType File { get; set; }
    }
}