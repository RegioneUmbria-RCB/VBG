using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Ninject;
using System;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneAllegatiDatiDinamici : IstanzeStepPage
    {
        [Inject]
        public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }

        [Inject]
        public IRiepiloghiDatiDinamiciService ModelliDinamiciService { get; set; }

        [Inject]
        public IRiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public IRedirectService _redirectService { get; set; }

        public bool IgnoraObbligoFirmaDigitale
        {
            get { return this.ViewstateGet("IgnoraObbligoFirmaDigitale", false); }
            set { this.ViewStateSet("IgnoraObbligoFirmaDigitale", value); }
        }

        public bool GeneraRiepilogoSchedeCheNonRichiedonoFirma
        {
            get { return this.ViewstateGet("GeneraRiepilogoSchedeCheNonRichiedonoFirma", true); }
            set { this.ViewStateSet("GeneraRiepilogoSchedeCheNonRichiedonoFirma", value); }
        }




        protected void Page_Load(object sender, EventArgs e)
        {
            // Il salvataggio viene gestito dal service
            this.Master.IgnoraSalvataggioDati = true;
            this.Master.ResetValidatorsOnLoad = false;

            if (!this.IsPostBack)
                this.DataBind();
        }

        #region Ciclo di vita dello step

        public override void OnInitializeStep()
        {
            this.ModelliDinamiciService.RigeneraRiepiloghi(this.IdDomanda, this.GeneraRiepilogoSchedeCheNonRichiedonoFirma);
        }

        public override bool CanEnterStep()
        {
            return this.ReadFacade.Domanda.RiepiloghiSchedeDinamiche.Count > 0;
        }


        public override bool CanExitStep()
        {
            var listaErrori = this.ReadFacade.Domanda.DatiDinamici.VerificaUploadModelliRichiesti(this.IgnoraObbligoFirmaDigitale);

            this.Errori.AddRange(listaErrori);

            return listaErrori.Count() == 0;
        }

        #endregion




        /// <summary>
        /// Elimina il riepilogo di una scheda dalla domanda
        /// </summary>
        /// <param name="row"></param>
        private void EliminaRigaRiepilogo(PresentazioneIstanzaDataSet.RiepilogoDatiDinamiciRow row)
        {
            if (!row.IsCodiceOggettoNull())
                this._allegatiDomandaFoRepository.EliminaAllegato(this.IdDomanda, row.CodiceOggetto);

            row.Delete();
        }

        #region Binding dei dati
        public class AllegatiRiepiloghiBindingItem
        {
            public int IdModello { get; set; }
            public int IndiceMolteplicita { get; set; }
            public bool Richiesto { get; set; }
            public bool RichiedeFirmaDigitale { get; set; }
            public string NomeScheda { get; set; }
            public string LinkDownloadModello { get; set; }
            public int? CodiceOggetto { get; set; }
            public string NomeFile { get; set; }
            public bool FirmatoDigitalmente { get; set; }
            public bool MostraBottoneFirma { get { return this.CodiceOggetto.HasValue && this.RichiedeFirmaDigitale && !this.FirmatoDigitalmente; } }
            public string CommandArgument { get { return this.IdModello.ToString() + "$" + this.IndiceMolteplicita.ToString(); } }
        }

        public override void DataBind()
        {
            var dataSource = from r in this.ReadFacade.Domanda.RiepiloghiSchedeDinamiche.Riepiloghi
                             orderby this.OrdineModello(r.IdModello), (int)this.ReadFacade.Domanda.DatiDinamici.GetModelloById(r.IdModello).TipoFirma, r.Descrizione
                             select new AllegatiRiepiloghiBindingItem
                             {
                                 IdModello = r.IdModello,
                                 IndiceMolteplicita = r.IndiceMolteplicita,
                                 Richiesto = this.ReadFacade.Domanda.DatiDinamici.GetModelloById(r.IdModello).TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna,
                                 RichiedeFirmaDigitale = !this.IgnoraObbligoFirmaDigitale && this.ReadFacade.Domanda.DatiDinamici.GetModelloById(r.IdModello).TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna,
                                 NomeScheda = r.Descrizione,
                                 //LinkDownloadModello	= GetUrlDownload( r.IdModello , r.IndiceMolteplicita ),
                                 CodiceOggetto = r.AllegatoDellUtente == null ? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
                                 NomeFile = r.AllegatoDellUtente == null ? String.Empty : r.AllegatoDellUtente.NomeFile,
                                 FirmatoDigitalmente = r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente
                             };


            this.gvRiepiloghiDatiDinamici.DataSource = dataSource;
            this.gvRiepiloghiDatiDinamici.DataBind();
        }

        private int OrdineModello(int idModello)
        {
            var ordine = this.ReadFacade.Domanda.DatiDinamici.ModelliIntervento.Where(x => x.Modello.IdModello == idModello).Select(x => x.Ordine).FirstOrDefault();

            return ordine;
        }

        #endregion

        #region Gestione eventi della DataGrid
        protected void gvRiepiloghiDatiDinamici_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            var gridRow = this.gvRiepiloghiDatiDinamici.Rows[e.RowIndex];

            var fuAllegato = (FileUpload)gridRow.FindControl("fuAllegato");
            var idModello = Convert.ToInt32(this.gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IdModello"]);
            var indiceMolteplicita = Convert.ToInt32(this.gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IndiceMolteplicita"]);

            try
            {
                var file = new WebFormsBinaryFile(fuAllegato, this._validPostedFileSpecification);

                this.ModelliDinamiciService.AggiungiOggettoRiepilogo(this.IdDomanda, idModello, indiceMolteplicita, file, this.IgnoraObbligoFirmaDigitale);

                this.gvRiepiloghiDatiDinamici.EditIndex = -1;

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void MostraModelloDinamico(object sender, EventArgs e)
        {
            var ib = (LinkButton)sender;
            var lbl = (Literal)ib.NamingContainer.FindControl("ltrNomeFile");

            var cmdArgs = ib.CommandArgument.Split('$');

            var idModello = Convert.ToInt32(cmdArgs[0]);
            var nomeRiepilogo = lbl.Text.Trim() + ".pdf";
            var indiceMolteplicita = Convert.ToInt32(cmdArgs[1]);

            var riepilogo = this._riepilogoModelloInHtmlFactory.FromIdDomandaOnline(this.IdDomanda, idModello, indiceMolteplicita);
            var result = riepilogo.ConvertiInPdf(nomeRiepilogo);

            this.Response.Clear();
            this.Response.ContentType = result.MimeType;
            this.Response.AddHeader("content-disposition", "attachment;filename=\"" + result.FileName + "\"");
            this.Response.BinaryWrite(result.FileContent);
            this.Response.End();
        }

        protected void gvRiepiloghiDatiDinamici_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "Firma")
            {
                var codiceOggetto = Convert.ToInt32(e.CommandArgument);

                this._redirectService.ToFirmaDigitale(this.IdDomanda, codiceOggetto);
            }
        }

        protected void gvRiepiloghiDatiDinamici_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            this.gvRiepiloghiDatiDinamici.EditIndex = -1;

            this.DataBind();
        }

        protected void gvRiepiloghiDatiDinamici_RowEditing(object sender, GridViewEditEventArgs e)
        {
            this.gvRiepiloghiDatiDinamici.EditIndex = e.NewEditIndex;

            this.DataBind();

            var fuAllegato = (FileUpload)this.gvRiepiloghiDatiDinamici.Rows[e.NewEditIndex].FindControl("fuAllegato");

            if (e.NewEditIndex != -1)
            {
                this.ClientScript.RegisterStartupScript(this.GetType(), "browseFile", @"triggerOpenFile('" + fuAllegato.ClientID + "');", true);
            }
        }

        protected void gvRiepiloghiDatiDinamici_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            var idModello = Convert.ToInt32(this.gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IdModello"]);
            var indiceMolteplicita = Convert.ToInt32(this.gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IndiceMolteplicita"]);

            this.ModelliDinamiciService.EliminaOggettoRiepilogo(this.IdDomanda, idModello, indiceMolteplicita);

            this.DataBind();

            this.gvRiepiloghiDatiDinamici_RowEditing(this, new GridViewEditEventArgs(e.RowIndex));
        }
        #endregion
    }
}
