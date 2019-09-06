using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione
{
    public class ProtocollazioneGenerica : IProtocollazione
    {
        public ProtocollazioneGenerica()
        {

        }

        public string Codice
        {
            get { return ""; }
        }


        public string UrlUpdate
        {
            get { throw new NotImplementedException(); }
        }

        public string UrlError
        {
            get { throw new NotImplementedException(); }
        }
    }
}
