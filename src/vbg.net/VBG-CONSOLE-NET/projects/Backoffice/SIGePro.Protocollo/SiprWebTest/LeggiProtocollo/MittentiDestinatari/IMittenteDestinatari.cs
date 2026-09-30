using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.MittentiDestinatari
{
    public interface IMittenteDestinatari
    {
        string InCaricoADescrizione { get; }
        MittDestOut[] MittentiDestintari { get; }
    }
}
