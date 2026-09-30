using System;
using System.ComponentModel;
using System.Globalization;
using System.Threading;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
    [DefaultProperty("ValoreDouble"),
       ToolboxData("<{0}:DoubleTextBox runat=server />")]
    public class DoubleTextBox : TextBox
    {
        [Browsable(true), Category("Behaviour"), Description("Espressione di formattazione del valore numerico")]
        public string FormatString
        {
            get { var o = this.ViewState["FloatFormatString"]; return o == null ? "N2" : o.ToString(); }
            set { this.ViewState["FloatFormatString"] = value; }
        }

        [Browsable(true), Category("Behaviour"), Description("Separatore cifre decimali")]
        public string DecimalSeparator
        {
            get { var o = this.ViewState["DecimalSeparator"]; return o == null ? "," : o.ToString(); }
            set { this.ViewState["DecimalSeparator"] = value; }
        }

        [Browsable(true), Category("Behaviour"), Description("Valore del controllo sotto forma di numero")]
        public double ValoreDouble
        {
            get
            {
                if (String.IsNullOrEmpty(this.Text))
                    return double.MinValue;

                CultureInfo oldCi = null;
                try
                {
                    oldCi = Thread.CurrentThread.CurrentCulture;

                    // Il parsing del valore deve essere effettuato utilizzando lo stesso cultureinfo dell'interfaccia grafica
                    Thread.CurrentThread.CurrentCulture = Thread.CurrentThread.CurrentUICulture;

                    return double.Parse(this.Text);
                }
                catch (Exception /*ex*/)
                {
                    return double.MinValue;
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

                    this.Text = value < -1E37 ? String.Empty : value.ToString(this.FormatString);
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
            this.Attributes.Add("onload", "Debug.Write('Load')");

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, "g_numberValidator.Initialize( document.getElementById('" + this.ClientID + "') );", true);
        }


        /*
		protected override void Render(HtmlTextWriter writer)
		{
			//this.Attributes.Add("onblur", "isValidFloat(this)");
			this.Attributes.CssStyle.Add("text-align", "right");
			base.Render(writer);
		}
		*/
        /*
		private void RegisterJs()
		{
			// TODO: l'espressione regolare deve riconoscere il separatore di gruppo e il separatore dei decimali
			// dell'interfaccia grafica
			string script = @"
function isValidFloat(controllo)
{
	var patt = new RegExp( ""^(\\+|-)?(((\\d{1,3}\\.)*(\\d{3})(,\\d+)?)|(\\d+(,\\d+)?))$""  );
	
	if (controllo.value.indexOf(',') == -1)
	{
		var arr = controllo.value.split('.');
	
		if ( arr.length == 2 && arr[1].length != 3 )
			controllo.value = controllo.value.replace('.',',');
	}

	var valore = controllo.value;

	if (valore == """")	 return true;

	if ( !valore.match( patt ) )
	{
		alert(""" + MessaggioErrore + @""");
		controllo.value = """";
		controllo.focus();
		
		return false;
	}

	return true;
}";

			if (!this.Page.ClientScript.IsClientScriptBlockRegistered(scriptName))
			{
				this.Page.ClientScript.RegisterClientScriptBlock(typeof(string), scriptName, script, true);
			}
		}*/
    }
}
