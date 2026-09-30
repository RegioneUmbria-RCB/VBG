using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public class DatiDinamiciReadOnlyText : DatiDinamiciBaseControl<Panel>
    {
        /// <summary>
		/// Ritorna la lista di proprieta valorizzabili tramite la pagina di editing dei campi
		/// </summary>
		/// <returns>lista di proprieta valorizzabili tramite la pagina di editing dei campi</returns>
		public static ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[]{
                        new ProprietaDesigner("StileCampo","Stile campo",TipoControlloEditEnum.ListBox,"Titolo=titolo,Sottotitolo=sottotitolo,Testo semplice=testo-semplice","titolo")
            };
        }

        private readonly Literal _literal = new Literal();

        public override string Valore
        {
            get
            {
                return this._literal.Text;
            }
            set
            {
                this._literal.Text = value;
            }
        }

        public string StileCampo
        {
            get { object o = this.ViewState["StileCampo"]; return o == null ? "testo-semplice" : (string)o; }
            set { this.ViewState["StileCampo"] = value; }
        }

        public DatiDinamiciReadOnlyText(CampoDinamicoBase campo) : base(campo)
        {
            this._literal.ID = "_literal";
            this.InnerControl.Controls.Add(this._literal);
        }

        protected override string GetNomeTipoControllo()
        {
            return "d2-read-only-text";
        }

        protected override string GetExtraCssClasses()
        {
            return this.StileCampo;
        }
    }
}
