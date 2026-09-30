using Init.Sigepro.FrontEnd.WebControls.Common;
using System.Web.UI;
//using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
    /// <summary>
    /// Descrizione di riepilogo per AlberoProcControl.
    /// </summary>
    public class AlberoProcControl : BaseVisuraControl
    {
        private readonly RicercaAlberoProc _innerControl = new RicercaAlberoProc();

        public string Value
        {
            get { return this._innerControl.Value; }
        }


        public AlberoProcControl()
        {
        }

        protected override Control GetInnerControl()
        {
            return this._innerControl;
        }
        protected override void RenderChildren(HtmlTextWriter writer)
        {
            this._innerControl.RenderControl(writer);
        }
    }
}