using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Civilia.Protocollazione
{
    /// <summary>
    /// NON VA BENE, VEDI MEGLIO DOCUMENTAZIONE
    /// </summary>

    public class ProtocolloResult
    {
        public int ResultType { get; set; }
        public string ResultDescription { get; set; }
        public Result Result { get; set; }
        public int TotalCount { get; set; }

        public ProtocolloResult()
        {

        }
    }
}
