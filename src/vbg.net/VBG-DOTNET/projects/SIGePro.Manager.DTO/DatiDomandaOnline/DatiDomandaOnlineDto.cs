using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.DatiDomandaOnline
{
    [DataContract]
    public class DatiDomandaOnlineDto
    {
        [DataMember]
        public int Id { get; set; }
        //[DataMember]
        //public string CodiceFiscaleRichiedente { get; set; } = "";
        [DataMember]
        public int? CodiceOggetto { get; set; }
        [DataMember]
        public System.DateTime? DataUltimaModifica { get; set; }
        [DataMember]
        public string IdentificativoDomanda { get; set; } = "";
        [DataMember]
        public string Richiedente { get; set; } = "";
        [DataMember]
        public string Intervento { get; set; } = "";
        [DataMember]
        public int? CodiceIntervento { get; set; }
        [DataMember]
        public string Oggetto { get; set; } = "";
        [DataMember]
        public bool PagamentoAvviato { get; set; }
        [DataMember]
        public bool PagamentoCompletato { get; set; }
        [DataMember]
        public string Bookmark { get; set; } = "";
        [DataMember]
        public bool UpgradeCompleto { get; set; } = false;
    }
}
