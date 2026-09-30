using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Fascicolazione.Lettura
{
    public class LeggiFascicoloRequest
    {
        public LeggiFascicoloWSRequest Request { get; internal set; }
        public string Url { get; internal set; }
        public string Aoo { get; internal set; }
        public string CodiceAmministrazione { get; internal set; }
    }
}
