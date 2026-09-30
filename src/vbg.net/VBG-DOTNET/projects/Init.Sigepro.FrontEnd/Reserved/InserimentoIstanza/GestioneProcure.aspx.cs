using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Ninject;
using System;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneProcure : IstanzeStepPage
    {
        public class GrigliaProcureBindingItem
        {
            public string CodiceProcuratore { get; set; }
            public string CodiceAnagrafe { get; set; }
            public string NomeAnagrafe { get; set; }
            public string NomeProcuratore { get; set; }
            public string CodiceOggetto { get; set; }
            public bool AllegatoPresente { get; set; }
            public string PathDownload { get; set; }
            public string NomeFile { get; set; }
            public bool IsFirmatoDigitalmente { get; set; }
            public bool RichiedeFirmaDigitale { get; set; }
            public string DocIdentitaCodiceOggetto { get; set; }
            public bool DocIdentitaPresente { get; set; }
            public string DocIdentitaPathDownload { get; set; }
            public string DocIdentitaNomeFile { get; set; }

            public bool AllegatoNecessitaFirma
            {
                get
                {
                    return this.AllegatoPresente && this.RichiedeFirmaDigitale && !this.IsFirmatoDigitalmente;
                }
            }
        }



        [Inject]
        public ProcureService ProcureService { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public IRedirectService _redirectService { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        public bool RichiedeFirmaDigitale
        {
            get { object o = this.ViewState["RichiedeFirmaDigitale"]; return o == null ? true : (bool)o; }
            set { this.ViewState["RichiedeFirmaDigitale"] = value; }
        }

        public bool RichiedeCaricamentoDocumentoIdentita
        {
            get { return this.gvProcure.Columns[this.gvProcure.Columns.Count - 1].Visible; }
            set { this.gvProcure.Columns[this.gvProcure.Columns.Count - 1].Visible = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            // Il salvataggio dati viene effettuato dal service
            this.Master.IgnoraSalvataggioDati = true;
            this.Master.ResetValidatorsOnLoad = false;


            if (!this.IsPostBack)
                this.DataBind();

        }

        #region Gestione eventi della DataGrid
        protected void gvProcure_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            var gridRow = this.gvProcure.Rows[e.RowIndex];

            var fuProcura = (FileUpload)gridRow.FindControl("fuProcura");

            var codiceAnagrafe = this.gvProcure.DataKeys[e.RowIndex]["CodiceAnagrafe"].ToString();
            var codiceProcuratore = this.gvProcure.DataKeys[e.RowIndex]["CodiceProcuratore"].ToString();

            try
            {
                var file = new WebFormsBinaryFile(fuProcura, this._validPostedFileSpecification);

                this.ProcureService.CaricaOggettoProcura(this.IdDomanda, codiceAnagrafe, codiceProcuratore, file);

                this.gvProcure.EditIndex = -1;

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void gvProcure_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName != "Firma")
                return;

            var codiceOggetto = Int32.Parse(e.CommandArgument.ToString());

            this._redirectService.ToFirmaDigitale(this.IdDomanda, codiceOggetto);
        }

        protected void gvProcure_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            this.gvProcure.EditIndex = -1;

            this.DataBind();
        }

        protected void gvProcure_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            var codiceAnagrafe = this.gvProcure.DataKeys[e.RowIndex]["CodiceAnagrafe"].ToString();
            var codiceProcuratore = this.gvProcure.DataKeys[e.RowIndex]["CodiceProcuratore"].ToString();

            this.ProcureService.EliminaOggettoProcura(this.IdDomanda, codiceAnagrafe, codiceProcuratore);

            this.DataBind();
        }
        #endregion

        #region Ciclo della vita dello step

        public override bool CanEnterStep()
        {
            return this.ReadFacade.Domanda.Procure.Procure.Where(x => x.Procuratore != null).Count() > 0;
        }

        public override bool CanExitStep()
        {
            // Si può uscire dallo step solo se per tutti i richiedenti procurati è stato caricato il file che ne attesta la procura
            // e se i files caricati sono firmati digitalmente
            var numeroProcuratoriNonValidi = this.ReadFacade.Domanda
                                                        .Procure
                                                        .Procure
                                                        .Where(x => x.Allegato == null && x.Procuratore != null)
                                                        .Count();

            if (numeroProcuratoriNonValidi > 0)
            {
                this.Errori.Add("Per proseguire è necessario caricare tutti i documenti comprovanti la procura speciale");

                return false;
            }

            if (this.RichiedeCaricamentoDocumentoIdentita)
            {
                var numeroDocumentiIdentitaNonValidi = this.ReadFacade.Domanda
                                                                .Procure
                                                                .Procure
                                                                .Where(x => x.DocumentoIdentita == null && x.Procuratore != null)
                                                                .Count();

                if (numeroDocumentiIdentitaNonValidi > 0)
                {
                    this.Errori.Add("Per proseguire è necessario caricare i documenti di identità di tutti i procuratori");

                    return false;
                }
            }

            if (!this.RichiedeFirmaDigitale)
                return true;

            var procureNonFirmate = this.ReadFacade.Domanda.Procure
                                                        .Procure
                                                        .Where(x => x.Allegato != null && x.Procuratore != null && !x.Allegato.FirmatoDigitalmente);

            if (procureNonFirmate.Count() == 0)
                return true;

            foreach (var procura in procureNonFirmate)
            {
                var errStr = String.Format("Il file attestante la procura di {0} relativamente a {1} non è firmato digitalmente", procura.Procuratore.Nominativo, procura.Procurato.Nominativo);

                this.Errori.Add(errStr);
            }



            return false;
        }

        #endregion

        public override void DataBind()
        {
            this.gvProcure.DataSource = this.ReadFacade.Domanda
                                             .Procure
                                             .Procure
                                             .Where(x => x.Procuratore != null)
                                             .Select(x => new GrigliaProcureBindingItem
                                             {
                                                 CodiceProcuratore = x.Procuratore.CodiceFiscale,
                                                 CodiceAnagrafe = x.Procurato.CodiceFiscale,
                                                 NomeAnagrafe = x.Procurato.Nominativo,
                                                 NomeProcuratore = x.Procuratore.Nominativo,
                                                 CodiceOggetto = x.Allegato == null ? String.Empty : x.Allegato.CodiceOggetto.ToString(),
                                                 AllegatoPresente = x.Allegato != null,
                                                 PathDownload = x.Allegato != null ? this._urlDownloadOggettiService.GetUrlDownload(x.Allegato.CodiceOggetto) : String.Empty,
                                                 NomeFile = x.Allegato != null ? x.Allegato.NomeFile : String.Empty,
                                                 IsFirmatoDigitalmente = x.Allegato != null ? x.Allegato.FirmatoDigitalmente : false,
                                                 RichiedeFirmaDigitale = RichiedeFirmaDigitale,

                                                 DocIdentitaCodiceOggetto = x.DocumentoIdentita == null ? String.Empty : x.DocumentoIdentita.CodiceOggetto.ToString(),
                                                 DocIdentitaPresente = x.DocumentoIdentita != null,
                                                 DocIdentitaPathDownload = x.DocumentoIdentita != null ? this._urlDownloadOggettiService.GetUrlDownload(x.DocumentoIdentita.CodiceOggetto) : String.Empty,
                                                 DocIdentitaNomeFile = x.DocumentoIdentita != null ? x.DocumentoIdentita.NomeFile : String.Empty,
                                             });
            this.gvProcure.DataBind();
        }


        protected void bmAllegaDocumento_OkClicked(object sender, EventArgs e)
        {
            try
            {
                var codiceProcuratore = this.hfCodiceProcuratore.Value;
                var codiceAnagrafe = this.hfCodiceAnagrafe.Value;
                var tipoDocumento = this.hfTipoDocumento.Value;
                var file = new WebFormsBinaryFile(this.fuDocumento.PostedFile, this._validPostedFileSpecification);

                if (String.IsNullOrEmpty(codiceProcuratore))
                {
                    throw new Exception("Codice procuratore non valido");
                }

                if (String.IsNullOrEmpty(codiceAnagrafe))
                {
                    throw new Exception("Codice anagrafe non valido");
                }

                if (tipoDocumento != "procura" && tipoDocumento != "documentoIdentita")
                {
                    throw new Exception("Codice tipo documento non valido");
                }

                if (tipoDocumento == "procura")
                {
                    this.ProcureService.CaricaOggettoProcura(this.IdDomanda, codiceAnagrafe, codiceProcuratore, file);
                }
                else
                {
                    this.ProcureService.CaricaDocumentoIdentitaProcura(this.IdDomanda, codiceAnagrafe, codiceProcuratore, file);
                }

                this.DataBind();

            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }

        }

        protected void lnkEliminaDocumentoIdentita_Click(object sender, EventArgs e)
        {
            var linkButton = (LinkButton)sender;
            var row = linkButton.NamingContainer as GridViewRow;

            try
            {
                var codiceAnagrafe = this.gvProcure.DataKeys[row.RowIndex]["CodiceAnagrafe"].ToString();
                var codiceProcuratore = this.gvProcure.DataKeys[row.RowIndex]["CodiceProcuratore"].ToString();

                this.ProcureService.EliminaOggettoDocIdentita(this.IdDomanda, codiceAnagrafe, codiceProcuratore);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void lnkElimina_Click(object sender, EventArgs e)
        {
            var linkButton = (LinkButton)sender;
            var row = linkButton.NamingContainer as GridViewRow;

            try
            {
                var codiceAnagrafe = this.gvProcure.DataKeys[row.RowIndex]["CodiceAnagrafe"].ToString();
                var codiceProcuratore = this.gvProcure.DataKeys[row.RowIndex]["CodiceProcuratore"].ToString();

                this.ProcureService.EliminaOggettoProcura(this.IdDomanda, codiceAnagrafe, codiceProcuratore);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }
    }
}
