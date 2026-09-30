using System;

namespace Init.Sigepro.FrontEnd
{
    public partial class fv : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
                cmdRicarica_click(this, EventArgs.Empty);
        }

        protected void cmdOk_click(object sender, EventArgs e)
        {
            Application["APPUNTI"] = txtTesto.Text;
        }

        protected void cmdRicarica_click(object sender, EventArgs e)
        {
            txtTesto.Text = Application["APPUNTI"] == null ? String.Empty : Application["APPUNTI"].ToString();
        }


    }
}