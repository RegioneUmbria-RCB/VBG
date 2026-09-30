using System.Web.UI.Design;

namespace Init.Utils.Web.Designers
{
	/// <summary>
	/// Descrizione di riepilogo per TreeViewDesigner.
	/// </summary>
	public class AlberoDesigner : ControlDesigner
	{
		public AlberoDesigner()
		{
		}

		public override string GetDesignTimeHtml()
		{
			string html = @"
<span>
RootItem<br>
&nbsp;&nbsp;&nbsp;&nbsp;Child1<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Child11<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Child12<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Child13<br>
&nbsp;&nbsp;&nbsp;&nbsp;Child2<br>
&nbsp;&nbsp;&nbsp;&nbsp;Child3<br>
</span>
";
			return html;
		}

	}
}