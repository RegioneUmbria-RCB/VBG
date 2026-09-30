using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.TabelleDiBase
{
    [DataContract]
    public class FormaGiuridicaDto
    {
        [DataMember]
        public string CodiceFormaGiuridica { get; set; }
        [DataMember]
        public string FormaGiuridica { get; set; }
    }
}
