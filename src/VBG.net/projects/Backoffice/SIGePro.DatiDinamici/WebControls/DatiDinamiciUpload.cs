using System;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.DatiDinamici.Properties;
using Init.SIGePro.DatiDinamici.Statistiche;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public class DatiDinamiciUpload : DatiDinamiciBaseControl<TextBox>
	{
		private static class Constants
		{
			public const string ClasseCssControllo = "ddFileUpload";
			public const string NomeAttributoIdCampo = "idCampoDinamico";
			public const string NomeEventoModifica = "onchange";
            public const string NomeAttributoVerificaFirma = "data-verifica-firma-digitale";
			public const string ChiaveClientScript = "ChiaveClientScript";

		}

		public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
		{
			return new TipoConfrontoFiltroEnum[] {};
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
                                new ProprietaDesigner("VerificaFirmaDigitale","Verifica firma digitale",TipoControlloEditEnum.ListBox,"No=false,Si=true","false")
			};
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

        public bool VerificaFirmaDigitale
        {
            get { object o = this.ViewState["VerificaFirmaDigitale"]; return o == null ? false : (bool)o; }
            set { this.ViewState["VerificaFirmaDigitale"] = value; }
        }
        

		protected string Token
		{
			get { return HttpContext.Current.Items["Token"].ToString(); }
		}


		public DatiDinamiciUpload():base()
		{
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
		}

		protected override string GetNomeEventoModifica()
		{
			return "change";
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2Upload";
		}

		public DatiDinamiciUpload(CampoDinamicoBase campo)
			:base( campo )
		{
			InnerControl.CausesValidation = false;
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
		}

		protected override void OnPreRender(EventArgs e)
		{
			this.InnerControl.CssClass = Constants.ClasseCssControllo;
			this.InnerControl.Attributes.Add(Constants.NomeAttributoIdCampo, base.IdCampoCollegato.ToString() );
            this.InnerControl.Attributes.Add(Constants.NomeAttributoIdCampo, base.IdCampoCollegato.ToString() );
            this.InnerControl.Attributes.Add(Constants.NomeAttributoVerificaFirma, this.VerificaFirmaDigitale ? "1" : "0");
			base.OnPreRender(e);
		}
	}
}
