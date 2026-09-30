using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Forms;
using VBG.BlazorComponentsLibrary.DesignComuni.Forms;
using VBG.BlazorComponentsLibrary.DesignComuni.Modals;
using static AreaRiservataCore.Pages.Movimenti.SostituzioniDocumentali;

namespace AreaRiservataCore.Pages.Movimenti
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

    public class DocumentData
    {
        public DocumentoSostituibileBindingItem updatingDocument { get; set; }
        public Attachment newDocument { get; set; }
    }

    public class Attachment
    {
        public string? FileName { get; set; }
        public IBrowserFile? File { get; set; }
        public int? CodiceOggetto { get; set; }
    }

    public partial class SostituzioniDocumentali
    {
        public class DocumentoSostitutivo
        {
            public int CodiceOggetto { get; set; }
            public string NomeFile { get; set; } = "";
        }

        public class DocumentoSostituibileBindingItem
        {
            public int? CodiceOggetto { get; set; }
            public string Descrizione { get; set; } = "";
            public int IdDocumento { get; set; }
            public string NomeFile { get; set; } = "";
            public OrigineDocumentoSostituibileEnum Origine { get; set; }
            public string CommandArgument
            {
                get { return String.Format("{0}${1}", this.Origine, this.CodiceOggetto); }
            }

            public DocumentoSostitutivo? DocumentoSostitutivo { get; set; }
        }

        public class SostituzioniDocumentaliBindingItem
        {
            public string Descrizione { get; set; } = "";
            public IEnumerable<DocumentoSostituibileBindingItem> Documenti { get; set; }

            public SostituzioniDocumentaliBindingItem()
            {
                this.Documenti = new List<DocumentoSostituibileBindingItem>();
            }
        }

        [Inject]
        protected SostituzioniDocumentaliViewModel _viewModel { get; set; } = default!;

        [Inject]
        public IPostedFileSpecificationFactoryAsync _postedFileSpecificationFactoryAsync { get; set; } = default!;

        public bool RichiedeFirmaDigitale
        {
            get { return this._viewModel.RichiedeFirmaDigitale; }
        }

        private IValidPostedFileSpecificationAsync validPostedFileSpecificationAsync { get; set; }

        private SostituzioniDocumentaliBindingItem documentiIntervento;
        private List<SostituzioniDocumentaliBindingItem> documentiEndo;
        private DocumentData documentData = new();
        private bool _annullaSostituzioneModalVisible;
        private DocumentoSostitutivo _documentoSostitutivo = default!;
        private bool _showUploadModal;

        protected override void OnInitializedMovimenti()
        {
            validPostedFileSpecificationAsync = this._postedFileSpecificationFactoryAsync.Get(
            new FileValidationFlags
            {
                FirmatoDigitalmente = this.RichiedeFirmaDigitale,
                Obbligatorio = true
            });

            documentData.newDocument = new();
            DataBind();
        }

        public void DataBind()
        {
            var sostituzioni = this._viewModel.GetDocumentiSostituibili();

            documentiIntervento = new SostituzioniDocumentaliBindingItem
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

            documentiEndo = new List<SostituzioniDocumentaliBindingItem>();

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

            //this.sostituzioniDocumentaliGrid.RichiedeFirmaDigitale = this.RichiedeFirmaDigitale;
            //this.sostituzioniDocumentaliGrid.DataSource = documentiIntervento.Documenti;
            //this.sostituzioniDocumentaliGrid.DataBind();

            //this.rptDocumentiEndoSostituibili.DataSource = documentiEndo;
            //this.rptDocumentiEndoSostituibili.DataBind();
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }

        private async Task OnBtnBackAsync()
        {
            await GoToPreviousStepAsync();
        }

        private async Task OnBtnNextAsync()
        {
            await GoToNextStepAsync();
        }

        protected async Task OnSostituisciDocumentoAsync()
        {
            try
            {
                this._showUploadModal = false;
                var file = await documentData.newDocument.File.ToBinaryFileAsync(validPostedFileSpecificationAsync);

                if (file?.Size == 0)
                {
                    throw new InvalidOperationException("File vuoto o non valido");
                }

                string strOrigine = documentData.updatingDocument.CommandArgument.Split('$')[0];
                var origine = (OrigineDocumentoSostituibileEnum)Enum.Parse(typeof(OrigineDocumentoSostituibileEnum), strOrigine);

                await Task.Run(() => this._viewModel.EffettuaSostituzione(origine, documentData.updatingDocument.CodiceOggetto.Value, documentData.updatingDocument.Descrizione, Convert.ToInt32(documentData.newDocument.CodiceOggetto), documentData.newDocument.FileName));

                documentData.newDocument = new();
                this.DataBind();
            }
            catch (Exception ex)
            {
                MessageContainer.ClearErrors();
                MessageContainer.AddError("il file caricato non è valido: " + ex.Message);
            }
        }

        private void OpenAnnullaSostituzioneModal(DocumentoSostituibileBindingItem doc)
        {
            this._annullaSostituzioneModalVisible = true;
            this._documentoSostitutivo = doc.DocumentoSostitutivo;
        }

        protected void OnAnnullaSostituzioneDocumentale()
        {
            try
            {
                this._annullaSostituzioneModalVisible = false;
                this._viewModel.AnnullaSostituzione(this._documentoSostitutivo.CodiceOggetto);

                this.DataBind();
            }
            catch (Exception ex)
            {
                MessageContainer.ClearErrors();
                MessageContainer.AddError("Si è verificato un errore: " + ex.Message);
            }
        }

        private void CloseAnnullaSostituzioneModal()
        {
            this._annullaSostituzioneModalVisible = false;
        }

        private void OpenUploadWindow(DocumentoSostituibileBindingItem doc)
        {
            documentData.updatingDocument = doc;

            this._showUploadModal = true;
        }

        private void UploadFile_OnChanged(FormFileChangedEventArgs e)
        {
            documentData.newDocument.FileName = e.FileName;
        }

        private void CloseUploadModal() 
        {
            this._showUploadModal = false;
            documentData.newDocument = new();
        }
    }
}
