using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
    [DataContract]
    public class RisultatoRicercaDatiDinamiciDto
    {
        [DataMember]
        public string Value { get; set; }
        [DataMember]
        public string Label { get; set; }
    }
}
