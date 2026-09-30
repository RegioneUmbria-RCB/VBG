using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Ninject;
using System;
using System.Web.UI.WebControls;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class CompilaSchedeDinamiche : MovimentiBasePage
    {
        private static class Constants
        {
            public const int IdPannelloLista = 0;
            public const int IdPannelloDettaglio = 1;
        }

        [Inject]
        protected CompilazioneSchedeDinamicheViewModel _viewModel { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Page.Title = this._viewModel.GetTitoloMovimentoDaEffettuare();

            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            this.rptSchedeDaCompilare.DataSource = this._viewModel.GetListaSchedeDinamiche();
            this.rptSchedeDaCompilare.DataBind();
        }

        protected void lnkSchedaDinamica_Click(object sender, EventArgs e)
        {
            var lnkSchedaDinamica = (LinkButton)sender;

            var idScheda = Convert.ToInt32(lnkSchedaDinamica.CommandArgument);
            var scheda = this._viewModel.CaricaSchedadinamica(idScheda);

            this.lblTitoloModello.Text = scheda.NomeModello;

            this.multiView.ActiveViewIndex = Constants.IdPannelloDettaglio;

            scheda.EseguiScriptCaricamento();

            this.renderer.ImpostaMascheraSolaLettura(new MascheraSolaLetturaVuota());
            //renderer.RicaricaModelloDinamico += (s,ea) => this._viewModel.RicaricaModelloDinamico(s,ea);
            this.renderer.DataSource = scheda;
            this.renderer.DataBind();

        }

        protected void cmdProcedi_Click(object sender, EventArgs e)
        {
            if (!this._viewModel.CanExitStep())
            {
                this.Errori.Add("Per poter proseguire è necessario compilare tutte le schede");
                return;
            }

            this.GoToNextStep();
        }

        protected void cmdTornaIndietro_Click(object sender, EventArgs e)
        {
            this.GoToPreviousStep();
        }


        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            try
            {
                this._viewModel.SalvaSchedaDinamica(this.IdMovimento, this.renderer.DataSource);

                this.cmdChiudi_Click(this, EventArgs.Empty);
            }
            catch (Exception ex)
            {
                this.MostraErroreSalvataggio(ex);
            }
        }

        private void MostraErroreSalvataggio(Exception ex)
        {
            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "alert('Si sono verificati errori durante il salvataggio');", true);
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = Constants.IdPannelloLista;

            this.DataBind();
        }


        protected override FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento.IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }
    }
}