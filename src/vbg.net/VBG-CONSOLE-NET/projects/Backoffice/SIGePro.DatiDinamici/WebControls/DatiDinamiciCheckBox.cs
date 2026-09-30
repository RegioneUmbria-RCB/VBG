using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using Init.SIGePro.DatiDinamici.Statistiche;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public partial class DatiDinamiciCheckBox : DatiDinamiciBaseControl<CheckBox>
	{
		public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
		{
			return new TipoConfrontoFiltroEnum[] { 
							TipoConfrontoFiltroEnum.Equal, 
							TipoConfrontoFiltroEnum.NotEqual };

		}

		/// <summary>
		/// Ritorna la lista di proprieta valorizzabili tramite la pagina di editing dei campi
		/// </summary>
		/// <returns>lista di proprieta valorizzabili tramite la pagina di editing dei campi</returns>
		public static ProprietaDesigner[] GetProprietaDesigner()
		{
			return new ProprietaDesigner[]{
				new ProprietaDesigner("Obbligatorio","Obbligatorio",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
				new ProprietaDesigner("IgnoraObbligatorietaSuAttivita","Ignora obbligatorietà su schede attività",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
				new ProprietaDesigner("ValoreTrue", "Valore se spuntato","1"),
				new ProprietaDesigner("ValoreFalse", "Valore se non spuntato","0"),
				new ProprietaDesigner("EtichettaADestra", "Etichetta a destra", TipoControlloEditEnum.ListBox, "No=false,Si=true", "false")
			};
		}



		public string ValoreTrue
		{
			get { object o = this.ViewState["ValoreTrue"]; return o == null ? "1" : o.ToString(); }
			set { this.ViewState["ValoreTrue"] = value; }
		}

		public string ValoreFalse
		{
			get { object o = this.ViewState["ValoreFalse"]; return o == null ? "0" : o.ToString(); }
			set { this.ViewState["ValoreFalse"] = value; }
		}

		protected override string GetNomeEventoModifica()
		{
			return "click";
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2CheckBox";
		}

		public override string Valore
		{
			get
			{
				return this.InnerControl.Checked ? ValoreTrue : ValoreFalse;
			}
			set
			{
				this.InnerControl.Checked = value == ValoreTrue;
			}
		}

		internal DatiDinamiciCheckBox()
		{
			this.InnerControl.CausesValidation = false;
		}

		public DatiDinamiciCheckBox(CampoDinamicoBase campo)
			: base(campo)
		{
			//this.InnerControl.AutoPostBack = true;
			this.InnerControl.CausesValidation = false;

			//this.InnerControl.CheckedChanged += delegate(object sender, EventArgs e)
			//{
			//    NotifyValueChanged(this.Valore);
			//};

		}

		protected override void OnPreRender(EventArgs e)
		{
			this.InnerControl.Attributes.Add("data-valore-true", this.ValoreTrue);
			this.InnerControl.Attributes.Add("data-valore-false", this.ValoreFalse);

			base.OnPreRender(e);
		}


	}
}
