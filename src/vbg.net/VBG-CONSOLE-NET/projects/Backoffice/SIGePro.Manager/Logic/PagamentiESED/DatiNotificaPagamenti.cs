using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.PagamentiESED
{
    [Serializable]
    public class DatiNotificaPagamenti
    {
        [XmlElement(Order = 1)]
        public string IdDomanda { get; private set; }

        [XmlElement(Order = 2)]
        public string Messaggio { get; private set; }

        [XmlElement(Order = 3)]
        public string Esito { get; private set; }

        [XmlElement(Order = 4)]
        public string Data { get; private set; }

        [XmlElement(Order = 5)]
        public string IdComune { get; private set; }

        [XmlElement(Order = 6)]
        public string NumeroOperazione { get; private set; }

        [XmlElement(Order = 7)]
        public string IdOrdine { get; private set; }

        [XmlElement(Order = 8)]
        public string IdTransazione { get; private set; }

        [XmlElement(Order = 9)]
        public string TipoPagamento { get; private set; }

        [XmlElement(Order = 10)]
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
