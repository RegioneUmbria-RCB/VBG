using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.Sit.silverbrowser
{
    public partial class SilverBrowserController : System.Web.UI.Page
    {


        public string Modo
        {
            get { return Request.QueryString["modo"]; }
        }

        public bool UseRemoteServer
        {
            get
            {
                return !String.IsNullOrEmpty(Request.QueryString["Remote"]);
            }
        }

        public string Foglio
        {
            get { return Request.QueryString["f"].PadLeft(4, '0'); }
        }

        public string Particella
        {
            get { return Request.QueryString["p"].PadLeft(5, '0'); }
        }

        public string CodiceVia
        {
            get { return Request.QueryString["v"].Replace("F844", "").TrimStart('0'); }
        }

        public string Civico
        {
            get { return Request.QueryString["c"]; }
        }

        public string Esponente
        {
            get { return Request.QueryString["e"]; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
        }
    }
}