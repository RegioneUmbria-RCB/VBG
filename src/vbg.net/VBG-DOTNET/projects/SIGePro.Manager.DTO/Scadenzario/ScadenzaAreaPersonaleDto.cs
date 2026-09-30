using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class ScadenzaAreaPersonaleDto
    {
        [DataMember]
        public ScadenzaDto Scadenza { get; set; }
        [DataMember]
        public DatiMovimentoDaEffettuareDto MovimentoDiOrigine { get; set; }
        [DataMember]
        public ConfigurazioneMovimentoDaEffettuareDto Configurazione { get; set; }
        [DataMember]
        public string UuidMovimentoDaFare { get; set; }
    }
}
