using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class ValoreDatoDinamicoMovimentoDto
    {
        [DataMember]
        public int Id { get; set; }
        [DataMember]
        public int Indice { get; set; }
        [DataMember]
        public string Valore { get; set; }
        [DataMember]
        public string ValoreDecodificato { get; set; }
    }
}