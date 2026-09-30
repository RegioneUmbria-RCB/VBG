using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PraticheEdilizieSiena;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class BenvenutoLDP : IstanzeStepPage
    {
        [Inject]
        protected IPraticheEdilizieSienaService _ldpService { get; set; }

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

            if (!ReturningFromCallingPage)
            {
                var redirUrl = this._ldpService.GetUrlCompilazioneDomanda(this.UserAuthenticationResult.Token, this.IdDomanda);

                Response.Redirect(redirUrl);
                Response.End();
            }

            this._ldpService.AggiornaDatiLocalizzazione(this.IdDomanda);

            this.Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}