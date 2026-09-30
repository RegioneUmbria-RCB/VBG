using System;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public class DatiDinamiciButton : DatiDinamiciBaseControl<Button>
    {
        /// <summary>
        /// Restituisce la lista di proprieta valorizzabili tramite la pagina di editing dei campi
        /// </summary>
        /// <returns>lista di proprieta valorizzabili tramite la pagina di editing dei campi</returns>
        public static ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[]{
                new ProprietaDesigner("Testo", "Testo del bottone","Invia"),
                new ProprietaDesigner("EffettuaPostback","Effettua Postback<br/><small>Se impostato a 'Si' effettua un post della pagina dopo la notifica della modifica</small>" ,TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
            };
        }

        public string Testo
        {
            get { return this.InnerControl.Text; }
            set { this.InnerControl.Text = value; }
        }

        public bool EffettuaPostback
        {
            get { object o = this.ViewState["EffettuaPostback"]; return o == null ? false : (bool)o; }
            set { this.ViewState["EffettuaPostback"] = value; }
        }


        public override string Valore
        {
            get
            {

                return DateTime.Now.Ticks.ToString();
            }
            set
            {
                // ...
            }
        }

        public bool AggiornaPagina
        {
            get { object o = this.ViewState["AggiornaPagina"]; return o == null ? false : (bool)o; }
            set { this.ViewState["AggiornaPagina"] = value; }
        }


        public DatiDinamiciButton()
        {
            this.InizializzaControllo();
        }

        public DatiDinamiciButton(CampoDinamicoBase campo)
            : base(campo)
        {

            this.InizializzaControllo();
        }

        private void InizializzaControllo()
        {
            this.InnerControl.CausesValidation = false;
            this.InnerControl.OnClientClick = "return false;";
            //this.InnerControl.Attributes.Add("class", "ddButton");
        }

        protected override void OnPreRender(EventArgs e)
        {
            if (this.AggiornaPagina)
            {
                this.InnerControl.OnClientClick = this.Page.ClientScript.GetPostBackEventReference(this, "");
            }

            base.OnPreRender(e);

            if (this.EffettuaPostback)
            {
                this.InnerControl.Attributes.Add("data-effettua-postback", "1");
            }
        }

        protected override string GetNomeEventoModifica()
        {
            return "click";
        }

        protected override string GetNomeTipoControllo()
        {
            return "d2Button";
        }

        protected override string GetExtraCssClasses()
        {
            return "btn btn-primary";
        }
    }
}
