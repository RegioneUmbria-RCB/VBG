using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class TabellaDatiMultipli : IRigaRenderizzata
	{
		HtmlTableRow _containerRow = new HtmlTableRow();
		HtmlTable _table = new HtmlTable();
		bool _solaLettura;

		public TabellaDatiMultipli(bool solaLettura, int idGruppo)
		{
			this._table = new HtmlTable();
			this._table.Attributes.Add("class", DatiDinamiciRenderingConstants.ClasseCssTabellaRigheMultiple);


			this._containerRow = new HtmlTableRow();
			this._containerRow.Attributes.Add("class", "d2groupid_" + idGruppo.ToString());
			this._containerRow.Attributes.Add("data-d2-group", idGruppo.ToString());
            
			this._solaLettura = solaLettura;

			// Questa cella conterrà la tabella contenente i dati delle righe multiple
			var cellaTabellaMultipla = new HtmlTableCell();

			this._containerRow.Cells.Add(cellaTabellaMultipla);

			cellaTabellaMultipla.Controls.Add(this._table);
			cellaTabellaMultipla.Controls.Add(new Literal
			{
				Text = DatiDinamiciRenderingConstants.HtmlNewline
			});
		}

		public void AggiungiIntestazione(IEnumerable<string> listaEtichette, bool bottoneEliminaPresente)
		{
			var rigaIntestazione = new HtmlTableRow();
			var cssClass = DatiDinamiciRenderingConstants.ClasseCssTestataRigheMultipleNoOO;

			rigaIntestazione.Attributes.Add("class", cssClass);

			foreach (var etichetta in listaEtichette)
			{
				var cellaIntestazione = new HtmlTableCell("th");
				cellaIntestazione.InnerHtml = etichetta;

				rigaIntestazione.Cells.Add(cellaIntestazione);
			}

			// L'ultima colonna contiene i bottoni con le azioni effettuabili sulla riga
			if (!this._solaLettura && bottoneEliminaPresente)
			{
				var cellaIntestazione = new HtmlTableCell("th");
				cellaIntestazione.InnerHtml = DatiDinamiciRenderingConstants.HtmlSpacer;

				rigaIntestazione.Cells.Add(cellaIntestazione);
			}

			this._table.Rows.Add(rigaIntestazione);
		}

		public void AggiungiRigaMultipla(RigaMultipla riga)
		{
			this._table.Rows.Add(riga.ToHtmlRow());
		}

		public void AggiungiFooter(FooterTabellaMultipla footer)
		{
			this._table.Rows.Add(footer.ToHtmlRow());
		}

		HtmlTableRow IRigaRenderizzata.AsHtmlRow()
		{
			return this._containerRow;
		}

		public int NumeroCelle
		{
			get { return this._containerRow.Cells.Count; }
		}

		public int NumeroControlli
		{
			get { return this._containerRow.Controls.Count; }
		}

	}
}
