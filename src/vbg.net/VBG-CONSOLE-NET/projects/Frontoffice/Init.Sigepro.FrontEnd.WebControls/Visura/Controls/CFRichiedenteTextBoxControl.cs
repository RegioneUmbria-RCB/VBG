using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
    public class CFRichiedenteTextBoxControl : VisuraTextBoxControl
    {

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            base.MaxLength = 16;
            base.Columns = 20;
            base.Attributes.Add("placeholder", "Codice fiscale richiedente");
        }

        public new string Value
        {
            get
            {
                return (this.GetInnerControl() as TextBox).Text.ToUpper();
            }
        }
    }
}
