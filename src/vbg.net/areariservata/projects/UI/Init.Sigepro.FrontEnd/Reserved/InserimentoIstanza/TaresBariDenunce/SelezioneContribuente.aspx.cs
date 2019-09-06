using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.DenunceTares;
using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce
{
    public partial class SelezioneContribuente : IstanzeStepPage
    {
        private static class Constants
        {
            public const int IdVistaRicerca = 0;
            public const int IdVistaDettaglio = 1;
        }

        [Inject]
        protected IComuniService _comuniService { get; set; }

        [Inject]
        protected DenunceTaresBariService _taresService { get; set; }

        public int CodiceTipoSoggettoPersonaFisica
        {
            get { object o = this.ViewState["CodiceTipoSoggettoPersonaFisica"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceTipoSoggettoPersonaFisica"] = value; }
        }

        public int CodiceTipoSoggettoPersonaGiuridica
        {
            get { object o = this.ViewState["CodiceTipoSoggettoPersonaGiuridica"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceTipoSoggettoPersonaGiuridica"] = value; }
        }

        public int CodiceTipoSoggettoLegaleRappresentante
        {
            get { object o = this.ViewState["CodiceTipoSoggettoLegaleRappresentante"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceTipoSoggettoLegaleRappresentante"] = value; }
        }

        public TipoUtenzaTaresEnum TipoUtenza
        {
            get { return (TipoUtenzaTaresEnum)Enum.Parse(typeof(TipoUtenzaTaresEnum), TipoUtenzaString); }
            set { this.TipoUtenzaString = value.ToString(); }
        }

        public string TipoUtenzaString
        {
            get { object o = this.ViewState["TipoUtenzaString"]; return o == null ? "Domestica" : (string)o; }
            set { this.ViewState["TipoUtenzaString"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var utenza = _taresService.GetUtenzaSelezionata(IdDomanda);

                if (utenza != null)
                {
                    MostraVistaDettaglio();
                }
                else
                {
                    MostraVistaRicerca();
                }
            }
        }

        public override bool CanExitStep()
        {

            try
            {
                this._taresService.InserisciUtenzaTraAnagrafiche(IdDomanda, CodiceTipoSoggettoPersonaFisica, CodiceTipoSoggettoPersonaGiuridica, CodiceTipoSoggettoLegaleRappresentante);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }

            return this.Errori.Count == 0;
        }

        protected void cmdNuovaUtenza_Click(object sender, EventArgs e)
        {
            try
            {
                this._taresService.ImpostaContribuente(IdDomanda, DatiAnagraficiContribuenteDenunciaTares.Nuovo());

                this.Master.cmdNextStep_Click(this, EventArgs.Empty);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            try
            {
                if (String.IsNullOrEmpty(txtIdContribuente.Text.Trim()))
                {
                    throw new Exception("Immettere un identificativo contribuente");
                }


                if (String.IsNullOrEmpty(txtPartitaIvaCf.Text.Trim()))
                {
                    throw new Exception("Immettere un codice fiscale o una partita iva");
                }


                var datiContribuente = this._taresService.GetDatiContribuente(UserAuthenticationResult.DatiUtente, txtIdContribuente.Text, txtPartitaIvaCf.Text);

                if (datiContribuente != null)
                {
                    _taresService.ImpostaContribuente(IdDomanda, datiContribuente);

                    MostraVistaDettaglio();
                }

            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }

        private void MostraVistaRicerca()
        {
            txtIdContribuente.Text = "";
            txtPartitaIvaCf.Text = "";
            this.multiView.ActiveViewIndex = Constants.IdVistaRicerca;
            this.Master.MostraPaginatoreSteps = false;
        }

        private void MostraVistaDettaglio()
        {

            this.utenzaSelezionabileItem.TipoUtenza = this.TipoUtenza;
            this.utenzaSelezionabileItem.DataSource = _taresService.GetUtenzaSelezionata(IdDomanda);
            this.utenzaSelezionabileItem.DataBind();

            this.multiView.ActiveViewIndex = Constants.IdVistaDettaglio;

            this.Master.MostraPaginatoreSteps = true;
        }

        protected void cmdSelezionacontribuente_Click(object sender, EventArgs e)
        {
            MostraVistaRicerca();
        }
    }
}