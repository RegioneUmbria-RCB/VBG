using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls
{
    public interface IAutocompleteResult
    {
        string Value { get; set; }
        string Text { get; set; }
        public string TextPrefix { get; set; }
    }
}
