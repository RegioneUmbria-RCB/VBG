using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ProtocollazioneMovimento : IProtocollazioneIstanzaMovimento
    {
        Movimenti _movimento;

        public ProtocollazioneMovimento(Movimenti movimento)
        {
            _movimento = movimento;
        }

        public string IdentificativoRichiesta
        {
            get { return String.Format("M-{0}-{1}-{2}", _movimento.IDCOMUNE, _movimento.CODICEMOVIMENTO, DateTime.Now.ToString("yyyyMMddhhmmss")); }
        }
    }
}
