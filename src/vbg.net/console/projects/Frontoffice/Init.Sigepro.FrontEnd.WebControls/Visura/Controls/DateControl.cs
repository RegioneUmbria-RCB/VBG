using System;
using System.Web.UI;
using Init.Sigepro.FrontEnd.WebControls.Common;
using Init.Utils.Web.UI;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per DateControl.
	/// </summary>
	public class DateControl : BaseVisuraControl
	{
		private DateTextBox m_innerControl = new DateTextBox();

		public DateTime Value
		{
			get { return m_innerControl.DateValue.GetValueOrDefault(DateTime.MinValue); }
		}

		public DateControl()
		{
		}

		protected override Control GetInnerControl()
		{
			return m_innerControl;
		}
	}
}