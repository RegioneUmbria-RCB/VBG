using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using Init.SIGePro.DatiDinamici.Statistiche;
using Init.Utils.Web.UI;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public partial class DatiDinamiciIntTextBox : DatiDinamiciBaseControl<IntTextBox>
	{
		private static class Constants
		{
			public const string ValoreDefaultAttribute = "data-valore-default";
		}

		public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
		{
			return new TipoConfrontoFiltroEnum[] { 
									TipoConfrontoFiltroEnum.Equal,
									TipoConfrontoFiltroEnum.NotEqual,
									TipoConfrontoFiltroEnum.LessThan,
									TipoConfrontoFiltroEnum.LessThanOrEqual,
									TipoConfrontoFiltroEnum.GreaterThan,
									TipoConfrontoFiltroEnum.GreaterThanOrEqual,
									TipoConfrontoFiltroEnum.Null,
									TipoConfrontoFiltroEnum.NotNull };
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
						new ProprietaDesigner("Columns", "Larghezza visualizzata","10"),
						new ProprietaDesigner("ValidationMinValue", "Valore minimo","0"),
						new ProprietaDesigner("ValidationMaxValue", "Valore massimo","99999"),
						new ProprietaDesigner("ValoreDefault", "Valore di default",String.Empty),
						new ProprietaDesigner("TipoNumerico", "Tipo numerico","true",false) };
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

		public string ValoreDefault
		{
			get { return InnerControl.Attributes[Constants.ValoreDefaultAttribute]; }
			set { InnerControl.Attributes[Constants.ValoreDefaultAttribute] = value; }
		}
		public override string Valore
		{
			get
			{
				return InnerControl.Text;
			}
			set
			{
				InnerControl.Text = value;
			}
		}


		public bool ReadOnly
		{
			get { return InnerControl.ReadOnly; }
			set { InnerControl.ReadOnly = value; }
		}


		internal DatiDinamiciIntTextBox()
		{
			this.InnerControl.CausesValidation = false;
		}

		public DatiDinamiciIntTextBox(CampoDinamicoBase campo)
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
			return "d2IntTextBox";
		}

	}
}
