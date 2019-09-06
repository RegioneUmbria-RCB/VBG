using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public partial class DatiInviatiConSuccesso : ReservedBasePage
	{
		protected void Page_Load(object sender, EventArgs e)
		{

		}

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            Redirect("~/reserved/benvenuto.aspx", x => { });
        }
    }
}