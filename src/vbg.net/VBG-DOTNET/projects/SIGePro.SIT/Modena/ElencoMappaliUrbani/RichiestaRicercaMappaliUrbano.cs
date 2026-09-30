using Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Sit.Modena.ElencoMappaliUrbani
{
    [XmlRoot(Namespace = "http://elisa.it/aci", ElementName = "RichiestaRicercaMappaleUrbano", IsNullable = false)]
    public class RichiestaRicercaMappaleUrbanoType
    {
        [XmlElement("IdEnte")]
        public string IdEnte { get; set; }

        [XmlElement("IdentificativoParzialeUIU", Namespace = "http://www.sigmater.it/catasto")]
        public IdentificativoParzialeUIUType IdentificativoParzialeUIU { get; set; }
    }
}
