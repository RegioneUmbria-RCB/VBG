using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public class RisorsaTestualeLabel : Label
    {
        [Inject]
        public IRisorseTestualiService _risorseService { get; set; }

        public string ValoreDefault
        {
            get { object o = this.ViewState["ValoreDefault"]; return o == null ? this.ClientID : (string)o; }
            set { this.ViewState["ValoreDefault"] = value; }
        }

        public string IdRisorsa
        {
            get { object o = this.ViewState["IdRisorsa"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["IdRisorsa"] = value; }
        }

        public string ExtraCssclass
        {
            get { object o = this.ViewState["ExtraCssclass"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ExtraCssclass"] = value; }
        }




        public RisorsaTestualeLabel()
        {

        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            FoKernelContainer.Inject(this);
        }

        protected override void OnPreRender(EventArgs e)
        {
            var idRisorsa = String.IsNullOrEmpty(IdRisorsa) ? this.ClientID : IdRisorsa;
            var valoreDefault = string.IsNullOrEmpty(this.Text) ? this.ValoreDefault : this.Text;

            this.Text = this._risorseService.GetRisorsa(idRisorsa, valoreDefault);
            this.CssClass = "risorsa-testuale";

            if (!String.IsNullOrEmpty(this.ExtraCssclass))
            {
                this.CssClass += " " + this.ExtraCssclass;
            }
            this.Attributes.Add("data-id-risorsa", idRisorsa);

            base.OnPreRender(e);
        }
    }
}
