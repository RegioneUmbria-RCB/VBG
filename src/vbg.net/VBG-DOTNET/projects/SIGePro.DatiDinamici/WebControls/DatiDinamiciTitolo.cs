using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    public partial class DatiDinamiciTitolo : DatiDinamiciBaseControl<Label>
    {

        public override string Valore
        {
            get
            {
                return this.InnerControl.Text;
            }
            set
            {
                this.InnerControl.Text = value;
            }
        }

        public static ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[] { };
        }



        public DatiDinamiciTitolo(CampoDinamicoBase campo)
            : base(campo)
        {
            //InnerControl.CssClass = "Titolo";
        }


        protected override void Render(System.Web.UI.HtmlTextWriter writer)
        {
            writer.AddAttribute(HtmlTextWriterAttribute.Class, "Titolo");
            writer.RenderBeginTag(HtmlTextWriterTag.H3);
            writer.Write(this.InnerControl.Text);
            writer.RenderEndTag();
        }

        protected override string GetNomeTipoControllo()
        {
            return "d2Titolo";
        }
    }
}
