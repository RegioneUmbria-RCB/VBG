using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Anagrafiche
{
    [DataContract]
    public class AnagraficaCompattaDto
    {
        [DataMember]
        public int CodiceAnagrafe { get; set; } = -1;
        [DataMember]
        public string Nome { get; set; } = "";
        [DataMember]
        public string Nominativo { get; set; } = "";
        [DataMember]
        public string CodiceFiscale { get; set; } = "";
        [DataMember]
        public string PartitaIva { get; set; } = "";
        [DataMember]
        public bool UtenteTester { get; set; } = false;
    }
}
