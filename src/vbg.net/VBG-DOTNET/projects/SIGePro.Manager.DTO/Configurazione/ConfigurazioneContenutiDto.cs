using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [DataContract]
    public class CodiceAccreditamentoDto
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public string NomeComune { get; set; }

        [DataMember]
        [XmlElement(Order = 1)]
        public string CodiceAccreditamento { get; set; }
    }

    [DataContract]
    public class ConfigurazioneContenutiDto
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public string NomeRegione { get; set; }

        [DataMember]
        [XmlElement(Order = 1)]
        public string NomeComune { get; set; }

        [DataMember]
        [XmlElement(Order = 2)]
        public string NomeComuneSottotitolo { get; set; }

        [DataMember]
        [XmlElement(Order = 3)]
        public string ResponsabileSportello { get; set; }

        [DataMember]
        [XmlElement(Order = 4)]
        public string IndirizzoPec { get; set; }

        [DataMember]
        [XmlElement(Order = 5)]
        public string IndirizzoEmail { get; set; }

        [DataMember]
        [XmlElement(Order = 6)]
        public string Telefono { get; set; }

        [DataMember]
        [XmlElement(Order = 7)]
        public int? CodiceOggettoLogo { get; set; }

        [DataMember]
        [XmlElement(Order = 8)]
        public bool AreaRiservataAttiva { get; set; }

        [DataMember]
        [XmlElement(Order = 9)]
        public CodiceAccreditamentoDto[] ListaCodiciAccreditamento { get; set; }
    }
}
