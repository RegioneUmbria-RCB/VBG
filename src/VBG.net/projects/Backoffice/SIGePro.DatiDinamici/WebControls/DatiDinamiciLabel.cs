using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	public partial class DatiDinamiciLabel : DatiDinamiciBaseControl<Label>
	{

		public static ProprietaDesigner[] GetProprietaDesigner()
		{
			return new ProprietaDesigner[] { };
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

		public DatiDinamiciLabel(CampoDinamicoBase campo):base(campo)
		{
			IgnoraRegistrazioneJavascript = true;
		}


		protected override void Render(System.Web.UI.HtmlTextWriter writer)
		{
			NascondiIconaHelp();

			base.Render(writer);
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2Label";
		}
	}
}
