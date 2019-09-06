using Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneVerificaSoggettiFirmatari : IstanzeStepPage
    {
        [Inject]
        protected VerificaSoggettiFirmatariService _verificaSoggettiFirmatariService { get; set; }

        public bool Bloccante
        {
            get { object o = this.ViewState["Bloccante"]; return o == null ? false : (bool)o; }
            set { this.ViewState["Bloccante"] = value; }
        }

        protected EsitoVerificaSoggetti EsitoVerifica { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override bool CanEnterStep()
        {
            this.EsitoVerifica = this._verificaSoggettiFirmatariService.Verifica(IdDomanda);

            if (!this.EsitoVerifica.VerificaRiuscita && Bloccante)
            {
                this.Master.MostraBottoneAvanti = false;
            }

            return !this.EsitoVerifica.VerificaRiuscita;
        }

        public override void DataBind()
        {
            
        }
    }
}