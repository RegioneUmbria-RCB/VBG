using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{

    [ToolboxData("<{0}:LabeledDoubleTextBox runat=\"server\"/>"),
   ValidationProperty("Value"),
  DefaultProperty("Value"),
 ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledDoubleTextBox : LabeledControlBase// : LabeledControl<DoubleTextBox>
    {
        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public DoubleTextBox Item
        {
            get { return this.GetInnerControl<DoubleTextBox>(); }
        }

        protected override WebControl CreateInnerControl()
        {
            return new DoubleTextBox();
        }
    }
}
