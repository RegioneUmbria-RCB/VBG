using System;
using System.ComponentModel;
//using System.Drawing.Design;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
    [ToolboxData("<{0}:HelpDiv runat=server></{0}:HelpDiv>")]
    [DefaultProperty("Text"), ParseChildren(false)]
    public partial class HelpDiv : WebControl
    {
        [Bindable(true), Category("Appearance"), DefaultValue(""), PersistenceMode(PersistenceMode.EncodedInnerDefaultProperty)]
        public string Text
        {
            get
            {
                String s = (String)this.ViewState["Text"];
                return ((s == null) ? String.Empty : s);
            }

            set
            {
                this.ViewState["Text"] = value;
            }
        }

        [Bindable(true), Category("Appearance"), DefaultValue("HelpControllo")]
        public override string CssClass
        {
            get
            {
                return String.IsNullOrEmpty(base.CssClass) ? "HelpControllo" : base.CssClass;
            }
            set
            {
                base.CssClass = value;
            }
        }


        [Bindable(true), Category("Appearance"), DefaultValue("~/Images/help_controllo.gif")]
        public string HelpIcon
        {
            get { object o = this.ViewState["HelpIcon"]; return o == null ? "~/Images/help_controllo.gif" : (string)o; }
            set { this.ViewState["HelpIcon"] = value; }
        }


        protected override void AddParsedSubObject(object obj)
        {
            if (!(obj is LiteralControl))
            {
                throw new HttpException("Controllo figlio non supportato: " + obj.GetType());
            }
            this.Text = ((LiteralControl)obj).Text;
        }



        protected override void Render(HtmlTextWriter output)
        {
            output.AddAttribute(HtmlTextWriterAttribute.Class, this.CssClass);
            output.AddAttribute(HtmlTextWriterAttribute.Id, this.ClientID);
            output.AddStyleAttribute(HtmlTextWriterStyle.Display, "none");
            output.AddStyleAttribute(HtmlTextWriterStyle.Position, "absolute");
            output.RenderBeginTag(HtmlTextWriterTag.Div);

            if (this.DesignMode)
            {
                if (String.IsNullOrEmpty(this.Text))
                    output.Write("[Help per controllo]");
                else
                    output.Write(this.Text);
            }
            else
            {
                output.Write(this.Text);
            }
            output.RenderEndTag();
        }
    }
}
