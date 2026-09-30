using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.DatiDinamici.OnceOnly
{
    [DataContract]
    public class FonteDatiOnceOnlyDto
    {
        [DataMember]
        public int IdCampo { get; set; }
        [DataMember]
        public string NomeCampo { get; set; } = "";
        [DataMember]
        public bool IsUpload { get; set; } = false;
        [DataMember]
        public string FonteInterna { get; set; } = "";
        [DataMember]
        public string FonteEsterna { get; set; } = "";
    }
}
