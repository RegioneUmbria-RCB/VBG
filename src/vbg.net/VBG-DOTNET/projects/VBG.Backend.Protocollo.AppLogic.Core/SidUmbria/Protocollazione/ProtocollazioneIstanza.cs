using Init.SIGePro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class ProtocollazioneIstanza : IProtocollazioneIstanzaMovimento
    {
        private readonly IIstanzaDaProtocollare _istanza;

        public ProtocollazioneIstanza(IIstanzaDaProtocollare istanza)
        {
            this._istanza = istanza;
        }

        public string IdentificativoRichiesta
        {
            get { return String.Format("I-{0}-{1}-{2}", this._istanza.IDCOMUNE, this._istanza.CODICEISTANZA, DateTime.Now.ToString("yyyyMMddhhmmss")); }
        }
    }
}
