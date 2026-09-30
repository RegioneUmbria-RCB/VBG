using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ProtocollazioneDefault : IProtocollazioneIstanzaMovimento
    {
        public ProtocollazioneDefault()
        {

        }

        public string IdentificativoRichiesta
        {
            get { return ""; }
        }
    }
}
