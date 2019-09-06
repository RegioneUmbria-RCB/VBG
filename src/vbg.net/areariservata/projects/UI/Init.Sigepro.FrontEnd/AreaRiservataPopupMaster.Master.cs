using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using System.Linq;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd
{
	public partial class AreaRiservataPopupMaster : BaseAreaRiservataMaster
	{
        public bool MostraTitoloPagina
        {
            get { return lblTitoloPagina.Visible; }
            set { lblTitoloPagina.Visible = value; }
        }

        protected override void OnInit(EventArgs e)
		{
			base.OutputErrori = rptErrori;

			base.OnInit(e);
		}

        protected void Page_Load(object sender, EventArgs e)
		{

		}

		protected override void OnPreRender(EventArgs e)
		{
			if (!IsPostBack)
				lblTitoloPagina.Text = this.Page.Title;

			if (String.IsNullOrEmpty(lblTitoloPagina.Text))
				pnlTitolo.Visible = false;
		}
	}
}
