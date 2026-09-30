using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Interventi
{
    [DataContract]
    public class NodoConWorkflowDto
    {
        [DataMember]
        public int Id { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
        [DataMember]
        public int CodiceOggettoWorkflow { get; set; }
    }
}
