using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;

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
						new ProprietaDesigner("Testo", "Testo del bottone","Invia") };
		}

		public string Testo
		{
			get { return this.InnerControl.Text; }
			set { this.InnerControl.Text = value; }
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
			InizializzaControllo();
		}

		public DatiDinamiciButton(CampoDinamicoBase campo)
			: base(campo)
		{

			InizializzaControllo();
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
