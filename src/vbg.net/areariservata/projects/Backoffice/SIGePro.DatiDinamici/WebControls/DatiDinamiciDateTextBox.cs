using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.DatiDinamici.Statistiche;
using Init.Utils.Web.UI;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public partial class DatiDinamiciDateTextBox : DatiDinamiciBaseControl<TextBox>
	{
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
								new ProprietaDesigner("ReadOnly", "Sola lettura", TipoControlloEditEnum.ListBox, "No=false,Si=true","false") };
		}

        private DateTime? GetDateValue()
        {
            try
            {
                var dt = DateTime.ParseExact(InnerControl.Text, "dd/MM/yyyy", null);

                return dt;
            }
            catch (Exception)
            {
                return (DateTime?)null;
            }
        }

        private void SetDateValue(DateTime dt)
        {
            this.InnerControl.Text = dt.ToString("dd/MM/yyyy");
        }

		public override string Valore
		{
			get
			{
				if (String.IsNullOrEmpty(InnerControl.Text))
					return String.Empty;

				return GetDateValue().Value.ToString("yyyyMMdd");
			}
			set
			{
				InnerControl.Text = String.Empty;

				if (String.IsNullOrEmpty(value))
					return;

				try
				{
					SetDateValue( DateTime.ParseExact(value, "yyyyMMdd", null));
				}
				catch (Exception /*ex*/)
				{
					throw new ArgumentException("Controllo " + this.ID + ":il valore " + value + " non è una data valida");
				}
			}
		}

		public bool ReadOnly
		{
			get { return InnerControl.ReadOnly; }
			set { InnerControl.ReadOnly = value; }
		}

		internal DatiDinamiciDateTextBox()
		{
			this.InnerControl.CausesValidation = false;
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
		}

		public DatiDinamiciDateTextBox(CampoDinamicoBase campo)
			: base(campo)
		{
			this.InnerControl.CausesValidation = false;
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
			this.InnerControl.Attributes.Add("placeholder", "gg/mm/aaaa");
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2DateTextBox";
		}

        protected override string GetExtraCssClasses()
        {
            return "date-text-box";
        }

        protected override string GetNomeEventoModifica()
        {
            return "changeDate";
        }
    }
}
