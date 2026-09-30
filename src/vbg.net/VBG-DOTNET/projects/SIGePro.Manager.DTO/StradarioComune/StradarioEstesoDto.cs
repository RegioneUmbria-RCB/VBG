using Init.SIGePro.Manager.DTO.Comuni;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.StradarioComune
{
    [DataContract]
    public class StradarioEstesoDto
    {
        [DataMember]
        public int CodiceStradario { get; set; }
        [DataMember]
        public string IdComune { get; set; }
        [DataMember]
        public string Prefisso { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
        [DataMember]
        public string Cap { get; set; }
        [DataMember]
        public string LocFraz { get; set; }
        [DataMember]
        public string CodViario { get; set; }
        [DataMember]
        public DatiComuneCompatto ComuneLocalizzazione { get; set; }
    }
}
