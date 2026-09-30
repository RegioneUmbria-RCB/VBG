using System;
using System.Xml.Serialization;
namespace Init.SIGePro.Protocollo.Data
{
    public class FascicoloXml
    {
        public int AnnoFascicolo { get; set; }
        public string NumeroFascicolo { get; set; }
        public string IdFascicolo { get; set; }
        public int AnnoProtDaFascicolare { get; set; }
        public string NumeroProtDaFascicolare { get; set; }
        public string IdProtDaFascicolare { get; set; }
        public int AnnoProtFascicolato { get; set; }
        public string NumeroProtFascicolato { get; set; }
        public string IdProtFascicolato { get; set; }
    }
}