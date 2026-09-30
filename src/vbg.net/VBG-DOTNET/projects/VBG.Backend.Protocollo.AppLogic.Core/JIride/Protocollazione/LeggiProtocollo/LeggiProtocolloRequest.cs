using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo
{
    public class LeggiProtocolloRequest
    {
        public int? IdProtocollo { get; internal set; }
        public string IdProtocolloVBG { get; internal set; }
        public bool IsCopia { get; internal set; }
        public string Url { get; internal set; }
        public string AOO { get; internal set; }
        public string CodiceAmministrazione { get; internal set; }
        public short? AnnoProtocollo { get; internal set; }
        public int? NumeroProtocollo { get; internal set; }
        public bool UsaNumeroAnnoPerLettura { get; internal set; }
        public string NumeroProtocolloOriginale { get; internal set; }
        public string Operatore { get; internal set; }
        public string Ruolo { get; internal set; }
        public string UrlPec { get; internal set; }
        public bool VisualizzaRicevutePec { get; internal set; }

        public bool UsaLeggiDocumentoPlus { get; internal set; } = false;
    }
}
