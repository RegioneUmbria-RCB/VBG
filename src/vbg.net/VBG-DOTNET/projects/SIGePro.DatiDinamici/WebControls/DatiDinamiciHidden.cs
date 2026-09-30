using System;
using System.Web.UI;
using VBG.DatiDinamici;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public class DatiDinamiciHidden : DatiDinamiciTextBox
    {
        internal DatiDinamiciHidden() : base() { }

        public DatiDinamiciHidden(CampoDinamicoBase campo) : base(campo) { }

        protected override string GetNomeTipoControllo()
        {
            return "d2-hidden";
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            this.InnerControl.Style.Add(HtmlTextWriterStyle.Display, "none");
        }
    }
}
