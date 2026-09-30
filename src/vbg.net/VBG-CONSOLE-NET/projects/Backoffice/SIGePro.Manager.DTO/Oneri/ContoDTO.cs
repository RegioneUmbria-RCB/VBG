
using System;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Oneri
{
    [Serializable]
    [DataContract]
    public class ContoDto
    {
        [XmlElement(Order = 10)]
        [DataMember]
        public int Id { get; set; }

        [XmlElement(Order = 20)]
        [DataMember]
        public string CodiceSottoConto { get; set; }

        [XmlElement(Order = 30)]
        [DataMember]
        public string NumeroAccertamento { get; set; }

        [XmlElement(Order = 40)]
        [DataMember]
        public string Conto { get; set; }

        [XmlElement(Order = 50)]
        [DataMember]
        public int? Iva { get; set; }

        [XmlElement(Order = 60)]
        [DataMember]
        public string CodiceMappaturaNodoPagamenti { get; set; }
    }
}
