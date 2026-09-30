using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Commissioni
{

    [DataContract]
    public class PraticaCommissioneBreveDto
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Uuid { get; set; }

        [DataMember]
        public int IdCommissioniR { get; set; }

        [DataMember]
        public string Comune { get; set; }
        [DataMember]
        public string NumeroData { get; set; }
        [DataMember]
        public string DatiProtocollo { get; set; }
        [DataMember]
        public string Richiedente { get; set; }
        [DataMember]
        public string Intervento { get; set; }
        [DataMember]
        public string DescrizioneLavori { get; set; }
        [DataMember]
        public int DocumentiSelezionati { get; set; } = 0;

        [DataMember]
        public string DescrizioneParere { get; set; }
        
        [DataMember]
        public string ParereEsteso { get; set; }
    }
}