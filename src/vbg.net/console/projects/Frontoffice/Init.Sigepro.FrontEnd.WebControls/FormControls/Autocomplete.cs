using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public class Autocomplete : TextBox
    {
        System.Web.UI.WebControls.HiddenField _hiddenField = new System.Web.UI.WebControls.HiddenField();

        public override string ID
        {
            get
            {
                return base.ID;
            }
            set
            {
                base.ID = value;
                this._hiddenField.ID = base.ID + "_hidden";
            }
        }

        public string Value
        {
            get { return this._hiddenField.Value; }
            set { this._hiddenField.Value = value; }
        }

        public string HiddenClientID
        {
            get 
            {
                return this._hiddenField.ClientID;
            }
        }


        protected override void CreateChildControls()
        {
            base.CreateChildControls();

            this.Controls.Add(this._hiddenField);
            this.GlyphType = "search";
        }
    }
}
