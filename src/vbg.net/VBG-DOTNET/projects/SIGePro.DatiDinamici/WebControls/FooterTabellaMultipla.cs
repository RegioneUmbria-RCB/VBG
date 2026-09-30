using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	internal class FooterTabellaMultipla
	{
		HtmlTableRow _row = new HtmlTableRow();
		HtmlTableCell _cella = new HtmlTableCell();

		public FooterTabellaMultipla(int idGruppoRigheMultiple, EventHandler aggiungiClickHandler)
		{
			this._row.Attributes.Add("class", "bloccoMultiploAggiungiRiga");

			var cmdAggiungi = new LinkButton
			{
				ID = "cmdAggiungi" + idGruppoRigheMultiple.ToString(),
				Text = "Aggiungi riga",
				CommandArgument = idGruppoRigheMultiple.ToString()
			};
			cmdAggiungi.Click += aggiungiClickHandler;

			this._row.Cells.Add(this._cella);
			this._cella.Controls.Add(cmdAggiungi);
		}


		public void SetColSpan(int colCount)
		{
			this._cella.ColSpan = colCount + 1;	// +1 perchè va contato anche il td del bottone elimina
		}

		public HtmlTableRow ToHtmlRow()
		{
			return this._row;
		}
	}
}
