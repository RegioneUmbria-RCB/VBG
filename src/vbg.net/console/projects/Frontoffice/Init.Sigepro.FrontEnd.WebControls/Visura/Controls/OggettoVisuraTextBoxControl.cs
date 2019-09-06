using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
    public class OggettoVisuraTextBoxControl : VisuraTextBoxControl
    {
        public new string Value
        {
            get
            {
                return String.Format("%{0}%",base.Value);                
            }
        }
    }
}
