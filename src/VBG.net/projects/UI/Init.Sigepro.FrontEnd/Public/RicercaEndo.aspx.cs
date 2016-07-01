using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Contenuti;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class RicercaEndo : ContenutiBasePage
	{
		protected void Page_Load(object sender, EventArgs e)
		{

		}

		protected void EndoSelezionato(object sender, int idEndo)
		{
			Response.Redirect("~/Public/RicercaEndoDettaglio.aspx?IdComune=" + IdComune + "&Software=" + Software + "&Id=" + idEndo);
		}
	}
}