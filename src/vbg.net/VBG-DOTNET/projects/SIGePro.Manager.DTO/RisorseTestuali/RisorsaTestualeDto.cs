using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.RisorseTestuali
{
    [DataContract]
    public class RisorsaTestualeDto
    {
        [DataMember]
        public string Chiave { get; set; }
        [DataMember]
        public string Valore { get; set; }
    }
}
