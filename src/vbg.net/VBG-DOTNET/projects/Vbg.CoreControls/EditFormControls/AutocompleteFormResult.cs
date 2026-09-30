using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.EditFormControls
{
    public class AutocompleteFormResult
    {
        public string Text { get; set; }
        public string Value { get; set; }
        public List<string> AddictionalValues { get; set; }

        public AutocompleteFormResult(string value, string text)
        {
            Text = text;
            Value = value;
        }

        public AutocompleteFormResult(string value, string text, params string[] additionalValues)
        {
            Text = text;
            Value = value;

            if (AddictionalValues == null)
                AddictionalValues = new List<string>();

            foreach (string val in additionalValues)
            {
                AddictionalValues.Add(val);
            }
        }

        public bool IsNulOrEmpty()
        {
            return string.IsNullOrEmpty(Text) && string.IsNullOrEmpty(Value);
        }
    }
}
