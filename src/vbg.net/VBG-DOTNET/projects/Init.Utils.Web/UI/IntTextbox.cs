using System;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
    [DefaultProperty("IntValue"),
       ToolboxData("<{0}:IntTextBox runat=server />")]
    public class IntTextBox : TextBox
    {
        public string ErrorMessage
        {
            get { object o = this.ViewState["ErrorMessage"]; return o == null ? "Valore numerico non valido" : (string)o; }
            set { this.ViewState["ErrorMessage"] = value; }
        }

        [Browsable(true), Category("Behaviour"), Description("Valore del controllo sotto forma di numero")]
        public int? ValoreInt
        {
            get
            {
                if (String.IsNullOrEmpty(this.Text))
                    return null;

                try
                {
                    int val = int.Parse(this.Text);

                    if (val == int.MinValue) return null;

                    return val;
                }
                catch (Exception /*ex*/)
                {
                    return null;
                }
            }
            set
            {
                if (value == null || value == int.MinValue)
                {
                    this.Text = string.Empty;
                    return;
                }

                this.Text = value.ToString();
            }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            if (!this.Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "FloatTextBoxChecker"))
            {
                this.Page.ClientScript.RegisterClientScriptInclude(
                   this.GetType(), "FloatTextBoxChecker",
                   this.Page.ClientScript.GetWebResourceUrl(this.GetType(),
                   "Init.Utils.Web.UI.FloatTextBoxChecker.js"));
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            this.Attributes.CssStyle.Add("text-align", "right");
            this.Attributes.Add("onblur", "g_numberValidator.ValidateInt(this)");
            this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, "g_numberValidator.Initialize( document.getElementById('" + this.ClientID + "'),'" + this.ErrorMessage + "' );", true);
        }


    }
}
