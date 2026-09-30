using System;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Statistiche;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public class DatiDinamiciEagle : DatiDinamiciBaseControl<Button>
    {
        public override string Valore { get => this._datiLocalizzazione.Value; set => this._datiLocalizzazione.Value = value; }

        public string TestoBottone { get => this.InnerControl.Text; set => this.InnerControl.Text = value; }

        public string CampoLocalizzazione
        {
            get => this.InnerControl.Attributes["data-campo-localizzazione"];
            set => this.InnerControl.Attributes["data-campo-localizzazione"] = value;
        }

        private readonly HiddenField _datiLocalizzazione = new HiddenField();

        public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
        {
            return new TipoConfrontoFiltroEnum[0];
        }

        public static ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[]{
                        new ProprietaDesigner("CampoLocalizzazione","Campo con localizzazione EAGLE", ""),
                        new ProprietaDesigner("TestoBottone","Testo bottone", "Mostra sulla mappa")
            };
        }

        internal DatiDinamiciEagle() : base() { }

        public DatiDinamiciEagle(CampoDinamicoBase campo) : base(campo) { }

        protected override void CreateChildControls()
        {
            this._datiLocalizzazione.ID = "dettagli-localizzazione";
            this.Controls.Add(this._datiLocalizzazione);
        }

        protected override string GetNomeTipoControllo()
        {
            return "d2-eagle";
        }

        protected override string GetNomeEventoModifica() => "eagle.valore-modificato";

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            //this.InnerControl.Style.Add(HtmlTextWriterStyle.Display, "none");
        }

        protected override string GetExtraCssClasses()
        {
            return "btn btn-primary d2Button";
        }
    }
}
