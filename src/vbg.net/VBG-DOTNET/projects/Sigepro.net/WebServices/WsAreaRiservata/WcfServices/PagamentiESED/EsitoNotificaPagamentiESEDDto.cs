using System.Runtime.Serialization;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.PagamentiESED
{
    [DataContract]
    public class EsitoNotificaPagamentiESEDDto
    {
        [DataMember]
        public string Esito { get; set; }
        [DataMember]
        public string Errore { get; set; }
    }
}
