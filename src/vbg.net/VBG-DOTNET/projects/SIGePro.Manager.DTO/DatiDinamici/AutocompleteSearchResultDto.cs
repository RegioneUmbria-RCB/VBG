using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
    [DataContract]
    public class AutocompleteSearchResultDto
    {
        [DataMember]
        public RisultatoRicercaDatiDinamiciDto[] Risultati { get; set; } = new RisultatoRicercaDatiDinamiciDto[0];

        [DataMember]
        public int TotaleRisultatiTrovati { get; set; } = 0;
    }
}
