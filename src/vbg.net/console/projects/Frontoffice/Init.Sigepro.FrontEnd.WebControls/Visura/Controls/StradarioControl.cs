using System.Web.UI;
using Init.Sigepro.FrontEnd.WebControls.Common;
//using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per StradarioControl.
	/// </summary>
	public class StradarioControl : BaseVisuraControl
	{
		private RicercaStradario m_innerControl = new RicercaStradario();

		public override string IdComune
		{
			get { return base.IdComune; }
		}

		public override string CodiceComune
		{
			get { return base.CodiceComune; }
			set
			{
				base.CodiceComune = value;
				m_innerControl.CodiceComune = value;
			}
		}
/*
		public override DataBase Database
		{
			get{ return base.Database; }
			set{ m_innerControl.Database = base.Database = value; }
		}
*/
		public string Value
		{
			get { return m_innerControl.Value == -1 ? "" : m_innerControl.Value.ToString(); }
		}

		public StradarioControl()
		{
			this.Label = "Indirizzo";
		}

		protected override Control GetInnerControl()
		{
			return m_innerControl;
		}
	}
}