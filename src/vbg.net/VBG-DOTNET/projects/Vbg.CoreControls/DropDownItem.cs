using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls
{
    public class DropDownItemComponent
    {
        public string Value;
        public string Text;
        public string ImageUrl;
        public bool IsSelected;

        public DropDownItemComponent(string value, string text, string imageUrl = "", bool isSelected = false) : base()
        {
            this.Value = value;
            this.Text = text;
            this.ImageUrl = imageUrl;
            this.IsSelected = isSelected;
        }

        public DropDownItemComponent(KeyValuePair<string, string> keyValuePair) : base()
        {
            this.Value = keyValuePair.Key;
            this.Text = keyValuePair.Value;
            this.ImageUrl = "";
            this.IsSelected = false;
        }
    }
}
