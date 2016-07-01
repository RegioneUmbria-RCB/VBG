using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using System.Web.UI;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	public partial class DatiDinamiciTitolo : DatiDinamiciBaseControl<Label>
	{

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

		public static ProprietaDesigner[] GetProprietaDesigner()
		{
			return new ProprietaDesigner[] { };
		}



		public DatiDinamiciTitolo(CampoDinamicoBase campo)
			: base(campo)
		{
			//InnerControl.CssClass = "Titolo";
		}


		protected override void Render(System.Web.UI.HtmlTextWriter writer)
		{
			writer.AddAttribute(HtmlTextWriterAttribute.Class, "Titolo");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
			writer.Write(InnerControl.Text);
			writer.RenderEndTag();
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2Titolo";
		}
	}
}
