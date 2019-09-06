using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public class VisuraListItem
{
        private VisuraListItemDto x;

        public int CodiceIstanza { get; protected set; }
        public string Uuid { get; protected set; }
        public string Oggetto { get; protected set; }
        public string Progressivo { get; protected set; }
        public string TipoIntervento { get; protected set; }
        public string NumeroProtocollo { get; protected set; }
        public string Operatore { get; protected set; }
        public string CodiceArea { get; protected set; }
        public string Civico { get; protected set; }
        public string Stato { get; protected set; }
        public DateTime DataPresentazione { get; protected set; }
        public string Subalterno { get; protected set; }
        public string NumeroIstanza { get; protected set; }
        public string TipoProcedura { get; protected set; }
        public string LocalizzazioneConCivico { get; protected set; }
        public DateTime? DataProtocollo { get; protected set; }
        public string Particella { get; protected set; }
        public string Software { get; protected set; }
        public string Richiedente { get; protected set; }
        public string Foglio { get; protected set; }
        public string TipoCatasto { get; protected set; }
        public string Azienda { get; protected set; }

        public VisuraListItem(VisuraListItemDto x)
        {
            this.CodiceIstanza = x.Idpratica;
            this.Uuid = x.Uuid;
            this.Oggetto = x.Oggetto;
            //this.Progressivo = 
            this.TipoIntervento = x.Descrizioneintervento;
            this.NumeroProtocollo = x.Numeroprotocollo;
            this.Operatore = x.Operatore;
            this.CodiceArea = x.Zonizzazione;
            this.Civico = x.Pr_civico;
            this.Stato = x.Statopratica;
            this.DataPresentazione = x.Datapresentazione;
            this.Subalterno = x.Sub;
            this.NumeroIstanza = x.Numeropratica;
            this.LocalizzazioneConCivico = x.Pr_indirizzo + " " + x.Pr_civico;
            this.DataProtocollo = x.Dataprotocollo;
            this.Particella = x.Particella;
            this.Software = x.Software;
            this.Richiedente = x.Nominativo;
            this.Foglio = x.Foglio;
            this.TipoCatasto = x.Tipocatasto;
            this.Azienda = x.Az_nominativo;
        }
    }
}
