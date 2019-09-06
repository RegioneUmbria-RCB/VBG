using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneAidaSmart;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class aida_smart_nuova_domanda : ReservedBasePage
    {
        [Inject]
        protected IAidaSmartService _aidaService { get; set; }

        protected string RedirUrl { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.RedirUrl = this._aidaService.GetUrlNuovaDomanda(this.UserAuthenticationResult.DatiUtente);


        }
    }
}