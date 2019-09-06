using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    [ToolboxData("<{0}:TextBox runat='server' />")]
    public class TextBox : BootstrapFormControl<System.Web.UI.WebControls.TextBox>
    {
        public enum InputType
        {
            Qualsiasi,
            SoloTesto,
            SoloNumeri,
            NumeriETesto
        }

        public string Text
        {
            get
            {
                if (this.MaxLength > 0 && this._innerControl.Text.Length > this.MaxLength)
                {
                    return this._innerControl.Text.Substring(0, this.MaxLength);
                }

                return this._innerControl.Text;
            }
            set { this._innerControl.Text = value; }
        }

        public int MaxLength
        {
            get { return this._innerControl.MaxLength; }
            set { this._innerControl.MaxLength = value; }
        }

        public bool ReadOnly
        {
            get { return this._innerControl.ReadOnly; }
            set { this._innerControl.ReadOnly = value; }
        }

        public TextBoxMode TextMode
        {
            get { return this._innerControl.TextMode; }
            set { this._innerControl.TextMode = value; }
        }

        public int Rows
        {
            get { return this._innerControl.Rows; }
            set { this._innerControl.Rows = value; }
        }

        public InputType LimitaInput
        {
            get { object o = this.ViewState["LimitaInput"]; return o == null ? InputType.Qualsiasi : (InputType)o; }
            set { this.ViewState["LimitaInput"] = value; }
        }

        public string Pattern
        {
            get { object o = this.ViewState["Pattern"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["Pattern"] = value; }
        }



        public string ValidateDeferred
        {
            get { object o = this.ViewState["ValidateDeferred"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ValidateDeferred"] = value; }
        }


        protected override System.Web.UI.WebControls.TextBox CreateInnerControl()
        {
            return new System.Web.UI.WebControls.TextBox();
        }

        public override string Value
        {
            get { return this.Text; }
            set { this.Text = value; }
        }

        public string ValidationAttributes
        {
            get { object o = this.ViewState["ValidationAttributes"]; return o == null ? String.Empty : (string)o; }
            set
            {
                this.ViewState["ValidationAttributes"] = value;

                var parts = value.Split(';');

                foreach (var p in parts)
                {
                    var val = p.Split('=');

                    if (val.Length != 2) continue;

                    this.Inner.Attributes.Add(val[0], val[1]);
                }
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            if (this.LimitaInput != InputType.Qualsiasi)
            {
                var pattern = PatternDaInputType();
                this.Inner.Attributes.Add("pattern", pattern + "*");
                this.Inner.Attributes.Add("data-filtra-caratteri", pattern);
            }

            if (!String.IsNullOrEmpty(this.Pattern))
            {
                this.Inner.Attributes.Add("pattern", this.Pattern);
            }

            if (!String.IsNullOrEmpty(ValidateDeferred))
            {
                this.Inner.Attributes.Add("data-validate-deferred", ValidateDeferred);
            }
        }

        private string PatternDaInputType()
        {
            if (this.LimitaInput == InputType.SoloTesto)
            {
                return "[A-Za-z ]";
            }

            if (this.LimitaInput == InputType.SoloNumeri)
            {
                return "\\d";
            }

            if (this.LimitaInput == InputType.NumeriETesto)
            {
                return "[\\d\\w]";
            }

            return String.Empty;
        }

    }
}
