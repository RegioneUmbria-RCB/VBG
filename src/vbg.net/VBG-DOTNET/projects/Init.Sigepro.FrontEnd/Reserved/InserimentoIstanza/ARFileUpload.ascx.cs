using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.ComponentModel;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class ARFileUpload : System.Web.UI.UserControl
    {
        [Inject]
        protected AllegatiDomandaService _allegatiService { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public IUrlDownloadOggettiService _urlDownloadService { get; set; }

        public static class Constants
        {
            public const int IdVistaUpload = 0;
            public const int IdVistaDettaglio = 1;
        }

        private string IdComune { get { return Request.QueryString["idComune"]; } }
        private string Software { get { return Request.QueryString["Software"]; } }
        private int IdPresentazione { get { return Convert.ToInt32(Request.QueryString["IdPresentazione"]); } }

        [Bindable(true)]
        public int? CodiceOggetto
        {
            get { object o = this.ViewState["CodiceOggetto"]; return o == null ? (int?)null : (int)o; }
            set { this.ViewState["CodiceOggetto"] = value; }
        }

        [Bindable(true)]
        public int IdEndoprocedimento
        {
            get { object o = this.ViewState["IdEndoprocedimento"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdEndoprocedimento"] = value; }
        }

        [Bindable(true)]
        public bool RichiedeFirmaDigitale
        {
            get { object o = this.ViewState["RichiedeFirmaDigitale"]; return o == null ? true : (bool)o; }
            set { this.ViewState["RichiedeFirmaDigitale"] = value; }
        }


        [Bindable(true)]
        public AllegatoDellaDomanda DataSource { get; set; }

        public delegate void LeavingPageDelegate(object sender, EventArgs e);
        public event LeavingPageDelegate LeavingPage;

        protected void Page_Load(object sender, EventArgs e)
        {
            FoKernelContainer.Inject(this);
        }

        protected void lnkFirma_Click(object sender, EventArgs e)
        {
            if (LeavingPage != null)
                LeavingPage(this, EventArgs.Empty);
        }

        protected void lnkRimuovi_Click(object sender, EventArgs e)
        {
            this._allegatiService.RimuoviDaDomanda(this.IdPresentazione, this.CodiceOggetto.Value);

            this.DataSource = null;
            this.DataBind();
        }

        protected void lnkCaricaFile_Click(object sender, EventArgs e)
        {
            var result = this._allegatiService.AllegaADomanda(this.IdPresentazione, new WebFormsBinaryFile(this.fuCaricaFile.PostedFile, this._validPostedFileSpecification), false);

            this.DataSource = new AllegatoDellaDomanda(result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente, String.Empty);
            this.DataBind();
        }

        public override void DataBind()
        {
            if (this.DataSource == null)
            {
                this.MostraVistaUpload();
            }
            else
            {
                this.MostraVistaDettaglio();
            }
        }

        private void MostraVistaUpload()
        {
            this.multiView.ActiveViewIndex = Constants.IdVistaUpload;
        }

        private void MostraVistaDettaglio()
        {
            this.CodiceOggetto = this.DataSource.CodiceOggetto;
            this.multiView.ActiveViewIndex = Constants.IdVistaDettaglio;
            this.hlDownloadFile.Text = this.DataSource.NomeFile;
            this.hlDownloadFile.NavigateUrl = this._urlDownloadService.GetUrlDownload(this.DataSource.CodiceOggetto);

            this.lnkFirma.Visible = this.lblErroreFirma.Visible = this.RichiedeFirmaDigitale && !this.DataSource.FirmatoDigitalmente;
        }
    }
}