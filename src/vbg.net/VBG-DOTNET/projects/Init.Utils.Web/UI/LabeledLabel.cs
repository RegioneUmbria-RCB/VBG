using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{

    [ToolboxData("<{0}:LabeledLabel runat=\"server\"/>"),
   ValidationProperty("Value"),
  DefaultProperty("Value"),
 ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledLabel : LabeledControlBase// : LabeledControl<System.Web.UI.WebControls.Label>
    {
        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public Label Item
        {
            get { return this.GetInnerControl<Label>(); }
        }

        protected override WebControl CreateInnerControl()
        {
            return new Label();
        }
    }

}
