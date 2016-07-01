using System.Web.UI;
using Init.Sigepro.FrontEnd.WebControls.Common;
//using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per AlberoProcControl.
	/// </summary>
	public class AlberoProcControl : BaseVisuraControl
	{
		private RicercaAlberoProc m_innerControl = new RicercaAlberoProc();


		public override string IdComune
		{
			get { return base.IdComune; }
		}


		public override string Software
		{
			get { return base.Software; }
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
			get { return m_innerControl.Value; }
		}


		public AlberoProcControl()
		{
		}

		protected override Control GetInnerControl()
		{
			return m_innerControl;
		}
		protected override void RenderChildren(HtmlTextWriter writer)
		{
			m_innerControl.RenderControl(writer);
		}
	}
}