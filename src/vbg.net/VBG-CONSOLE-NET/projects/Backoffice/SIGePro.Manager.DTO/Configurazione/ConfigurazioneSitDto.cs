using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    public class ConfigurazioneSitDto
    {
        [XmlElement(Order = 0)]
        public string UrlWsSit { get; set; }

        [XmlElement(Order = 1)]
        public bool Attivo { get; set; } = false;

        [XmlElement(Order = 2)]
        public bool ForzaStepLocalizzazioniSit { get; set; }

        [XmlElement(Order = 3)]
        public string AliasBackendLocale { get; set; }

        [XmlElement(Order = 4)]
        public string CodiceComune { get; set; }
    }
}
