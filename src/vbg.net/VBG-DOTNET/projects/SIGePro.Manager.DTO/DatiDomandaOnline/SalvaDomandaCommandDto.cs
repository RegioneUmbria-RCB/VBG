using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.DatiDomandaOnline
{
    [DataContract]
    public class SalvaDomandaCommandDto
    {
        [DataMember]
        public string Software { get; set; } = "";
        [DataMember]
        public int IdDomanda { get; set; }
        [DataMember]
        public int CodiceAnagrafe { get; set; }
        [DataMember]
        public byte[] DatiDomanda { get; set; } = Array.Empty<byte>();
        [DataMember]
        public string IdentificativoDomanda { get; set; } = "";
        [DataMember]
        public bool FlagTrasferita { get; set; }
        [DataMember]
        public bool FlagPresentata { get; set; }
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
        public bool AggiornaDataUltimaModifica { get; set; } = true;
        [DataMember]
        public string Provenienza { get; set; } = "";
    }
}
