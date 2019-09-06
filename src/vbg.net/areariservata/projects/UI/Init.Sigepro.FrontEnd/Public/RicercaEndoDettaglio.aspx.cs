using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Contenuti;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class RicercaEndoDettaglio : ContenutiBasePage
	{
		public string Id
		{
			get { return Request.QueryString["Id"]; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}

		protected void cmdClose_Click(object sender, EventArgs e)
		{
			Response.Redirect("~/Public/RicercaEndo.aspx?IdComune=" + IdComune + "&Software=" + Software);
		}

		public string GetUrlStampaPagina()
		{
			return GetBaseUrlAssoluto() + "Public/MostraDettagliEndo.aspx?idComune=" + IdComune + "&Id=" + Id + "&Print=true";
		}

		public string GetUrlDownloadPagina()
		{
			var downloadUrl = GetUrlStampaPagina();

			return ResolveClientUrl("~/Public/DownloadPage.ashx") + "?IdComune=" + IdComune + "&url=" + Server.UrlEncode(downloadUrl);
		}
	}
}