using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class RigaMultipla
	{
		HtmlTableRow _row = new HtmlTableRow();

		public RigaMultipla(int indiceMolteplicita)
		{
			var cssClass = indiceMolteplicita % 2 == 1 ? DatiDinamiciRenderingConstants.ClasseCssRigaMultipla : DatiDinamiciRenderingConstants.ClasseCssRigaMultiplaAlt;
			this._row.Attributes.Add("class", cssClass);
		}

		public void AggiungiCellaVuota(string cssClass)
		{
			var cellaControllo = new HtmlTableCell();

			cellaControllo.Attributes.Add("class", cssClass);
			this._row.Cells.Add(cellaControllo);
		}

		public void AggiungiCampoDinamico(string cssClass, IDatiDinamiciControl control)
		{
			var cella = new HtmlTableCell();

			cella.Attributes.Add("class", cssClass);
			cella.Controls.Add(control as WebControl);	

			this._row.Cells.Add(cella);			
		}

		public void AggiungiCellaConControllo(Control control)
		{
			var cella = new HtmlTableCell();

			cella.Controls.Add(control);

			this._row.Cells.Add(cella);
		}

		public void AggiungiBottoneElimina(BottoneEliminaRiga bottone)
		{
			this.AggiungiCellaConControllo(bottone.AsGenericControl());
		}

		internal HtmlTableRow ToHtmlRow()
		{
			return this._row;
		}
	}
}
