using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
    [DataContract]
    public class EnteAutorizzazione
    {
        [DataMember]
        public string Codice { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
    }
}
