using System.Web;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    public class DatiDinamiciReadOnlyButton : WebControl, IDatiDinamiciControl
    {
        private Literal _testoDescrizione { get; set; }
        private readonly HiddenField _hidden;

        public int IdCampoCollegato
        {
            get { object o = this.ViewState["IdCampoCollegato"]; return o == null ? -1 : (int)o; }
            private set { this.ViewState["IdCampoCollegato"] = value; }
        }

        public string Descrizione
        {
            get { return this._testoDescrizione.Text; }
            private set { this._testoDescrizione.Text = value; }
        }
        public int Indice { get; set; }
        public int NumeroRiga { get; set; }

        public string IdComune
        {
            get { return HttpContext.Current.Items["IdComune"].ToString(); }
        }
        public string Software
        {
            get { return HttpContext.Current.Items["Software"].ToString(); }
        }

        public string Valore { get => this._hidden.Value; set => this._hidden.Value = value; }

        public string Note { get; set; }

        public DatiDinamiciReadOnlyButton(CampoDinamicoBase campo) : this()
        {
            this.IdCampoCollegato = campo.Id;
            this.Descrizione = campo.Descrizione;
        }

        protected DatiDinamiciReadOnlyButton()
        {
            this._hidden = new HiddenField();
            this._testoDescrizione = new Literal();

            this.Controls.Add(this._hidden);
            this.Controls.Add(this._testoDescrizione);

        }
    }
}
