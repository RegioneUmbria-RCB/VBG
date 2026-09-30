using System.Xml.Serialization;

namespace VBG.Backend.SIT.AppLogic.Modena.ElencoMappaliUrbani
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
