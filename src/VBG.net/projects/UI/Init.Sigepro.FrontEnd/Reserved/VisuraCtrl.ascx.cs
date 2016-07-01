using System;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Entities.Visura;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class VisuraCtrl : System.Web.UI.UserControl
	{
		[Inject]
		public IVisuraRepository _visuraRepository { get; set; }
		[Inject]
		public IScadenzeService _scadenzeService { get; set; }
		[Inject]
		public IConfigurazione<ParametriVisura> _configurazione { get; set; }

		public delegate void ScadenzaSelezionataDelegate(object sender, string idScadenza);
		public event ScadenzaSelezionataDelegate ScadenzaSelezionata;

		private DatiDettaglioPratica m_dataSource;

		public DatiDettaglioPratica DataSource
		{
			get { return m_dataSource; }
			set { m_dataSource = value; }
		}

		public VisuraCtrl()
		{
			FoKernelContainer.Inject(this);
		}


		protected void Page_Load(object sender, EventArgs e)
		{
		}

		public void EffettuaVisuraIstanza(string idComune , string software, int codiceIstanza)
		{
			DatiDettaglioPratica pratica = _visuraRepository.GetDettaglioPratica(idComune, codiceIstanza.ToString());
			
			this.DataSource = pratica;
			this.DataBind();

			if (!IsPostBack)
				ltrIntestazioneDettaglio.Text = _configurazione.Parametri.MessaggioIntestazioneVisura;

			var listaScadenze = _scadenzeService.GetListaScadenzeByNumeroIstanza( software , pratica.NumeroPratica);

			dgScadenze.DataSource = listaScadenze;
			dgScadenze.DataBind();
		}

		public override void DataBind()
		{
			lblNumeroPratica.Text = DataSource.NumeroPratica;
			lblDataPresentazione.Text = DataSource.DataPresentazione;
			lblOggetto.Text = DataSource.Oggetto;
			lblIntervento.Text = DataSource.DescrizioneIntervento;
			lblStatoPratica.Text = DataSource.StatoPratica;
			lblProtocollo.Text = DataSource.NumeroProtocollo;
			lblDataProtocollo.Text = DataSource.DataProtocollo;
			lblIstruttore.Text = DataSource.Istruttore;
			lblOperatore.Text = DataSource.Operatore;
			lblResponsabileProc.Text = DataSource.ResponsabileProc;

			dgAltridati.DataSource = DataSource.AltreInfo;
			dgAltridati.DataBind();

			dgMovimenti.DataSource = DataSource.Movimenti;
			dgMovimenti.DataBind();

			dgOneri.DataSource = DataSource.Oneri;
			dgOneri.DataBind();

			dgProcedimenti.DataSource = DataSource.Procedimenti;
			dgProcedimenti.DataBind();

			dgSoggetti.DataSource = DataSource.SoggettiCollegati;
			dgSoggetti.DataBind();

			dgLocalizzazioni.DataSource = DataSource.Localizzazioni;
			dgLocalizzazioni.DataBind();

			dgDatiCatastali.DataSource = DataSource.DatiCatastali;
			dgDatiCatastali.DataBind();

			dgAutorizzazioni.DataSource = DataSource.Autorizzazioni;
			dgAutorizzazioni.DataBind();


		}

		public string EsitoMovimento(object val)
		{
			if ( val == null || String.IsNullOrEmpty( val.ToString() ) ) return String.Empty;

			if (val.ToString() == "0") return "Negativo";

			return "Positivo";
		}

		public void dgScadenze_SelectedIndexChanged(object sender, EventArgs e)
		{
			string scadenza = dgScadenze.DataKeys[dgScadenze.SelectedIndex].Value.ToString();

			if (ScadenzaSelezionata != null)
				ScadenzaSelezionata(this, scadenza);
		}
	}
}