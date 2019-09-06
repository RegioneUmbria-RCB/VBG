using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public static class SostituzioneDocumentaleExtensions
    {
        public static SostituzioniDocumentali.DocumentoSostitutivo ToDocumentoSostitutivo(this SostituzioneDocumentale sd)
        {
            if (sd == null)
            {
                return null;
            }

            return new SostituzioniDocumentali.DocumentoSostitutivo
            {
                CodiceOggetto = sd.CodiceOggettoSostitutivo,
                NomeFile = sd.NomeFileSostitutivo
            };
        }
    }


    public partial class SostituzioniDocumentali : MovimentiBasePage
    {
        public class DocumentoSostitutivo
        {
            public int CodiceOggetto { get; set; }
            public string NomeFile { get; set; }
        }

        public class DocumentoSostituibileBindingItem
        {
            public int? CodiceOggetto { get; set; }
            public string Descrizione { get; set; }
            public int IdDocumento { get; set; }
            public string NomeFile { get; set; }
            public OrigineDocumentoSostituibileEnum Origine { get; set; }
            public string CommandArgument
            {
                get { return String.Format("{0}${1}", this.Origine, CodiceOggetto); }
            }

            public DocumentoSostitutivo DocumentoSostitutivo { get; set; }
        }

        public class SostituzioniDocumentaliBindingItem
        {
            public string Descrizione { get; set; }
            public IEnumerable<DocumentoSostituibileBindingItem> Documenti { get; set; }

            public SostituzioniDocumentaliBindingItem()
            {
                this.Documenti = new List<DocumentoSostituibileBindingItem>();
            }
        }

        [Inject]
        protected SostituzioniDocumentaliViewModel _viewModel { get; set; }

        [Inject]
        protected ValidPostedFileSpecification _dimensioneFileCorretta { get; set; }

        public bool RichiedeFirmaDigitale
        {
            get { return this._viewModel.RichiedeFirmaDigitale; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            var sostituzioni = this._viewModel.GetDocumentiSostituibili();

            var documentiIntervento = new SostituzioniDocumentaliBindingItem
            {
                Descrizione = sostituzioni.DocumentiIntervento.Descrizione,
                Documenti = sostituzioni.DocumentiIntervento
                                        .Documenti
                                        .Select(d => new DocumentoSostituibileBindingItem
                                        {
                                            CodiceOggetto = d.CodiceOggetto,
                                            Descrizione = d.Descrizione,
                                            IdDocumento = d.IdDocumento,
                                            NomeFile = d.NomeFile,
                                            Origine = OrigineDocumentoSostituibileEnum.Intervento,
                                            DocumentoSostitutivo = this._viewModel.GetDocumentoSostitutivo(OrigineDocumentoSostituibileEnum.Intervento, d.CodiceOggetto.Value).ToDocumentoSostitutivo()
                                        })
            };

            var documentiEndo = new List<SostituzioniDocumentaliBindingItem>();

            if (sostituzioni.DocumentiEndo != null)
            {
                foreach (var docsEndo in sostituzioni.DocumentiEndo)
                {
                    var docs = new SostituzioniDocumentaliBindingItem
                    {
                        Descrizione = docsEndo.Descrizione,
                        Documenti = docsEndo.Documenti
                                    .Select(d => new DocumentoSostituibileBindingItem
                                    {
                                        CodiceOggetto = d.CodiceOggetto,
                                        Descrizione = d.Descrizione,
                                        IdDocumento = d.IdDocumento,
                                        NomeFile = d.NomeFile,
                                        Origine = OrigineDocumentoSostituibileEnum.Endoprocedimento,
                                        DocumentoSostitutivo = this._viewModel.GetDocumentoSostitutivo(OrigineDocumentoSostituibileEnum.Endoprocedimento, d.CodiceOggetto.Value).ToDocumentoSostitutivo()
                                    })
                    };

                    documentiEndo.Add(docs);
                }
            }

            this.sostituzioniDocumentaliGrid.RichiedeFirmaDigitale = this.RichiedeFirmaDigitale;
            this.sostituzioniDocumentaliGrid.DataSource = documentiIntervento.Documenti;
            this.sostituzioniDocumentaliGrid.DataBind();

            this.rptDocumentiEndoSostituibili.DataSource = documentiEndo;
            this.rptDocumentiEndoSostituibili.DataBind();
        }

        protected override IStepViewModel GetViewmodel()
        {
            return _viewModel;
        }

        protected void cmdTornaIndietro_Click(object sender, EventArgs e)
        {
            GoToPreviousStep();
        }

        protected void cmdProcedi_Click(object sender, EventArgs e)
        {
            GoToNextStep();
        }
        
        protected void OnAnnullaSostituzioneDocumentale(object sender, SostituzioniDocumentaliGrid.AnnullaSostituzioneDocumentaleEventArgs e)
        {
            try
            {
                this._viewModel.AnnullaSostituzione(e.CodiceOggettoSostitutivo);

                DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore: " + ex.Message);
            }
        }

        protected void OnSostituisciDocumento(object sender, SostituzioniDocumentaliGrid.SostituisciDocumentoEventArgs e)
        {
            try
            {
                var file = new BinaryFile(e.FileSostitutivo, this._dimensioneFileCorretta);
                
                if (file.Size == 0)
                {
                    throw new InvalidOperationException("File vuoto o non valido");
                }

                this._viewModel.EffettuaSostituzione(e.Origine, e.CodiceOggettoOriginale, e.NomeFileOriginale, file);

                DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add("il file caricato non è valido: " + ex.Message);
            }
        }

        protected void rptDocumentiEndoSostituibili_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if(e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var grid = (SostituzioniDocumentaliGrid)e.Item.FindControl("sostituzioniDocumentaliEndoGrid");
                var dataItem = (SostituzioniDocumentaliBindingItem)e.Item.DataItem;

                grid.RichiedeFirmaDigitale = this.RichiedeFirmaDigitale;
                grid.DataSource = dataItem.Documenti;
                grid.DataBind();
            }
        }
    }
}