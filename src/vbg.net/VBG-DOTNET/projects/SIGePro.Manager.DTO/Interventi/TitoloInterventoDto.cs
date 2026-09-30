using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Interventi
{
    [DataContract]
    public class TitoloInterventoDto
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Titolo { get; set; }

        [DataMember]
        public string Note { get; set; }
        [DataMember]
        public string UrlSchedaServizio { get; set; }
        [DataMember]
        public string NomeCategoria { get; set; }
        [DataMember]
        public string UrlCategoria { get; set; }
    }
}
