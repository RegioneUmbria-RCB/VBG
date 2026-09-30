using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Visura
{
    [DataContract]
    public class StatoIstanzaDto
    {
        [DataMember]
        public string CodiceStato { get; set; }
        [DataMember]
        public string Stato { get; set; }
    }
}
