using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
    [ToolboxData("<{0}:LabeledDecimalTextBox runat=\"server\"/>"),
      ValidationProperty("Value"),
     DefaultProperty("Value"),
    ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledDecimalTextBox : LabeledControlBase// : LabeledControl<DoubleTextBox>
    {
        private const string RequiredAttributeName = "required";

        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public DecimalTextBox Item
        {
            get { return this.GetInnerControl<DecimalTextBox>(); }
        }

        protected override WebControl CreateInnerControl()
        {
            return new DecimalTextBox();
        }

        public bool Required
        {
            get { return this.Item.Attributes[RequiredAttributeName] != null; }
            set
            {
                if (value)
                {
                    this.Item.Attributes.Add(RequiredAttributeName, RequiredAttributeName);
                }
                else
                {
                    this.Item.Attributes.Remove("required");
                }
            }
        }
    }
}
