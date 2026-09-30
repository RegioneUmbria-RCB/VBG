using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public interface IControlWithLabel
    {
        string Label { get; set; }
        string ID { get; }
    }
}
