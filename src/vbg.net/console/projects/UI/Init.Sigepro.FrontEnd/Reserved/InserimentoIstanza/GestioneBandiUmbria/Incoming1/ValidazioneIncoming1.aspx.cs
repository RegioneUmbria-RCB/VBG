using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using Ninject;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.Incoming1
{
    public partial class ValidazioneIncoming1 : IstanzeStepPage
    {


        [Inject]
        protected IBandiIncomingService _service { get; set; }

        public ValidazioneIncoming1()
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
                var esitoValidazione = this._service.ValidaBandoIncoming(IdDomanda);

                this.Errori.AddRange(esitoValidazione.Errori);
                this.Avvertimenti.AddRange(esitoValidazione.Avvisi);

                // this.txtTitoloProgetto.Text = esitoValidazione.DatiProgetto.TitoloProgetto;
                // this.txtAcronimo.Text = esitoValidazione.DatiProgetto.Acronimo;
                // this.txtTipologia.Text = esitoValidazione.DatiProgetto.TipologiaAggregazione;
                // this.txtDurata.Text = esitoValidazione.DatiProgetto.DurataIniziativa;

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
            get;
            set;
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