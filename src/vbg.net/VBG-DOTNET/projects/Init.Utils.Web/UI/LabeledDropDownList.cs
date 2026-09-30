using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{

    [ToolboxData("<{0}:LabeledDropDownList runat=\"server\"/>"),
   ValidationProperty("Value"),
  DefaultProperty("Value"),
 ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledDropDownList : LabeledControlBase// : LabeledControl<DropDownList>
    {
        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public DropDownList Item
        {
            get { return this.GetInnerControl<DropDownList>(); }
        }

        protected override WebControl CreateInnerControl()
        {
            return new DropDownList();
        }
    }

}
