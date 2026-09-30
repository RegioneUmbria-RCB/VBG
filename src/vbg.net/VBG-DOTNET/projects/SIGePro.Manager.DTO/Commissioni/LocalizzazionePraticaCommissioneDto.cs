using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class LocalizzazionePraticaCommissioneDto
    {
        [DataMember]
        public string Toponimo { get; set; }
        
        [DataMember]
        public string Mappali { get; set; }
    }
}