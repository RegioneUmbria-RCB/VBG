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
	public class DatiDinamiciTextBox : DatiDinamiciBaseControl<TextBox>
	{
		public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
		{
			return new TipoConfrontoFiltroEnum[] { 
							TipoConfrontoFiltroEnum.Equal, 
							TipoConfrontoFiltroEnum.NotEqual, 
							TipoConfrontoFiltroEnum.Null, 
							TipoConfrontoFiltroEnum.NotNull,
							TipoConfrontoFiltroEnum.Like};
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
						new ProprietaDesigner("ReadOnly", "Sola lettura", TipoControlloEditEnum.ListBox, "No=false,Si=true","false"),
						new ProprietaDesigner("MaxLength", "Lunghezza massima","99999"),
						new ProprietaDesigner("Columns", "Larghezza visualizzata","40"),
						new ProprietaDesigner("MultiLine", "Multiriga", TipoControlloEditEnum.ListBox, "No=false,Si=true","false"),
						new ProprietaDesigner("Rows", "N. righe (se multiriga)","1"),
						new ProprietaDesigner("EspressioneRegolare", "Espressione regolare di validazione","")};
		}

		public int Columns
		{
			get { return InnerControl.Columns; }
			set { InnerControl.Columns = value; }
		}
		public int MaxLength
		{
			get { return InnerControl.MaxLength; }
			set { InnerControl.MaxLength = value; }
		}

		public bool ReadOnly
		{
			get { return InnerControl.ReadOnly; }
			set { InnerControl.ReadOnly = value; }
		}

		public bool MultiLine
		{
			get { return InnerControl.TextMode == TextBoxMode.MultiLine; }
			set { InnerControl.TextMode = value ? TextBoxMode.MultiLine : TextBoxMode.SingleLine; }
		}



		public int Rows
		{
			get { return InnerControl.Rows; }
			set { InnerControl.Rows = value; }
		}

		public override string Valore
		{
			get { return InnerControl.Text; }
			set { InnerControl.Text = value; }
		}

		internal DatiDinamiciTextBox()
		{
			this.InnerControl.CausesValidation = false;
		}

		public DatiDinamiciTextBox(CampoDinamicoBase campo)
			: base(campo)
		{
			//this.InnerControl.AutoPostBack = true;
			this.InnerControl.CausesValidation = false;

			//this.InnerControl.TextChanged += delegate(object sender, EventArgs e)
			//{
			//    NotifyValueChanged(this.InnerControl.Text);
			//};
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2TextBox";
		}
	}
}
