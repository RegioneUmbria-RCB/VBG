using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.WebControls.Common;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per IVisuraFilterControl.
	/// </summary>
	public interface IVisuraFilterControl : IDatabaseSoftwareControl
	{
		string CodiceComune { get; set; }
		string Title { get; set; }
	}
}