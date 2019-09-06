using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class testo_libero : IstanzeStepPage
    {
        private const string LoremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. <br /><br />Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.<br /><br /> Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        public string Testo
        {
            get { object o = this.ViewState["Testo"]; return o == null ? LoremIpsum : (string)o; }
            set { this.ViewState["Testo"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.IgnoraSalvataggioDati = true;
        }
    }
}