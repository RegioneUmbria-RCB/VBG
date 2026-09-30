using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
	public partial class GridViewConverter : ControlIDConverter
	{
		protected override bool FilterControl(System.Web.UI.Control control)
		{
			return control.GetType() == typeof(GridView);
		}
	}
}
