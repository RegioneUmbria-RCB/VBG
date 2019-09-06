using Init.SIGePro.DatiDinamici.Statistiche;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public class DatiDinamiciHidden : DatiDinamiciTextBox
    {
        internal DatiDinamiciHidden(): base()
        {
        }

        public DatiDinamiciHidden(CampoDinamicoBase campo):base(campo)
        { }

        protected override string GetNomeTipoControllo()
        {
            return "d2-hidden";
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            this.Style.Add(HtmlTextWriterStyle.Display, "none");
        }
    }
}
