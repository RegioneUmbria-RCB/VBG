using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.IntegrazioneLDP
{
    [DataContract]
    public class ConfigurazioneAlberoprocLDPItemDto
    {
        [DataMember]
        public string IdComune { get; set; }
        [DataMember]
        public int Id { get; set; }
        [DataMember]
        public string Contesto { get; set; }
        [DataMember]
        public string Codice { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
    }

    [DataContract]
    public class ConfigurazioneAlberoprocLDPDto
    {
        [DataMember]
        public ConfigurazioneAlberoprocLDPItemDto TipologiaOccupazione { get; set; }
        [DataMember]
        public ConfigurazioneAlberoprocLDPItemDto TipologiaPeriodo { get; set; }
        [DataMember]
        public ConfigurazioneAlberoprocLDPItemDto TipologiaGeometria { get; set; }
        [DataMember]
        public string LdpDolQString { get; set; }
    }
}
