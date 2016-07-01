using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.HtmlControls;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class Blocco
	{
		HtmlGenericControl _divBlocco;
		HtmlTable _tabella;
		int _numeroMassimoColonneInUnaRiga = 0;

		public Blocco(bool compatibilitaOpenOffice)
		{
			this._divBlocco = new HtmlGenericControl("div");
			this._tabella = new HtmlTable();
			
			this._divBlocco.Attributes.Add("class", "bloccoMultiplo");
			this._divBlocco.Style.Add("border", "1px solid #cccccc");
			this._divBlocco.Style.Add("padding", "10px");
			
			this._divBlocco.Controls.Add(this._tabella);

			if (compatibilitaOpenOffice)
			{
				this._tabella.Width = "100%";
				this._tabella.Attributes.Add("border", "1");
				this._tabella.Attributes.Add("bordercolor", "#000000");
				this._tabella.Attributes.Add("rules", "none");
				this._tabella.Attributes.Add("cellpadding", "0");
				this._tabella.Attributes.Add("cellspacing", "0");
			}

			this._numeroMassimoColonneInUnaRiga = 0;
		}

		internal void AssegnaGruppo(int idGruppo)
		{
			var oldClass = this._divBlocco.Attributes["class"];

			this._divBlocco.Attributes.Add("class", oldClass + " d2groupid_" + idGruppo);
			this._divBlocco.Attributes.Add("data-d2-group", idGruppo.ToString());
		}

		internal void Aggiungi(Control control)
		{
			this._divBlocco.Controls.AddAt(0,control);
		}

		internal void Aggiungi(IRigaRenderizzata riga)
		{
			this._numeroMassimoColonneInUnaRiga = Math.Max(this._numeroMassimoColonneInUnaRiga, riga.NumeroCelle);

			this._tabella.Rows.Add(riga.AsHtmlRow());
		}

		internal void CorreggiColspanCelle()
		{
			for (int indiceRiga = 0; indiceRiga < this._tabella.Rows.Count; indiceRiga++)
			{
				int celleNellaRigaCorrente = this._tabella.Rows[indiceRiga].Cells.Count;

				if (celleNellaRigaCorrente < this._numeroMassimoColonneInUnaRiga && celleNellaRigaCorrente > 0)
				{
					this._tabella.Rows[indiceRiga].Cells[celleNellaRigaCorrente - 1].ColSpan = this._numeroMassimoColonneInUnaRiga - (celleNellaRigaCorrente - 1);
				}
			}
		}

		internal Control AsHtmlControl()
		{
			return this._divBlocco;
		}
	}
}
