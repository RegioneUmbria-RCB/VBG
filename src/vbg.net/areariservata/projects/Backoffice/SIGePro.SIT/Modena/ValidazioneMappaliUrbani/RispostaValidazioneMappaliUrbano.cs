using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani
{
    [XmlRoot(Namespace = "http://elisa.it/aci", ElementName = "RispostaValidazioneMappaliUrbano", IsNullable = false)]
    public class RispostaValidazioneMappaliUrbanoType
    {
        [XmlElement("RichiestaValidazioneMappaliUrbano", Order = 1)]
        public RichiestaValidazioneMappaliUrbanoType RichiestaValidazioneMappaliUrbano { get; set; }

        [XmlElement("MappaliUrbanoValidati", Order = 2)]
        public MappaliUrbanoValidatiType MappaliUrbanoValidati { get; set; }
    }

    [XmlRoot(ElementName = "MappaliUrbanoValidati", IsNullable = false)]
    public partial class MappaliUrbanoValidatiType
    {
        [XmlElement("MappaleurbanoValidato", Order = 1)]
        public MappaleUrbanoValidatoType MappaleUrbanoValidato { get; set; }
    }

    public partial class MappaleUrbanoValidatoType
    {
        [XmlAttribute(AttributeName = "Valido")]
        public bool Valido { get; set; }

        [XmlElement("IdentificativoParzialeUIU", Order = 1, Namespace = "http://www.sigmater.it/catasto")]
        public IdentificativoParzialeUIUType IdentificativoParzialeUIU { get; set; }

    }
}
