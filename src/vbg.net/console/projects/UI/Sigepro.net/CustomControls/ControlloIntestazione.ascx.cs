using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net.Controls;

namespace SIGePro.Net.CustomControls
{


	public partial class CustomControls_ControlloIntestazione : UserControl
	{
		private const string CSS_SELEZIONATO = "Selezionato";
		private const string CSS_NON_SELEZIONATO = "NonSelezionato";

        private BasePage basePage
        {
            get { return this.Page as BasePage; }
        }

		public string TitoloPagina
		{
			get
			{
				return lblTitolo.Text;
			}

			set
			{
				lblTitolo.Text = value;
			}
		}

		public IntestazionePaginaTipiTabEnum TabSelezionato
		{
			get { object o = this.ViewState["TabSelezionato"]; return o == null ? IntestazionePaginaTipiTabEnum.Ricerca : (IntestazionePaginaTipiTabEnum)o; }
			set { this.ViewState["TabSelezionato"] = value; }
		}


		protected string ClasseCssRicerca
		{
			get { return TabSelezionato == IntestazionePaginaTipiTabEnum.Ricerca ? "selezionato" : ""; }
		}

		protected string ClasseCssRisultato
		{
			get { return TabSelezionato == IntestazionePaginaTipiTabEnum.Risultato ? "selezionato" : ""; }
		}

		protected string ClasseCssScheda
		{
			get { return TabSelezionato == IntestazionePaginaTipiTabEnum.Scheda ? "selezionato" : ""; }
		}

		private void TabSelezionatoChanged()
		{/*
			imgRicerca.CssClass = imgRisultato.CssClass = imgScheda.CssClass = CSS_NON_SELEZIONATO;

			if (TabSelezionato == IntestazionePaginaTipiTabEnum.Ricerca)
				imgRicerca.CssClass = CSS_SELEZIONATO;
			else if (TabSelezionato == IntestazionePaginaTipiTabEnum.Risultato)
				imgRisultato.CssClass = CSS_SELEZIONATO;
			else if (TabSelezionato == IntestazionePaginaTipiTabEnum.Scheda)
				imgScheda.CssClass = CSS_SELEZIONATO;*/
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}
	}
}