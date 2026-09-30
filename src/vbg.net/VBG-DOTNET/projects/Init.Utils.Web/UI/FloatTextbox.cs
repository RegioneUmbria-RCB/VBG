using System;
using System.ComponentModel;
using System.Globalization;
using System.Threading;
using System.Web.UI;
using System.Web.UI.WebControls;

[assembly: System.Web.UI.WebResource("Init.Utils.Web.UI.FloatTextBoxChecker.js", "text/javascript")]

namespace Init.Utils.Web.UI
{
    [DefaultProperty("FloatValue"),
       ToolboxData("<{0}:FloatTextBox runat=server />")]
    public class FloatTextBox : TextBox
    {/*
		private string scriptName = "isValidFloat";

		[Browsable(true), Category("Behaviour"),Description("Il messaggio di errore che deve essere mostrato in caso di valore numerico non valido")]
		public string MessaggioErrore
		{
			get
			{
				object o = this.ViewState["ErrMessage"];
				return (null == o) ? "Valore numerico non valido" : (string)o;
			}
			set { this.ViewState["ErrMessage"] = value; }
		}
		*/
        public string ErrorMessage
        {
            get { object o = this.ViewState["ErrorMessage"]; return o == null ? "Valore numerico non valido" : (string)o; }
            set { this.ViewState["ErrorMessage"] = value; }
        }


        [Browsable(true), Category("Behaviour"), Description("Espressione di formattazione del valore numerico")]
        public string FormatString
        {
            get { object o = this.ViewState["FloatFormatString"]; return o == null ? "N2" : o.ToString(); }
            set { this.ViewState["FloatFormatString"] = value; }
        }

        [Browsable(true), Category("Behaviour"), Description("Separatore cifre decimali")]
        public string DecimalSeparator
        {
            get { object o = this.ViewState["DecimalSeparator"]; return o == null ? "," : o.ToString(); }
            set { this.ViewState["DecimalSeparator"] = value; }
        }

        [Browsable(true), Category("Behaviour"), Description("Valore del controllo sotto forma di numero")]
        public float ValoreFloat
        {
            get
            {
                if (String.IsNullOrEmpty(this.Text))
                    return float.MinValue;

                CultureInfo oldCi = null;
                try
                {
                    oldCi = Thread.CurrentThread.CurrentCulture;

                    // Il parsing del valore deve essere effettuato utilizzando lo stesso cultureinfo dell'interfaccia grafica
                    Thread.CurrentThread.CurrentCulture = Thread.CurrentThread.CurrentUICulture;

                    return float.Parse(this.Text);
                }
                catch (Exception /*ex*/)
                {
                    return float.MinValue;
                }
                finally
                {
                    if (oldCi != null)
                        Thread.CurrentThread.CurrentCulture = oldCi;
                }
            }
            set
            {
                CultureInfo oldCi = null;
                try
                {
                    if (Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator != this.DecimalSeparator)
                    {
                        oldCi = Thread.CurrentThread.CurrentCulture;

                        CultureInfo newCi = new CultureInfo("it-IT");
                        newCi.NumberFormat.NumberDecimalSeparator = this.DecimalSeparator;
                        newCi.NumberFormat.NumberGroupSeparator = this.DecimalSeparator == "," ? "." : ",";

                        Thread.CurrentThread.CurrentCulture = newCi;
                    }

                    this.Text = value == float.MinValue ? String.Empty : value.ToString(this.FormatString);
                }
                finally
                {
                    if (oldCi != null)
                        Thread.CurrentThread.CurrentCulture = oldCi;
                }
            }
        }

        public override int Columns
        {
            get
            {
                return base.Columns;
            }
            set
            {
                base.Columns = value;
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
            this.Attributes.Add("onblur", "g_numberValidator.ValidateFloat(this)");

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, "g_numberValidator.Initialize( document.getElementById('" + this.ClientID + "'),'" + this.ErrorMessage + "' );", true);
        }
    }
}
