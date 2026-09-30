using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class IntegrazioneLDPLivorno : IstanzeStepPage
    {
        [Inject]
        protected IAreeUsoPubblicoLivornoService _ldpService { get; set; }

        public int IdCampoDataInizio
        {
            get { object o = this.ViewState["IdCampoDataInizio"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoDataInizio"] = value; }
        }

        public int IdCampoOraInizio
        {
            get { object o = this.ViewState["IdCampoOraInizio"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoOraInizio"] = value; }
        }


        public int IdCampoDataFine
        {
            get { object o = this.ViewState["IdCampoDataFine"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoDataFine"] = value; }
        }

        public int IdCampoOraFine
        {
            get { object o = this.ViewState["IdCampoOraFine"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoOraFine"] = value; }
        }

        public int IdCampoDescrizioneOccupazione
        {
            get { object o = this.ViewState["IdCampoDescrizioneOccupazione"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoDescrizioneOccupazione"] = value; }
        }
        public int IdCampoFrequenzaOccupazione
        {
            get { object o = this.ViewState["IdCampoFrequenzaOccupazione"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoFrequenzaOccupazione"] = value; }
        }




        protected bool ReturningFromCallingPage
        {
            get
            {
                return !String.IsNullOrEmpty(Request.QueryString["returning"]);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraBottoneAvanti = false;
            this.Master.AssociaProprietaStepDaWorkflow();

            if (!ReturningFromCallingPage)
            {
                var redirUrl = this._ldpService.GetUrlCompilazioneDomanda(this.IdDomanda);

                Response.Redirect(redirUrl);
                Response.End();
            }

            var campiInizio = new AggiornaDatiOccupazioneCommand.CampiIntervallo(this.IdCampoDataInizio, this.IdCampoOraInizio);
            var campiFine = new AggiornaDatiOccupazioneCommand.CampiIntervallo(this.IdCampoDataFine, this.IdCampoOraFine);

            var cmd = new AggiornaDatiOccupazioneCommand(this.IdDomanda, campiInizio, campiFine, this.IdCampoDescrizioneOccupazione, this.IdCampoFrequenzaOccupazione);

            this._ldpService.AggiornaDatiOccupazione(cmd);

            this.Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}