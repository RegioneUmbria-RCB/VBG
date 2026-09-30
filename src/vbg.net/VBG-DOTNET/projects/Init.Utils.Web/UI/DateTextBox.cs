using System;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

[assembly: System.Web.UI.WebResource("Init.Utils.Web.UI.DateTextBoxChecker.js", "text/javascript")]

namespace Init.Utils.Web.UI
{
    /// <summary>
    /// Descrizione di riepilogo per DateTextBox.
    /// </summary>
    [DefaultProperty("DateValue"),
        ToolboxData("<{0}:DateTextBox runat=server />")]
    public class DateTextBox : TextBox
    {
        private readonly string scriptName = "isValidDate";


        public DateTime? DateValue
        {
            set
            {
                this.Text = value.HasValue && value != DateTime.MinValue ? value.Value.ToString("dd/MM/yyyy") : String.Empty;
            }

            get
            {
                try
                {
                    return DateTime.ParseExact(this.Text, "dd/MM/yyyy", null);
                }
                catch (Exception /*ex*/)
                {
                    return null;
                }
            }
        }

        public string ReverseDateValue
        {
            get
            {
                return this.DateValue.HasValue ? this.DateValue.Value.ToString("yyyyMMdd") : String.Empty;
            }

            set
            {
                try
                {
                    if (String.IsNullOrEmpty(value))
                        this.DateValue = null;
                    else
                        this.DateValue = DateTime.ParseExact(value, "yyyyMMdd", null);
                }
                catch (Exception) { }
            }
        }

        [Category("Aspetto"),
            DefaultValue("Formato della data non valido"),
            Description("Messaggio di errore che viene mostrato quando il formato non è valido")]
        public string ErrorMessage
        {
            get
            {
                object o = this.ViewState["ErrMessage"];
                return (null == o) ? "Formato della data non valido" : (string)o;
            }
            set { this.ViewState["ErrMessage"] = value; }
        }


        public DateTextBox()
        {
            this.Columns = 12;
            this.MaxLength = 10;
        }

        protected override void OnInit(EventArgs e)
        {
            this.RegisterJs();

            base.OnInit(e);
        }


        protected override void Render(HtmlTextWriter writer)
        {
            this.MaxLength = 10;
            this.Attributes.Add("onblur", "isValidDate(this,true)");

            base.Render(writer);
        }


        private void RegisterJs()
        {
            if (!this.Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "DateTextBoxChecker"))
            {
                this.Page.ClientScript.RegisterClientScriptInclude(
                   this.GetType(), "DateTextBoxChecker",
                   this.Page.ClientScript.GetWebResourceUrl(this.GetType(),
                   "Init.Utils.Web.UI.DateTextBoxChecker.js"));
            }
        }
    }


}
