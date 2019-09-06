using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per VisuraTextBoxControl.
	/// </summary>
	public class VisuraTextBoxControl : BaseVisuraControl
	{
		private TextBox m_innerControl = new TextBox();

		public int MaxLength
		{
			get { return m_innerControl.MaxLength; }
			set { m_innerControl.MaxLength = value; }
		}

		public int Columns
		{
			get { return m_innerControl.Columns; }
			set { m_innerControl.Columns = value; }
		}

        public new AttributeCollection Attributes
        {
            get { return m_innerControl.Attributes; }
        }

		public string Value
		{
			get { return m_innerControl.Text; }
		}

		public VisuraTextBoxControl()
		{
		}

		protected override Control GetInnerControl()
		{
			return m_innerControl;
		}
	}
}