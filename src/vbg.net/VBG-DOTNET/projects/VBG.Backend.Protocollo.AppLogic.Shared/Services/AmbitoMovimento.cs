using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class AmbitoMovimento : IAmbito
    {
        private readonly IMovimentoDaProtocollare _movimento;

        public AmbitoMovimento(IMovimentoDaProtocollare movimento)
        {
            this._movimento = movimento;
        }

        public DateTime? DataRegistrazione
        {
            get { return this._movimento.DATA; }
        }
    }
}
