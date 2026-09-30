using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione
{
    public class ProtocollazioneGenerica : IProtocollazionePaDoc
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
