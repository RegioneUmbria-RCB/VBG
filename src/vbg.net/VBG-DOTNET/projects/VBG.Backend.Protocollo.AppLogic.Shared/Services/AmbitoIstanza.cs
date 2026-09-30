using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class AmbitoIstanza : IAmbito
    {
        IIstanzaDaProtocollare _istanza;
        public AmbitoIstanza(IIstanzaDaProtocollare istanza)
        {
            _istanza = istanza;
        }

        public DateTime? DataRegistrazione
        {
            get { return _istanza.DATA; }
        }
    }
}
