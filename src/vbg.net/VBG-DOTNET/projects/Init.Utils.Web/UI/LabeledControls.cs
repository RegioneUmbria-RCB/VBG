using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
    [ToolboxData("<{0}:LabeledTextBox runat=\"server\"/>"),
   ValidationProperty("Value"),
  DefaultProperty("Value"),
 ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledTextBox : LabeledControlBase//<System.Web.UI.WebControls.TextBox>
    {
        [Browsable(true),
        DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public TextBox Item
        {
            get { return this.GetInnerControl<TextBox>(); }
        }

        [Browsable(true),
        DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public int MaxLength
        {
            get { return this.GetInnerControl<TextBox>().MaxLength; }
            set { this.GetInnerControl<TextBox>().MaxLength = value; }
        }

        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public string Pattern
        {
            set { this.GetInnerControl<TextBox>().Attributes.Add("pattern", value); }
            get { return this.GetInnerControl<TextBox>().Attributes["pattern"] ?? ""; }
        }

        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public string DataListId
        {
            set { this.GetInnerControl<TextBox>().Attributes["list"] = value; }
            get { return this.GetInnerControl<TextBox>().Attributes["list"] ?? ""; }
        }

        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public bool Required
        {
            set
            {
                var inner = this.GetInnerControl<TextBox>();
                if (value)
                {
                    inner.Attributes.Add("required", "required");
                }
                else
                {
                    inner.Attributes.Remove("required");
                }
            }
            get { return this.GetInnerControl<TextBox>().Attributes.Contains("required"); }
        }


        public string FilterInput
        {
            get => this.GetInnerControl<TextBox>().Attributes["data-filter-input"];
            set => this.GetInnerControl<TextBox>().Attributes["data-filter-input"] = value;
        }

        protected override WebControl CreateInnerControl()
        {
            return new TextBox();
        }
    }


    public static class AttributeCollectionExtensions
    {
        public static bool Contains(this System.Web.UI.AttributeCollection collection, string attributeName)
        {
            foreach (var key in collection.Keys)
            {
                if (string.Equals(key.ToString(), attributeName, System.StringComparison.OrdinalIgnoreCase))
                {
                    return true;
                }
            }

            return false;
        }

    }
}
