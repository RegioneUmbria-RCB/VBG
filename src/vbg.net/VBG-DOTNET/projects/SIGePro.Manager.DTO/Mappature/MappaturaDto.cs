using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Mappature
{
    [DataContract]
    public class MappaturaDto
    {
        [DataMember]
        public int IdCampo { get; set; }
        [DataMember]
        public string NomeCampo { get; set; }
        [DataMember]
        public string Espressione { get; set; }
    }
}
