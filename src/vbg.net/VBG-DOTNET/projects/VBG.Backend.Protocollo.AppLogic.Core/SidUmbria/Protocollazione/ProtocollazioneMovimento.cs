using Init.SIGePro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class ProtocollazioneMovimento : IProtocollazioneIstanzaMovimento
    {
        private readonly IMovimentoDaProtocollare _movimento;

        public ProtocollazioneMovimento(IMovimentoDaProtocollare movimento)
        {
            this._movimento = movimento;
        }

        public string IdentificativoRichiesta
        {
            get { return String.Format("M-{0}-{1}-{2}", this._movimento.IDCOMUNE, this._movimento.CODICEMOVIMENTO, DateTime.Now.ToString("yyyyMMddhhmmss")); }
        }
    }
}
