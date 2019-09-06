using System;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Descrizione di riepilogo per ComboAttivita.
	/// </summary>
	[ToolboxData("<{0}:ComboAttivita runat=server></{0}:ComboAttivita>")]
	public class ComboAttivita : FilteredDropDownList
	{
		public ComboAttivita()
		{
			EnsureChildControls();
		}

		protected override void CreateChildControls()
		{
			this.DataTextField = "ISTAT";
			this.DataValueField = "CODICEISTAT";
		}


		public override void DataBind()
		{
			try
			{
				base.DataBind();
			}
			catch (ArgumentOutOfRangeException)
			{
				this.SelectedIndex = 0;
			}
		}


	}
}