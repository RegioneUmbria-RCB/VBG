using VBG.Backend.Protocollo.AppLogic.Core.ProtocolloHalley2Service;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Protocollazione
{
    public class ProtocolloHalley2Response
    {
        public ProtocolloHalley2Response(NuovoProtocolloResponse response)
        {
            this.NumeroProtocollo = response.numero;
            this.CodErrore = response.coderrore;
            this.DesErrore = response.deserrore;
            this.Anno = response.anno;
        }

        public string NumeroProtocollo { get; private set; }
        public string CodErrore { get; private set; }
        public string DesErrore { get; private set; }
        public string Anno { get; private set; }
    }
}