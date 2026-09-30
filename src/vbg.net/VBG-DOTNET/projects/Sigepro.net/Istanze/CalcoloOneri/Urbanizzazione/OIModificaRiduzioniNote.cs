using SIGePro.WebControls.UI;
using System;
using System.Web.UI.WebControls;

[assembly: System.Web.UI.WebResource("Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificaRiduzioniNoteBehavior.js", "text/javascript")]

namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
    public class OIModificaRiduzioniNote : Panel
    {
        private readonly TextBox m_textbox = new TextBox();
        private readonly SigeproButton m_closeButton = new SigeproButton();
        private readonly Literal m_literal = new Literal();

        public string Text
        {
            get { return this.m_textbox.Text; }
            set { this.m_textbox.Text = value; }
        }

        public string Titolo
        {
            get { var o = this.ViewState["Titolo"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["Titolo"] = value; }
        }


        public string AssociatedControlId
        {
            get { var o = this.ViewState["AssociatedControlId"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["AssociatedControlId"] = value; }
        }

        public string ActivationControlId
        {
            get { var o = this.ViewState["ActivationControlId"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ActivationControlId"] = value; }
        }

        public OIModificaRiduzioniNote()
        {
            var backdrop = new Panel();
            backdrop.CssClass = "controls-container";

            this.m_textbox.TextMode = TextBoxMode.MultiLine;
            this.m_textbox.Rows = 4;

            this.m_closeButton.IdRisorsa = "OK";
            this.CssClass = "controllo-modifica-note";

            var brLiteral = new Literal();
            brLiteral.Text = "<br />";

            backdrop.Controls.Add(this.m_literal);
            backdrop.Controls.Add(this.m_textbox);
            backdrop.Controls.Add(this.m_closeButton);
            this.Controls.Add(backdrop);
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            this.RegisterWebResource();
        }

        protected override void OnPreRender(EventArgs e)
        {
            var imgBtn = (ImageButton)this.NamingContainer.FindControl(this.ActivationControlId);
            var associatedControl = (WebControl)this.NamingContainer.FindControl(this.AssociatedControlId);

            this.m_literal.Text = "<h3>Note della causale \"" + this.Titolo + "\"</h3>";
            this.m_closeButton.OnClientClick = "document.getElementById('" + this.ClientID + "').style.visibility = 'hidden';return false;";

            this.RegisterStartupScript(imgBtn, associatedControl);
        }

        private void RegisterWebResource()
        {
            if (!this.Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "OIModificaRiduzioniNoteBehavior"))
            {
                this.Page.ClientScript.RegisterClientScriptInclude(
                   this.GetType(), "OIModificaRiduzioniNoteBehavior",
                   this.Page.ClientScript.GetWebResourceUrl(this.GetType(),
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
            var script = @"g_oIModificaRiduzioniNoteMgr.AddControl( '{0}','{1}','{2}' );";

            script = String.Format(script, this.ClientID, imgBtn.ClientID, associatedControl.ClientID);

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, script, true);
        }
    }
}
