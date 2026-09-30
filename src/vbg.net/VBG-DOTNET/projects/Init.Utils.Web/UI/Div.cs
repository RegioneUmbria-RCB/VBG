using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
	[DefaultProperty("Text"), ParseChildren(false), PersistChildren(true)]
	[ToolboxData("<{0}:Div runat=server></{0}:Div>")]
	public class Div : WebControl
	{
		public Div() :base( HtmlTextWriterTag.Div )
		{

		}
	}
}
