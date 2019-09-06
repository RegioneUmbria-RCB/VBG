using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Oneri
{
    [Serializable]
    public class OnereDto : CausaleOnereDto
    {
        [XmlElement(Order = 1)]
        public float Importo { get; set; }
        [XmlElement(Order = 2)]
        public string OrigineOnere { get; set; }
        [XmlElement(Order = 3)]
        public int CodiceInterventoOEndoOrigine { get; set; }
        [XmlElement(Order = 4)]
        public string InterventoOEndoOrigine { get; set; }
        [XmlElement(Order = 5)]
        public string Note { get; set; }
        [XmlElement(Order = 6)]
        public int? CampoDinamico { get; set; }

        public OnereDto()
        {

        }
    }
}
