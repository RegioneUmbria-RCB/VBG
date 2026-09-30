using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.WebControls.UI;

[assembly: System.Web.UI.WebResource("Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificaRiduzioniNoteBehavior.js", "text/javascript")]

namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
	public class OIModificaRiduzioniNote : WebControl
	{
		TextBox m_textbox = new TextBox();
		SigeproButton m_closeButton = new SigeproButton();
		Literal m_literal = new Literal();

		public string Text
		{
			get { return m_textbox.Text; }
			set { m_textbox.Text = value; }
		}

		public string Titolo
		{
			get { object o = this.ViewState["Titolo"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Titolo"] = value; }
		}
	

		public string AssociatedControlId
		{
			get { object o = this.ViewState["AssociatedControlId"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["AssociatedControlId"] = value; }
		}

		public string ActivationControlId
		{
			get { object o = this.ViewState["ActivationControlId"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["ActivationControlId"] = value; }
		}
		
		public OIModificaRiduzioniNote()
		{
			m_textbox.TextMode = TextBoxMode.MultiLine;
			m_textbox.Columns = 40;
			m_textbox.Rows = 4;

			m_closeButton.IdRisorsa = "OK";

			this.Style.Add(HtmlTextWriterStyle.Width, "350px");
			this.Style.Add("border", "1px solid #999");
			this.Style.Add("padding-left", "5px");
			this.Style.Add("padding-right", "5px");
			this.Style.Add("background-color", "#fff");
			this.Style.Add("clear", "both");

			Literal brLiteral = new Literal();
			brLiteral.Text = "<br />";

			m_textbox.Style.Add("float", "none");
			m_textbox.Style.Add("width", "98%");

			this.Controls.Add(m_literal);
			this.Controls.Add(m_textbox);
			this.Controls.Add(brLiteral);
			this.Controls.Add(m_closeButton);

		}

		protected override void OnInit(EventArgs e)
		{
			base.OnInit(e);

			RegisterWebResource();
		}

		protected override void OnPreRender(EventArgs e)
		{
			ImageButton imgBtn = (ImageButton)NamingContainer.FindControl(ActivationControlId);
			WebControl associatedControl = (WebControl)NamingContainer.FindControl(AssociatedControlId);
			
			m_literal.Text = "<b>Note della causale \"" + this.Titolo + "\"</b>";
			m_closeButton.OnClientClick = "document.getElementById('" + this.ClientID + "').style.visibility = 'hidden';return false;";

			RegisterStartupScript(imgBtn, associatedControl);
		}

		private void RegisterWebResource()
		{
			if (!this.Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "OIModificaRiduzioniNoteBehavior"))
			{
				this.Page.ClientScript.RegisterClientScriptInclude(
				   this.GetType(), "OIModificaRiduzioniNoteBehavior",
				   Page.ClientScript.GetWebResourceUrl(this.GetType(),
				   "Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificaRiduzioniNoteBehavior.js"));

//                string script = @"var req = Sys.WebForms.PageRequestManager.getInstance(); 
//req.add_endRequest(OnFinishedRequest); 
//req.add_beginRequest(OnStartedRequest); 
//
//function OnFinishedRequest(sender, args){ g_oIModificaRiduzioniNoteMgr.Update(); }
//function OnStartedRequest(sender, args){};";

//                this.Page.ClientScript.RegisterStartupScript(this.Page.GetType(), "registerAjaxPostback", script, true);
			}
		}

		private void RegisterStartupScript(ImageButton imgBtn, WebControl associatedControl)
		{
			string script = @"g_oIModificaRiduzioniNoteMgr.AddControl( '{0}','{1}','{2}' );";

			script = String.Format(script, this.ClientID, imgBtn.ClientID, associatedControl.ClientID);

			this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, script,true);
		}
	}
}
