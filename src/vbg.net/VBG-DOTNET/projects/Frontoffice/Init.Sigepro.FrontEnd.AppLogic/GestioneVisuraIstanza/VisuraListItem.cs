using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.SIGePro.Manager.DTO.Visura;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public class VisuraListItem
    {
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
        public string PosizioneArchivio { get; protected set; }

        public VisuraListItem(VisuraListItemV3 x)
        {
            this.CodiceIstanza = x.CodiceIstanza;
            this.Uuid = x.Uuid;
            this.Oggetto = x.Oggetto;
            this.Progressivo = x.Progressivo;
            this.TipoIntervento = x.TipoIntervento;
            this.NumeroProtocollo = x.NumeroProtocollo;
            this.Operatore = x.Operatore;
            this.CodiceArea = x.CodiceArea;
            this.Civico = x.Civico;
            this.Stato = x.Stato;
            this.DataPresentazione = x.DataPresentazione;
            this.Subalterno = x.Subalterno;
            this.NumeroIstanza = x.NumeroIstanza;
            this.TipoProcedura = x.TipoProcedura;
            this.LocalizzazioneConCivico = x.LocalizzazioneConCivico;
            this.DataProtocollo = x.DataProtocollo;
            this.Particella = x.Particella;
            this.Software = x.Software;
            this.Richiedente = x.Richiedente;
            this.Foglio = x.Foglio;
            this.TipoCatasto = x.TipoCatasto;
            this.Azienda = x.Azienda;
            this.PosizioneArchivio = x.PosizioneArchivio;
        }


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
            this.PosizioneArchivio = "";
        }
    }
}
