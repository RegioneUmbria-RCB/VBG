using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.DatiDinamici
{
    [DataContract]
    public class GetModelliDinamiciDaInterventoEEndoRequest
    {
        [DataMember]
        public int CodiceIntervento { get; set; }
        [DataMember]
        public List<int> ListaEndo { get; set; }
        [DataMember]
        public List<string> ListaTipiLocalizzazioni { get; set; }
        [DataMember]
        public bool IgnoraTipiLocalizzazione { get; set; }

        public GetModelliDinamiciDaInterventoEEndoRequest()
        {
            ListaEndo = new List<int>();
            ListaTipiLocalizzazioni = new List<string>();
        }
    }
}
