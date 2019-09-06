using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public class DateTextBox : BootstrapFormControl<Init.Utils.Web.UI.DateTextBox>
    {
        public bool ReadOnly
        {
            get { return this._innerControl.ReadOnly; }
            set { this._innerControl.ReadOnly = value; }
        }

        public DateTime? DateValue
        {
            get { return this._innerControl.DateValue; }
            set { this._innerControl.DateValue = value; }
        }

        public string MaxDate
        {
            get { object o = this.ViewState["MaxDate"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["MaxDate"] = value; }
        }


        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            this.GlyphType = "calendar";
            this.Placeholder = "gg/mm/aaaa";

            this.Inner.CssClass += " date-text-box";
            this.Inner.Attributes.Add("autocomplete","off");
            this.GroupCssClass = "date";
        }

        protected override Utils.Web.UI.DateTextBox CreateInnerControl()
        {
            return new Init.Utils.Web.UI.DateTextBox();
        }

        protected override void OnPreRender(EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.MaxDate))
            {
                this.Inner.Attributes.Add("data-date-use-current", "false");
                this.Inner.Attributes.Add("data-date-max-date", this.MaxDate);
            }


            base.OnPreRender(e);
        }

        protected override IEnumerable<KeyValuePair<string, string>> GetGroupAttributes()
        {
            yield return new KeyValuePair<string, string>("data-provide", "datepicker");
        }

        public string Text
        {
            get
            {
                return this._innerControl.Text;
            }

            set { this._innerControl.Text = value; }
        }

        public override string Value
        {
            get { return this.Text; }
            set { this.Text = value; }
        }
    }
}
