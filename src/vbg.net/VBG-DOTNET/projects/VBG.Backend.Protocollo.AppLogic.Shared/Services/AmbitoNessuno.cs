using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class AmbitoNessuno : IAmbito
    {
        public DateTime? DataRegistrazione
        {
            get { return DateTime.Now; }
        }
    }
}
