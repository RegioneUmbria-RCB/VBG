using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    public class DatiDinamiciReadOnlyHidden : DatiDinamiciLabel
    {
        public DatiDinamiciReadOnlyHidden(CampoDinamicoBase campo) : base(campo)
        {
        }

        public override string Valore { get => String.Empty; set => base.Valore = value; }

        protected override string GetNomeTipoControllo()
        {
            return "d2-hidden";
        }

        protected override void Render(HtmlTextWriter writer)
        {
            // non fa niente
        }
    }
}
