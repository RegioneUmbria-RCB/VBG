using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
    [DataContract]
    public class ListaAutorizzazioniItem
    {
        [DataMember]
        public int Codice { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
    }
}
