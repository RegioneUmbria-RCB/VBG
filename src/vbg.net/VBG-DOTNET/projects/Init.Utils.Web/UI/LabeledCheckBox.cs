using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{

    [ToolboxData("<{0}:LabeledCheckBox runat=\"server\"/>"),
   ValidationProperty("Value"),
  DefaultProperty("Value"),
 ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledCheckBox : LabeledControlBase // : LabeledControl<System.Web.UI.WebControls.CheckBox>
    {
        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public CheckBox Item
        {
            get { return this.GetInnerControl<CheckBox>(); }
        }

        protected override WebControl CreateInnerControl()
        {
            return new CheckBox();
        }
        public LabeledCheckBox()
        {
            this.Item.TextAlign = TextAlign.Right;
        }
    }

}
