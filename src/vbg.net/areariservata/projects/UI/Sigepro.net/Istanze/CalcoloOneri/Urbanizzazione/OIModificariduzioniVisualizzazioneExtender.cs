using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Utils.Web.UI;

[assembly: System.Web.UI.WebResource("Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificariduzioniVisualizzazioneExtenderBehavior.js", "text/javascript")]


namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
	public class OIModificariduzioniVisualizzazioneExtender : WebControl
	{
		public string CheckBoxId
		{
			get { object o = this.ViewState["CheckBoxId"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["CheckBoxId"] = value; }
		}

		public string DoubleTextBoxId
		{
			get { object o = this.ViewState["DoubleTextBoxId"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["DoubleTextBoxId"] = value; }
		}

		public string ImageButtonId
		{
			get { object o = this.ViewState["ImageButtonId"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["ImageButtonId"] = value; }
		}

		public double Riduzione
		{
			get { object o = this.ViewState["Riduzione"]; return o == null ? 0.0d : (double)o; }
			set { this.ViewState["Riduzione"] = value; }
		}

		protected override void OnInit(EventArgs e)
		{
			base.OnInit(e);

			if (!this.Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "OIModificariduzioniVisualizzazioneExtenderBehavior"))
			{
				this.Page.ClientScript.RegisterClientScriptInclude(
				   this.GetType(), "OIModificariduzioniVisualizzazioneExtenderBehavior",
				   Page.ClientScript.GetWebResourceUrl(this.GetType(),
				   "Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificariduzioniVisualizzazioneExtenderBehavior.js"));
			}
		}

		protected override void OnPreRender(EventArgs e)
		{
			base.OnPreRender(e);

			ImageButton imgBtn = (ImageButton)NamingContainer.FindControl(ImageButtonId);
			CheckBox chkBox = (CheckBox)NamingContainer.FindControl(CheckBoxId);
			DoubleTextBox dblTextbox = (DoubleTextBox)NamingContainer.FindControl(DoubleTextBoxId);

			string script = "g_visualizzazioneExtender.AddNew('{0}','{1}','{2}','{3}','{4}');";
			script = String.Format(script, this.ClientID, chkBox.ClientID, dblTextbox.ClientID, imgBtn.ClientID , Riduzione.ToString("N2"));

			this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, script, true);
		}
	}
}
