using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public class LabeledLabel : BootstrapFormControl<Label>
    {
        protected override Label CreateInnerControl()
        {
            return new Label();
        }

        public string Text
        {
            get
            {
                return this.Value;
            }
            set
            {
                this.Value = value;
            }
        }

        public override string Value
        {
            get
            {
                return this.Inner.Text;
            }
            set
            {
                this.Inner.Text = value;
            }
        }
    }
}
