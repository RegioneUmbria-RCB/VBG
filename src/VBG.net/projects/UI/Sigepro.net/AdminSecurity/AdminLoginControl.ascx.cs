using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;

namespace Sigepro.net.AdminSecurity
{
	public partial class AdminLoginControl : System.Web.UI.UserControl
	{
		public event EventHandler AuthenticationSucceded;
		public event EventHandler AuthenticationAborted;

		protected void Page_Load(object sender, EventArgs e)
		{
			/*if (AdminSecurityManager.IsCurrentUserAdmin && AuthenticationSucceded != null)
			{
				AuthenticationSucceded(this, EventArgs.Empty);
			}*/
		}

		protected void cmdOk_Click(object sender, EventArgs e)
		{
			BasePage page = (BasePage)this.Page;

			if (AdminSecurityManager.LogonAsAdmin(page.AuthenticationInfo.Alias, txtAdminUsername.Value, txtAdminPassword.Value))
			{
				if (AuthenticationSucceded != null)
					AuthenticationSucceded(this, EventArgs.Empty);
			}
			else
			{
				string js = "alert('Nome utente o password errati');";

				this.Page.ClientScript.RegisterStartupScript(this.GetType(), "passErrata", js, true);
			}
		}

		protected void cmdAnnulla_Click(object sender, EventArgs e)
		{
			if (AuthenticationAborted != null)
				AuthenticationAborted(this, EventArgs.Empty);
		}
	}
}