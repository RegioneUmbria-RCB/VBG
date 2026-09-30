
namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine
{
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
    using System;
    using System.Collections.Generic;


    /// <summary>
    /// Movimento che ha come contromovimento il movimento da effettuare nel frontoffice
    /// </summary>
    public class MovimentoDiOrigine
    {
        public RiferimentiIstanza DatiIstanza { get; set; }
        public int IdMovimento { get; set; }
        public string NomeAttivita { get; set; }
        public DateTime? DataAttivita { get; set; }
        public DatiProtocolloMovimento Protocollo { get; set; }
        public string Procedimento { get; set; }
        public string CodiceProcedimento { get; set; }
        public string Amministrazione { get; set; }
        public string Esito { get; set; }
        public string Oggetto { get; set; }
        public string Note { get; set; }

        public List<DatiAllegatoMovimento> Allegati { get; set; }
        public List<SchedaDinamicaMovimento> SchedeDinamiche { get; set; }

        public bool Pubblica { get; set; }
        public bool PubblicaOggetto { get; set; }
        public bool PubblicaEsito { get; set; }
        public bool PubblicaSchede { get; set; }

        public bool HaProcedimento { get { return !String.IsNullOrEmpty(this.Procedimento); } }
        public bool HaAmministrazione { get { return !String.IsNullOrEmpty(this.Amministrazione); } }

        public string Software { get; set; }

        // List<DocumentoSostituibile> _documentiSostituibili;
        // public IEnumerable<DocumentoSostituibile> DocumentiSostituibili { get { return this._documentiSostituibili; } }



        public MovimentoDiOrigine()
        {
            this.Allegati = new List<DatiAllegatoMovimento>();
            this.SchedeDinamiche = new List<SchedaDinamicaMovimento>();
            //this._documentiSostituibili = new List<DocumentoSostituibile>();
        }

        /*
        internal void AggiungiDocumentoSostituibileIntervento(int idDocumento, string descrizione, int? codiceOggetto, string nomeFile)
        {
            this._documentiSostituibili.Add(new DocumentoSostituibile(OrigineDocumentoSostituibileEnum.Intervento, idDocumento, descrizione, codiceOggetto, nomeFile));
        }

        internal void AggiungiDocumentoSostituibileEndo(int idDocumento, string descrizione, int? codiceOggetto, string nomeFile)
        {
            this._documentiSostituibili.Add(new DocumentoSostituibile(OrigineDocumentoSostituibileEnum.Endoprocedimento, idDocumento, descrizione, codiceOggetto, nomeFile));
        }
        */
        
    }
}