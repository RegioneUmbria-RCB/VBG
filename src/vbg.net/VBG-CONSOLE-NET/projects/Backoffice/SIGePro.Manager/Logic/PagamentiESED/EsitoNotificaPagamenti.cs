using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.PagamentiESED
{
    [Serializable]
    public class EsitoNotificaPagamenti
    {
        private class Constants
        {
            public const string OK = "OK";
            public const string KO = "KO";
        }

        [XmlElement(Order = 1)]
        public string Esito { get; private set; }

        [XmlElement(Order = 2)]
        public string Errore { get; private set; }

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
