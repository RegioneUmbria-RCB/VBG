using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.ComponentModel;

namespace Init.SIGePro.Protocollo.Sigedo
{
    [XmlRoot(ElementName = "RESPONSE")]
    public class SigedoSegnaturaUo
    {
        public SigedoSegnaturaUo()
        {
            this.Persone = new List<PersonaUo>();
        }

        [XmlArray(ElementName = "LISTA_PERSONE")]
        [XmlArrayItem(ElementName = "PERSONA")]
        public List<PersonaUo> Persone { get; set; }

    }

    public class PersonaUo
    {
        [XmlElement(ElementName = "MATRICOLA")]
        public string Matricola { get; set; }
        
        [XmlElement(ElementName = "NOME")]
        public string Nome { get; set; }

        [XmlElement(ElementName = "COGNOME")]
        public string Cognome { get; set; }
        
        [XmlElement(ElementName = "CODICE_FISCALE")]
        public string CodiceFiscale { get; set; }

        [XmlElement(ElementName = "DATA_NASCITA")]
        public string DataNascita { get; set; }

        [XmlElement(ElementName = "CODICE_UNITA_ORGANIZZATIVE")]
        public string CodiceUnitaOrganizzativa { get; set; }

        [XmlElement(ElementName = "DESCR_UNITA_ORGANIZZATIVE")]
        public string DescrizioneUnitaOrganizzativa { get; set; }

        [XmlElement(ElementName = "CODICE_UNITA_1LIVELLO")]
        public string CodiceUnitaLivello1 { get; set; }

        [XmlElement(ElementName = "DESCR_UNITA_1LIVELLO")]
        public string DescrizioneUnitaLivello1 { get; set; }
    }
}
