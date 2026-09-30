using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class AmbitoPec : IAmbito
    {
        PecInbox _pec;

        public AmbitoPec(PecInbox pec)
        {
            _pec = pec;
        }

        public DateTime? DataRegistrazione
        {
            get { return _pec.PecDate; }
        }
    }
}
