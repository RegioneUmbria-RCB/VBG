using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class DocumentoPraticaCommissioneDto
    {
        [DataMember]
        public string Categoria { get; set; }

        [DataMember]
        public string Descrizione { get; set; }

        [DataMember]
        public string NomeFile { get; set; }

        [DataMember]
        public int? CodiceOggetto { get; set; }

        [DataMember]
        public bool FirmatoDigitalmente { get; set; }

        [DataMember]
        public string Uid { get; set; }
    }
}