using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Visura.V1
{
    [DataContract]
    public class CampoVisuraFrontofficeDto
    {
        [DataMember]
        public string Etichetta { get; set; }
        [DataMember]
        public int Codice { get; set; }
        [DataMember]
        public string Valore { get; set; }
        [DataMember]
        public string IdRisorsa { get; set; }
    }
}
