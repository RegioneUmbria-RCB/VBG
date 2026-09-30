using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.TabelleDiBase
{
    [DataContract]
    public class ElencoProfessionaleDto
    {
        [DataMember]
        public int? EpId { get; set; }
        [DataMember]
        public string EpDescrizione { get; set; }
        [DataMember]
        public int? EpRegionale { get; set; }
    }
}
