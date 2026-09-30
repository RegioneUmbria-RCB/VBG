using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    internal class CambiaFascicoloRequest
    {
        public int? Anno { get; internal set; }
        public int? Numero { get; internal set; }
        public string Classifica { get; internal set; }
        public int AnnoProtocollo { get; internal set; }
        public int NumeroProtocollo { get; internal set; }
        public int Livello { get; internal set; }
    }
}
