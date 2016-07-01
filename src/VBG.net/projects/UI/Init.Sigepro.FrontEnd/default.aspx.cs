using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;

namespace Init.Sigepro.FrontEnd
{
	public partial class _default : System.Web.UI.Page
	{
		protected void Page_Load(object sender, EventArgs e)
		{
			var alias = Request.QueryString["alias"];
			var software = Request.QueryString["software"];

			new RedirectService(HttpContext.Current).RedirectToHomeContenuti(alias, software);
		}
	}
}
