using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.EditFormControls
{
    public class DropDownItem
    {
        public string Text { get; set; }
        public string Value { get; set; }
        public Dictionary<string, object> AdditionalValues { get; set; }
    }
}
