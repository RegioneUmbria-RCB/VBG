using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class PaginaRicercaFoMaster : System.Web.UI.MasterPage
	{

        protected string LoadScripts(string[] scripts)
        {
            var s = scripts.Select(x => $"<script type='text/javascript' src='{ResolveClientUrl(x)}'></script>");

            return String.Join(Environment.NewLine, s.ToArray());
        }

        protected void Page_Load(object sender, EventArgs e)
		{

		}
	}
}