using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Ninject;
using System;
using System.IO;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.CommonControls.Files
{
    public partial class FileUploadControl :
        Ninject.Web.UserControlBase
    {

        [Inject]
        protected IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        public bool IsValid
        {
            get => this.ErrorMessage.Length == 0;
        }

        public bool Required
        {
            get => this.EditPostedFile.Attributes.Keys.Cast<string>().Where(x => x == "required").Any();
            set
            {
                if (value)
                {
                    this.EditPostedFile.Attributes.Add("required", "required");
                }
                else
                {
                    this.EditPostedFile.Attributes.Remove("required");
                }
            }
        }

        public string EtichettaControllo
        {
            get => this.ViewState["EtichettaControllo"]?.ToString() ?? "File";
            set => this.ViewState["EtichettaControllo"] = value;
        }

        public string CssClass
        {
            get => this.ViewState["CssClass"]?.ToString() ?? "";
            set => this.ViewState["CssClass"] = value;
        }

        public int? CodiceOggetto
        {
            get => (int?)this.ViewState["CodiceOggetto"];
            protected set => this.ViewState["CodiceOggetto"] = value;
        }

        public string ErrorMessage
        {
            get => this.ViewState["ErrorMessage"]?.ToString() ?? "asdf";
            set => this.ViewState["ErrorMessage"] = value;
        }

        public string NomeFile
        {
            get => this.ViewState["NomeFile"]?.ToString() ?? "";
            protected set => this.ViewState["NomeFile"] = value;
        }

        protected bool ModalitaInserimento
        {
            get => Boolean.Parse(this.ViewState["ModalitaInserimento"]?.ToString() ?? "true");
            set => this.ViewState["ModalitaInserimento"] = value;
        }

        public bool FileModificato
        {
            get => Boolean.Parse(this.ViewState["FileModificato"]?.ToString() ?? "false");
            protected set => this.ViewState["FileModificato"] = value;
        }

        protected bool ModalitaVisualizzazione => !this.ModalitaInserimento;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (this.ModalitaInserimento && (this.EditPostedFile.PostedFile?.ContentLength).GetValueOrDefault(0) > 0)
            {
                this.FileModificato = true;
            }
        }

        public override void DataBind()
        {
            base.DataBind();
        }

        internal void ResetValues()
        {
            this.CodiceOggetto = null;
            this.ErrorMessage = "";
            this.NomeFile = "";
            this.ModalitaInserimento = true;
            this.FileModificato = false;
        }

        internal BinaryFile GetFile()
        {
            var ms = new MemoryStream(this.EditPostedFile.PostedFile.ContentLength);
            this.EditPostedFile.PostedFile.InputStream.Seek(0, SeekOrigin.Begin);
            this.EditPostedFile.PostedFile.InputStream.CopyTo(ms);

            ms.Seek(0, SeekOrigin.Begin);

            return BinaryFile.FromFileData(this.EditPostedFile.PostedFile.FileName, this.EditPostedFile.PostedFile.ContentType, ms.ToArray());
        }

        internal bool ValidaFile(IValidPostedFileSpecification validPostedFileSpecification)
        {
            var master = ((AreaRiservataMaster)this.Page.Master);
            master.ResetValidatorsOnLoad = true;

            var esito = validPostedFileSpecification.IsSatisfiedBy(this.EditPostedFile.PostedFile);

            if (!esito)
            {
                this.ErrorMessage = validPostedFileSpecification.ErrorMessage;
                master.ResetValidatorsOnLoad = false;
            }

            return esito;
        }

        internal void SetOggetto(int codiceOggetto, string nomeFile)
        {
            this.CodiceOggetto = codiceOggetto;
            this.ErrorMessage = "";
            this.NomeFile = nomeFile;
            this.ModalitaInserimento = false;
            this.FileModificato = false;
        }

        protected override void OnPreRender(EventArgs e)
        {
            if (this.CodiceOggetto.HasValue)
            {
                this.lnkMostraAllegato.NavigateUrl = this._urlDownloadOggettiService.GetUrlDownload(this.CodiceOggetto.Value);
            }

            base.OnPreRender(e);
        }

        protected void cmdSostituisci_Click(object sender, EventArgs e)
        {
            this.ModalitaInserimento = true;
            this.DataBind();
        }

        protected void cmdAnnullaModifiche_Click(object sender, EventArgs e)
        {
            this.ModalitaInserimento = false;
            this.DataBind();
        }
    }
}