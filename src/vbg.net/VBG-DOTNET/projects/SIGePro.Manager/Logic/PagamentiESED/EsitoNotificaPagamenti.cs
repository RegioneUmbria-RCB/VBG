using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.PagamentiESED
{
    [Serializable]
    [DataContract]
    public class EsitoNotificaPagamenti
    {
        private class Constants
        {
            public const string OK = "OK";
            public const string KO = "KO";
        }
        

        [XmlElement(Order = 1)]
        [DataMember]
        public string Esito { get; set; }

        [XmlElement(Order = 2)]
        [DataMember]
        public string Errore { get; set; }

        public EsitoNotificaPagamenti()
        {
            this.Esito = Constants.OK;
            this.Errore = "";
        }

        public EsitoNotificaPagamenti(string errore)
        {
            this.Esito = Constants.KO;
            this.Errore = errore;
        }
    }
}
