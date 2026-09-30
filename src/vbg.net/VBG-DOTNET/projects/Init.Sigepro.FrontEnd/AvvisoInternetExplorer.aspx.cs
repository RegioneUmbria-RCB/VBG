using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd
{
    public partial class AvvisoInternetExplorer : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public string LoadScripts(string[] scripts)
        {
            var s = scripts.Select(x => $"<script type='text/javascript' src='{ResolveClientUrl(x)}'></script>");

            return String.Join(Environment.NewLine, s.ToArray());
        }
    }
}