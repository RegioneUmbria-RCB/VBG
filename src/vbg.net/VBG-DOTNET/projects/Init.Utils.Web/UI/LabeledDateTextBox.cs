using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{

    [ToolboxData("<{0}:LabeledDateTextBox runat=\"server\"/>"),
   ValidationProperty("Value"),
  DefaultProperty("Value"),
 ControlValueProperty("Value"), DefaultEvent("ValueChanged")]
    public partial class LabeledDateTextBox : LabeledControlBase// : LabeledControl<DateTextBox>
    {
        [Browsable(true),
DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public DateTextBox Item
        {
            get { return this.GetInnerControl<DateTextBox>(); }
        }

        protected override WebControl CreateInnerControl()
        {
            return new DateTextBox();
        }
    }

}
