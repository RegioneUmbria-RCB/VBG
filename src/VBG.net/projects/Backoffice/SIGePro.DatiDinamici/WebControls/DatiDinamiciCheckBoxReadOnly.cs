using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	public class DatiDinamiciCheckBoxReadOnly : DatiDinamiciBaseControl<Label>
	{
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

		public override string Valore
		{
			get { object o = this.ViewState["Valore"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Valore"] = value; }
		}

	

		public static ProprietaDesigner[] GetProprietaDesigner()
		{
			return new ProprietaDesigner[] { };
		}

		public DatiDinamiciCheckBoxReadOnly(CampoDinamicoBase campo)
			: base(campo)
		{
			IgnoraRegistrazioneJavascript = true;
		}

		protected override void OnPreRender(EventArgs e)
		{
			base.OnPreRender(e);
		}

		protected override void Render(System.Web.UI.HtmlTextWriter writer)
		{
			NascondiIconaHelp();

			if (this.Valore == this.ValoreTrue)
				InnerControl.Text = "[X]";
			else
				InnerControl.Text = "[..]";
			
			

			base.Render(writer);
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2CheckBoxReadOnly";
		}
	}
}
