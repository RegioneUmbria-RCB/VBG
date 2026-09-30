using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Comuni
{
    [DataContract]
    public class DatiComuneCompatto
    {
        [DataMember]
        public string CodiceComune { get; set; }
        [DataMember]
        public string Comune { get; set; }
        [DataMember]
        public string SiglaProvincia { get; set; }
        [DataMember]
        public string Cf { get; set; }
        [DataMember]
        public string Provincia { get; set; }

        [DataMember]
        public string CodiceISTAT { get; set; }
    }
}
