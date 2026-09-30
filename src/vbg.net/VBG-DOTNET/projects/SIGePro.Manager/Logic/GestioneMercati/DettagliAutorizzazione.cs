using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
    [DataContract]
    public class DettagliAutorizzazione
    {
        [DataMember]
        public int Codice { get; set; }
        [DataMember]
        public string Numero { get; set; }
        [DataMember]
        public DateTime Data { get; set; }
        [DataMember]
        public EnteAutorizzazione Ente { get; set; }
        [DataMember]
        public int NumeroPresenze { get; set; }
    }
}
