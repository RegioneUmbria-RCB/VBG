using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.StradarioComune
{
    [DataContract]
    public class ColoreStradarioDto
    {
        [DataMember]
        public string CodiceColore { get; set; }

        [DataMember]
        public string Colore { get; set; }
    }
}
