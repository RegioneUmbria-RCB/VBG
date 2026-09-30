using System;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.servizi
{
    public partial class login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            var alias = Page.RouteData.Values["alias"].ToString();
            var software = Page.RouteData.Values["software"].ToString();

            Response.Redirect(String.Format("~/Login.aspx?idcomune={0}&software={1}", alias, software));
        }
    }
}