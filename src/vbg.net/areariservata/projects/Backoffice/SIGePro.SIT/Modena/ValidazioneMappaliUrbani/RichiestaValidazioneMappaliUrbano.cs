using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani
{
    [XmlRoot(Namespace = "http://elisa.it/aci", ElementName = "RichiestaValidazioneMappaliUrbano", IsNullable = false)]
    public partial class RichiestaValidazioneMappaliUrbanoType
    {
        [XmlElement("IdEnte", Order = 1)]
        public string IdEnte { get; set; }

        [XmlElement("IdUIUDaValidare", Order = 2)]
        public IdUIUDaValidareType IdUIUDaValidare { get; set; }

        [XmlElement("DataRiferimento", Order = 3)]
        public string DataRiferimento { get; set; }
    }

    public partial class IdUIUDaValidareType
    {
        [XmlElement("IdentificativoUIU", Order = 1, Namespace = "http://www.sigmater.it/catasto")]
        public IdentificativoUIUType IdentificativoUIU { get; set; }
    }

    public partial class IdentificativoUIUType
    {
        [XmlElement("IdentificativoComune", Order = 1, Namespace = "http://www.sigmater.it/ambitiamministrativi")]
        public IdentificativoComuneType IdentificativoComune { get; set; }

        [XmlElement("IdentificativoParzialeUIU", Order = 2)]
        public IdentificativoParzialeUIUType IdentificativoParzialeUIU { get; set; }
    }

    public partial class IdentificativoComuneType
    {
        [XmlElement("CodiceBelfioreComune", Order = 1)]
        public string CodiceBelfioreComune { get; set; }
    }

    public partial class IdentificativoParzialeUIUType
    {
        [XmlElement("Foglio", Order = 1)]
        public string Foglio { get; set; }

        [XmlElement("Mappale", Order = 2)]
        public string Mappale { get; set; }

        [XmlElement("Subalterno", Order = 3)]
        public string Subalterno { get; set; }
    }
}
