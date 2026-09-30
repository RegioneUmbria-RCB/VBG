using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{

    [ToolboxData("<{0}:LabeledIntTextBox runat=\"server\"/>"),
   ValidationProperty("Value"),
  DefaultProperty("Value"),
 ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledIntTextBox : LabeledControlBase//LabeledControl<IntTextBox>
    {
        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public IntTextBox Item
        {
            get { return this.GetInnerControl<IntTextBox>(); }
        }

        protected override WebControl CreateInnerControl()
        {
            return new IntTextBox();
        }
    }

}
