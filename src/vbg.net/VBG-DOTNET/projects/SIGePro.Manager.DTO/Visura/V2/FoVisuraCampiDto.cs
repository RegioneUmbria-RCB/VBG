using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Visura.V2
{
    [DataContract]
    public class FoVisuraCampiDto
    {
        [DataMember]
        public string Fkidcampo { get; set; } = "";
    }
}
