using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class BottoneEliminaRiga
	{
		LinkButton _lnkEliminaBlocco;
		HtmlGenericControl _divContenitore;


		public BottoneEliminaRiga(int idGruppo, int indiceMolteplicita, EventHandler clickHandler)
		{
			this._divContenitore = new HtmlGenericControl("div");

			this._divContenitore.Attributes.Add("class", "divEliminazioneBlocco");

			this._lnkEliminaBlocco = new LinkButton
			{
				ID = String.Format("{0}_{1}_elimina", idGruppo, indiceMolteplicita),
				Text = "Elimina",
				OnClientClick = "return confirm('Eliminare il blocco selezionato\\?');",
				CommandArgument = idGruppo + "$" + indiceMolteplicita,
				ToolTip = "Elimina blocco"
			};

			this._lnkEliminaBlocco.Click += clickHandler;

			this._divContenitore.Controls.Add(this._lnkEliminaBlocco);
		}

		public HtmlGenericControl AsGenericControl()
		{
			return this._divContenitore;
		}
	}
}
