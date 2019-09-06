using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public interface IBootstrapFormControl
    {
        string Label { get; set; }
        bool Required { get; set; }
        string Value { get; set; }
        bool Visible { get; set; }
        string ClientID { get; }
    }
}
