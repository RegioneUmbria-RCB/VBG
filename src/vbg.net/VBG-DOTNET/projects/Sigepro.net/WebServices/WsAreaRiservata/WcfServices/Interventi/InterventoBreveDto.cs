using System.Runtime.Serialization;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Interventi
{

    [DataContract]
    public class InterventoBreveDto
    {
        [DataMember]
        public int Codice { get; set; }

        [DataMember]
        public string Descrizione { get; set; }
    }
}