using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.TabelleDiBase
{
    [DataContract]
    public class SedeInailDto
    {

        [DataMember]
        public string Codice { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
    }
}
