using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class AtecoAlbero : BasePage
	{
		public override string Software
		{
			get
			{
				var sw = Request.QueryString["Software"];

				if (String.IsNullOrEmpty(sw))
					return "SS";

				return sw;
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
		}

		public void alberoAteco_FogliaSelezionata(object sender, int idAteco)
		{
			Response.Redirect("~/Public/AlberoInterventi.aspx?IdComune=" + IdComune + "&Software=" + Software + "&IdAteco=" + idAteco + "&popup=true");
		}
	}
}
