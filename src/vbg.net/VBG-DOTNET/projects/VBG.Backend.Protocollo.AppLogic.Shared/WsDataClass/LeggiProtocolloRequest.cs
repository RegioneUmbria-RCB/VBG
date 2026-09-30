using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Init.SIGePro.Protocollo.WsDataClass")]
    public class LeggiProtocolloRequest
    {
        [DataMember]
        public string Token { get; set; }
        [DataMember]
        public string Software { get; set; }
        [DataMember]
        public string CodiceComune { get; set; }
        [DataMember]
        public string IdProtocollo { get; set; }
        [DataMember]
        public string AnnoProtocollo { get; set; }
        [DataMember]
        public string NumeroProtocollo { get; set; }
        [DataMember]
        public DateTime? DallaData { get; set; }
        [DataMember]
        public DateTime? AllaData { get; set; }
        [DataMember]
        public string Classifica { get; set; }
        [DataMember]
        public string Flusso { get; set; }
        [DataMember]
        public string Oggetto { get; set; }
        [DataMember]
        public string Assegnatario { get; set; }
        [DataMember]
        public string Pagina { get; set; }
    }
}
