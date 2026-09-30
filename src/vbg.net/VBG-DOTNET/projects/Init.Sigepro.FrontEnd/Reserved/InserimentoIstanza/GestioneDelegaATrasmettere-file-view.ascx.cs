using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneDelegaATrasmettere_file_view : System.Web.UI.UserControl
    {
        [Inject]
        public BinaryFileFactory _filesFactory { get; set; }

        public GestioneDelegaATrasmettere_file_view()
        {
            FoKernelContainer.Inject(this);
        }

        public class DelegaATrasmettereFileModel
        {
            public bool Presente { get; set; }
            public string LinkDownload { get; set; }
            public string NomeFile { get; set; }
            public bool MostraAvvertimentoFirma { get; set; }

            public static DelegaATrasmettereFileModel FromAllegatoDomanda(string idComune, AllegatoDellaDomanda file, bool richiedeFirma, IUrlDownloadOggettiService urlDownloadOggettiService)
            {
                if (file == null)
                {
                    return null;
                }

                return new DelegaATrasmettereFileModel
                {

                    Presente = true,
                    LinkDownload = urlDownloadOggettiService.GetUrlDownload(file.CodiceOggetto),
                    NomeFile = file.NomeFile,
                    MostraAvvertimentoFirma = richiedeFirma && !file.FirmatoDigitalmente
                };
            }
        }

        public DelegaATrasmettereFileModel DataSource { get; set; }

        public event EventHandler FirmaDocumento;
        public event EventHandler EliminaDocumento;
        public event EventHandler FileCaricato;

        public BinaryFile UploadedFile
        {
            get
            {
                if (this.fileUpload.PostedFile == null)
                {
                    return null;
                }

                return this._filesFactory.Create(this.fileUpload.PostedFile);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            base.DataBind();
        }

        protected void cmdFirma_Click(object sender, EventArgs e)
        {
            if (this.FirmaDocumento != null)
            {
                this.FirmaDocumento(this, EventArgs.Empty);
            }
        }

        protected void cmdEliminaDelega_Click(object sender, EventArgs e)
        {
            if (this.EliminaDocumento != null)
            {
                this.EliminaDocumento(this, EventArgs.Empty);
            }
        }

        protected void cmdUpload_Click(object sender, EventArgs e)
        {
            if (this.FileCaricato != null)
            {
                this.FileCaricato(this, EventArgs.Empty);
            }
        }
    }
}