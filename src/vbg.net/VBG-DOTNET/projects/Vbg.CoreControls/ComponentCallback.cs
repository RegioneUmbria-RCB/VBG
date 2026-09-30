using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls
{
    public class ComponentCallback
    {
        public Action<Dictionary<string, object>> ActionWithParameters { get; set; }

    }
}
