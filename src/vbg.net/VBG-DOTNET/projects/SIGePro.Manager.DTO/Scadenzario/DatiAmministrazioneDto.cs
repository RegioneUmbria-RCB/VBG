using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class DatiAmministrazioneDto
    {
        [DataMember]
        public string PartitaIva { get; set; }
    }
}