using Init.SIGePro.Authentication;
using System;

namespace Sigepro.net
{
    public partial class tokenplease : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void cmdStacca_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(txtAlias.Text))
            {
                lblOutput.Text = "Specifica un alias";

                return;
            }

            var authInfo = AuthenticationManager.LoginApplicativo(txtAlias.Text);

            lblOutput.Text = authInfo.Token;
        }
    }
}