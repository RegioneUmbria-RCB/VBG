using Init.Utils.Web.UI;
using System;
using System.Web.UI.WebControls;

[assembly: System.Web.UI.WebResource("Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificariduzioniVisualizzazioneExtenderBehavior.js", "text/javascript")]


namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
    public class OIModificariduzioniVisualizzazioneExtender : WebControl
    {
        public string CheckBoxId
        {
            get { var o = this.ViewState["CheckBoxId"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CheckBoxId"] = value; }
        }

        public string DoubleTextBoxId
        {
            get { var o = this.ViewState["DoubleTextBoxId"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["DoubleTextBoxId"] = value; }
        }

        public string ImageButtonId
        {
            get { var o = this.ViewState["ImageButtonId"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ImageButtonId"] = value; }
        }

        public decimal Riduzione
        {
            get { var o = this.ViewState["Riduzione"]; return o == null ? 0.0m : (decimal)o; }
            set { this.ViewState["Riduzione"] = value; }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            if (!this.Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "OIModificariduzioniVisualizzazioneExtenderBehavior"))
            {
                this.Page.ClientScript.RegisterClientScriptInclude(
                   this.GetType(), "OIModificariduzioniVisualizzazioneExtenderBehavior",
                   this.Page.ClientScript.GetWebResourceUrl(this.GetType(),
                   "Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificariduzioniVisualizzazioneExtenderBehavior.js"));
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            var imgBtn = (ImageButton)this.NamingContainer.FindControl(this.ImageButtonId);
            var chkBox = (CheckBox)this.NamingContainer.FindControl(this.CheckBoxId);
            var dblTextbox = (DecimalTextBox)this.NamingContainer.FindControl(this.DoubleTextBoxId);

            var script = "g_visualizzazioneExtender.AddNew('{0}','{1}','{2}','{3}','{4}');";
            script = String.Format(script, this.ClientID, chkBox.ClientID, dblTextbox.ClientID, imgBtn.ClientID, this.Riduzione.ToString("N2"));

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, script, true);
        }
    }
}
