using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class FileDownload : System.Web.UI.UserControl
    {
        public object DataSource { get; set; }

        public string IdComune
        {
            get
            {
                return Request.QueryString["idcomune"];
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }
    }
}