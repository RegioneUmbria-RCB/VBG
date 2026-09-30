using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls
{
    public class AutocompleteResult : IAutocompleteResult
    {
        public string Text { get; set; }
        public string Value { get; set; }
        public string TextPrefix { get; set; }

        public AutocompleteResult(string value, string text, string textPrefix = null) : base()
        {
            Text = text;
            Value = value;
            TextPrefix = textPrefix;
        }
    }
}
