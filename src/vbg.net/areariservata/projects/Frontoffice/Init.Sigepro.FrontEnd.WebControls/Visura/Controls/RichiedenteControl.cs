using System.Web.UI;
using Init.Sigepro.FrontEnd.WebControls.Common;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per RichiedenteControl.
	/// </summary>
	public class RichiedenteControl : BaseVisuraControl
	{
		private RicercaRichiedente m_innerControl = new RicercaRichiedente();

		//public override string IdComune
		//{
		//    get { return base.IdComune; }
		//    set
		//    {
		//        base.IdComune = value;
		//        m_innerControl.IdComune = value;
		//    }
		//}

		public string Value
		{
			get { return m_innerControl.Value; }
		}

		public RichiedenteControl()
		{
		}

		protected override Control GetInnerControl()
		{
			return m_innerControl;
		}
	}
}