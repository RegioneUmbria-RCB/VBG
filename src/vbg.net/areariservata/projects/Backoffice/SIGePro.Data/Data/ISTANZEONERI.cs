using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    public partial class IstanzeOneri
    {
        [XmlIgnore]
        public decimal? QuotaCapitale { get; set; }

        [XmlIgnore]
        public decimal? CapitaleResiduo { get; set; }
    }
}
