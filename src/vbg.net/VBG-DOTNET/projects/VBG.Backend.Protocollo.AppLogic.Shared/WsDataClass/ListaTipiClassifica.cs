using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Name = "ListaTipiClassificaType", Namespace = "http://it.gruppoinit/Protocollazione")]
    public class ListaTipiClassificaType
    {
        public ListaTipiClassificaType(Exception ex)
        {
            this.Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaTipiClassificaType(string messaggio, string stackTrace)
        {
            this.Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public ListaTipiClassificaType()
        {

        }

        [DataMember(Order = 0)]
        public ListaTipiClassificaClassifica[] Classifica { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public ErroreProtocolloType Errore { get; set; }
    }
}
