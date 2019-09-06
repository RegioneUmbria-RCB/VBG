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

public partial class ServerVars : System.Web.UI.Page
{
	protected void Page_Load(object sender, EventArgs e)
	{
		this.PreRender += new EventHandler(ServerVars_PreRender);
	}

	void ServerVars_PreRender(object sender, EventArgs e)
	{

		foreach (string s in Request.ServerVariables)
			Response.Write("<b>" + s + "</b> = " + Request.ServerVariables[s] + "<br>"); 
	}
}
