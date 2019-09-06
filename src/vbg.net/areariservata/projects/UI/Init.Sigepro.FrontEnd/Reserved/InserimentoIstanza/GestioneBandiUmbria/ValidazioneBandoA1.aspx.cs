using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria
{
    public partial class ValidazioneBandoA1 : IstanzeStepPage
    {


        [Inject]
        protected IBandiUmbriaService _service { get; set; }

		public int IdAllegatoModello1
		{
			get { object o = this.ViewState["IdAllegatoModello1"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["IdAllegatoModello1"] = value; }
		}

		public int IdAllegatoModello2
		{
			get { object o = this.ViewState["IdAllegatoModello2"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["IdAllegatoModello2"] = value; }
		}



		public ValidazioneBandoA1()
		{
			this.Avvertimenti = new List<string>();
		}

        protected void Page_Load(object sender, EventArgs e)
        {
			if (!IsPostBack)
			{
				DoValidation();
			}
        }

        private void DoValidation()
        {
			try
			{
				var esitoValidazione = this._service.ValidaBandoA1(IdDomanda);

				this.Errori.AddRange(esitoValidazione.Errori);
				this.Avvertimenti.AddRange(esitoValidazione.Avvisi);

				this.txtTitoloProgetto.Text = esitoValidazione.DatiProgetto.TitoloProgetto;
				this.txtAcronimo.Text = esitoValidazione.DatiProgetto.Acronimo;
				this.txtTipologia.Text = esitoValidazione.DatiProgetto.TipologiaAggregazione;
				this.txtDurata.Text = esitoValidazione.DatiProgetto.DurataIniziativa;

				if (this.Errori.Count > 0)
				{
					if (String.IsNullOrEmpty(ConfigurationManager.AppSettings["BandiUmbriaService.ignoraErroriValidazione"]))
					{
						this.Master.MostraBottoneAvanti = false;
					}
				}

				this.pnlNessunErrore.Visible = this.Errori.Count == 0 && this.Avvertimenti.Count == 0;
			}
			catch (Exception ex)
			{
				this.Errori.Add(ex.Message);
			}
        }

        public List<string> Avvertimenti
        {
            get;set;
        }

        protected override void MostraErrori()
        {
			BindItemsList(rptErrori, this.Errori);
			BindItemsList(rptAvvisi, this.Avvertimenti);
        }

		private void BindItemsList(Repeater repeater, IEnumerable<string> items)
		{
			repeater.DataSource = items;
			repeater.DataBind();

			repeater.Visible = items.Count() > 0;
		}

    }
}