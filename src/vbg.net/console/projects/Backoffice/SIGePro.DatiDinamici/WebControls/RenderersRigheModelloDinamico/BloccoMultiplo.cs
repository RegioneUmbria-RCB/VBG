using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.HtmlControls;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class BloccoMultiplo : IRigaRenderizzata
	{
		HtmlTableRow _row;
		HtmlTableCell _cell;

		internal BloccoMultiplo()
		{
			this._row = new HtmlTableRow();
			this._cell = new HtmlTableCell();

			this._row.Cells.Add(this._cell);
		}

		internal void Aggiungi(Blocco blocco)
		{
			this._cell.Controls.Add(blocco.AsHtmlControl());
		}

		internal void Aggiungi(Control ctrl)
		{
			this._cell.Controls.Add(ctrl);
		}

		public HtmlTableRow AsHtmlRow()
		{
			return this._row;
		}

		public int NumeroCelle
		{
			get { return this._row.Cells.Count; }
		}

		public int NumeroControlli
		{
			get { return this._row.Controls.Count; }
		}
	}
}
