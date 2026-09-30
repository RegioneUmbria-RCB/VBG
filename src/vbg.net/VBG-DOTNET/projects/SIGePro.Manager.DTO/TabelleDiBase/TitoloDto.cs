using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.TabelleDiBase
{
    [DataContract]
    public class TitoloDto
    {
        [DataMember]
        public string CodiceTitolo { get; set; }
        [DataMember]
        public string Titolo { get; set; }
    }
}
