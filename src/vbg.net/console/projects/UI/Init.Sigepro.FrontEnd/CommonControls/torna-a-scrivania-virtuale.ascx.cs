using Init.Sigepro.FrontEnd.AppLogic.GestioneRitornoScrivaniaVirtuale;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class torna_a_scrivania_virtuale : System.Web.UI.UserControl
    {
        [Inject]
        public RitornoScrivaniaVirtualeService _ritornoScrivaniaVirtualeService { get; set; }

        public string CssClass
        {
            get { return this.tornaAScrivania.CssClass; }
            set { this.tornaAScrivania.CssClass = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            FoKernelContainer.Inject(this);

            this.Visible = this._ritornoScrivaniaVirtualeService.UrlRitornoDefinito;
        }

        protected void Unnamed_Click(object sender, EventArgs e)
        {
            var url = this._ritornoScrivaniaVirtualeService.GetUrlRitorno(true);

            Response.Redirect(url);
        }
    }
}