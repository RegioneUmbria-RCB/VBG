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
    public class DatiNotificaPagamenti
    {
        [XmlElement(Order = 1)]
        [DataMember]
        public string IdDomanda { get; set; }

        [XmlElement(Order = 2)]
        [DataMember]
        public string Messaggio { get; set; }

        [XmlElement(Order = 3)]
        [DataMember]
        public string Esito { get; set; }

        [XmlElement(Order = 4)]
        [DataMember]
        public string Data { get; set; }

        [XmlElement(Order = 5)]
        [DataMember]
        public string IdComune { get; set; }

        [XmlElement(Order = 6)]
        [DataMember]
        public string NumeroOperazione { get; set; }

        [XmlElement(Order = 7)]
        [DataMember]
        public string IdOrdine { get; set; }

        [XmlElement(Order = 8)]
        [DataMember]
        public string IdTransazione { get; set; }

        [XmlElement(Order = 9)]
        [DataMember]
        public string TipoPagamento { get; set; }

        [XmlElement(Order = 10)]
        [DataMember]
        public string Errore { get; set; }

        public DatiNotificaPagamenti()
        {

        }
        
        public DatiNotificaPagamenti(string idDomanda, string messaggio, string esito, string data, string idComune, string numeroOperazione, string idOrdine, string idTransazione, string tipoPagamento)
        {
            this.IdDomanda = IdDomanda;
            this.Messaggio = messaggio;
            this.Esito = esito;
            this.Data = data;
            this.IdComune = idComune;
            this.NumeroOperazione = numeroOperazione;
            this.IdOrdine = idOrdine;
            this.IdTransazione = idTransazione;
            this.TipoPagamento = tipoPagamento;
        }

        public DatiNotificaPagamenti(string errore)
        {
            this.Esito = "KO";
            this.Errore = errore;
        }
    }
}
