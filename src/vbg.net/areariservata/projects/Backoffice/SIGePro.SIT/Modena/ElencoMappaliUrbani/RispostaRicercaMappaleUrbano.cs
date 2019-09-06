using Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Sit.Modena.ElencoMappaliUrbani
{
    [XmlRoot(Namespace = "http://elisa.it/aci", ElementName = "RispostaRicercaMappaleUrbano", IsNullable = false)]
    public class RispostaRicercaMappaleUrbanoType
    {
        [XmlElement("RichiestaRicercaMappaleUrbano", Order = 1)]
        public RichiestaRicercaMappaleUrbanoType RichiestaRicercaMappaleUrbano { get; set; }

        [XmlElement("S3RispostaConsultUIU", Order = 2, Namespace = "http://www.sigmater.it/catasto")]
        public S3RispostaConsultUIUType S3RispostaConsultUIU { get; set; }

        [XmlElement("ErroreRicercaMappaleUrbano", Order = 3)]
        public ErroreRicercaMappaleUrbanoType ErroreRicercaMappaleUrbano { get; set; }
    }

    public class ErroreRicercaMappaleUrbanoType
    {
        [XmlElement("RichiestaRicercaMappaleUrbano", Order = 1)]
        public RichiestaRicercaMappaleUrbanoType RichiestaRicercaMappaleUrbano { get; set; }
    }

    public class S3RispostaConsultUIUType
    {
        [XmlElement("UIURicercata", Namespace = "http://www.sigmater.it/catasto")]
        public UIURicercataType[] UIURicercata { get; set; }
    }

    public class UIURicercataType
    {
        [XmlElement("IdentificativoParzialeUIU", Namespace = "http://www.sigmater.it/catasto")]
        public IdentificativoParzialeUIUType IdentificativoParzialeUIU { get; set; }

        [XmlElement("IndirizziCatastaliUIU", Namespace = "http://www.sigmater.it/catasto")]
        public IndirizziCatastaliUIUType IndirizziCatastaliUIU { get; set; }
    }

    public class IndirizziCatastaliUIUType
    {
        public IndirizzoUIUType IndirizzoUIU { get; set; }
    }

    public class IndirizzoUIUType
    {
        public string Indirizzo { get; set; }

        public string Civico { get; set; }
    }

    public class StradaType
    {
        public string CodStrada { get; set; }

        public string Dizione { get; set; }
    }
}

