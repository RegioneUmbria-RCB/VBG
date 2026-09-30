using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazioneInterna : IProtocollazione
    {
        public ProtocollazioneInterna()
        {

        }

        public string Flusso => "I";

        public DestinatariType GetDestinatari()
        {
            throw new NotImplementedException();
        }

        public MittenteType[] GetMittenti()
        {
            throw new NotImplementedException();
        }
    }
}
