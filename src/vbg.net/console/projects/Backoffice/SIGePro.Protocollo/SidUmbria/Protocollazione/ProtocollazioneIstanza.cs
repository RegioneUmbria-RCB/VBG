using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ProtocollazioneIstanza : IProtocollazioneIstanzaMovimento
    {
        Istanze _istanza;

        public ProtocollazioneIstanza(Istanze istanza)
        {
            _istanza = istanza;
        }

        public string IdentificativoRichiesta
        {
            get { return String.Format("I-{0}-{1}-{2}", _istanza.IDCOMUNE, _istanza.CODICEISTANZA, DateTime.Now.ToString("yyyyMMddhhmmss")); }
        }
    }
}
