using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.DenunceTares;
using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce
{
	public partial class SelezioneUtenza : IstanzeStepPage
	{
		private static class Constants
		{
			public const int IdVistaRicerca = 0;
			public const int IdVistaDettaglio = 1;
            public const string SessionKey = "SelezioneUtenza.DatiContribuente";
		}

		[Inject]
		protected IComuniService _comuniService { get; set; }

		[Inject]
		protected DenunceTaresBariService _taresService { get; set; }

        public string TestoRicerca
        {
            get { return ltrTestoRicerca.Text; }
            set { ltrTestoRicerca.Text = value; }
        }

        public string TestoDettaglio
        {
            get { return ltrTestoDettaglio.Text; }
            set { ltrTestoDettaglio.Text = value; }
        }
        

		public int CodiceTipoSoggettoPersonaFisica
		{
			get { object o = this.ViewState["CodiceTipoSoggettoPersonaFisica"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["CodiceTipoSoggettoPersonaFisica"] = value; }
		}

		public int CodiceTipoSoggettoPersonaGiuridica
		{
			get { object o = this.ViewState["CodiceTipoSoggettoPersonaGiuridica"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["CodiceTipoSoggettoPersonaGiuridica"] = value; }
		}

		public int CodiceTipoSoggettoLegaleRappresentante
		{
			get { object o = this.ViewState["CodiceTipoSoggettoLegaleRappresentante"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["CodiceTipoSoggettoLegaleRappresentante"] = value; }
		}

        public int NumeroMassimoUtenzeGestibili
        {
            get { return this.selezioneUtenzeMultiple.NumeroMassimoUtenzeGestibili; }
            set { this.selezioneUtenzeMultiple.NumeroMassimoUtenzeGestibili = value; }
        }

        public string MessaggioErroreLimiteUtenzeSuperato
        {
            get { return this.selezioneUtenzeMultiple.MessaggioErroreLimiteUtenzeSuperato; }
            set { this.selezioneUtenzeMultiple.MessaggioErroreLimiteUtenzeSuperato = value; }
        }

        public TipoUtenzaTaresEnum TipoUtenza
        {
            get { return (TipoUtenzaTaresEnum)Enum.Parse(typeof(TipoUtenzaTaresEnum), TipoUtenzaString); }
            set { this.TipoUtenzaString = value.ToString(); }
        }

        public string TipoUtenzaString
        {
            get { object o = this.ViewState["TipoUtenzaString"]; return o == null ? "Domestica" : (string)o; }
            set { this.ViewState["TipoUtenzaString"] = value; }
        }

        private DatiAnagraficiContribuenteDenunciaTares DatiContribuente
        {
            get { return (DatiAnagraficiContribuenteDenunciaTares)Session[Constants.SessionKey]; }
            set { Session[Constants.SessionKey] = value; }
        }


		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
                selezioneUtenzeMultiple.Visible = false;

                this.DatiContribuente = _taresService.GetUtenzaSelezionata(IdDomanda);

                if (this.DatiContribuente != null)
				{
					MostraVistaDettaglio();
				}
				else
				{
					MostraVistaRicerca();
				}
			}
		}

		public override bool CanExitStep()
		{
			try
			{
				this._taresService.InserisciUtenzaTraAnagrafiche(IdDomanda, CodiceTipoSoggettoPersonaFisica, CodiceTipoSoggettoPersonaGiuridica, CodiceTipoSoggettoLegaleRappresentante);
			}
			catch (Exception ex)
			{
				this.Errori.Add(ex.Message);
			}

			return this.Errori.Count == 0;
		}


		protected void cmdCerca_Click(object sender, EventArgs e)
		{
			try
			{
				if (String.IsNullOrEmpty(txtIdContribuente.Text.Trim()))
				{
					throw new Exception("Immettere un identificativo contribuente");
				}


				if (String.IsNullOrEmpty(txtPartitaIvaCf.Text.Trim()))
				{
					throw new Exception("Immettere un codice fiscale o una partita iva");
				}

                this.DatiContribuente = this._taresService.GetDatiContribuente(UserAuthenticationResult.DatiUtente, txtIdContribuente.Text, txtPartitaIvaCf.Text.ToUpper());

                selezioneUtenzeMultiple.TipoUtenza = this.TipoUtenza;
                selezioneUtenzeMultiple.DataSource = this.DatiContribuente;
                selezioneUtenzeMultiple.DataBind();			
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		private void MostraVistaRicerca()
		{
			this.multiView.ActiveViewIndex = Constants.IdVistaRicerca;
            
            this.Master.MostraBottoneAvanti = false;
		}

		private void MostraVistaDettaglio()
		{
            this.DatiContribuente = _taresService.GetUtenzaSelezionata(IdDomanda);

            this.txtIdContribuente.Text = this.DatiContribuente.IdContribuente.ToString();
            this.txtPartitaIvaCf.Text = String.IsNullOrEmpty(this.DatiContribuente.CodiceFiscale) ? this.DatiContribuente.CodiceFiscale : this.DatiContribuente.PartitaIva;

            this.selezioneUtenzeMultiple.DataSource = this.DatiContribuente;
            this.selezioneUtenzeMultiple.DataBind();

            this.utenzaSelezionabileItem.TipoUtenza = this.TipoUtenza;
            this.utenzaSelezionabileItem.DataSource = this.DatiContribuente;
			this.utenzaSelezionabileItem.DataBind();

			this.multiView.ActiveViewIndex = Constants.IdVistaDettaglio;

			this.Master.MostraBottoneAvanti = true;
		}

		protected void cmdSelezionacontribuente_Click(object sender, EventArgs e)
		{
			MostraVistaRicerca();
		}

        protected void selezioneUtenzeMultiple_Errore(object sender, string messaggio)
        {
            this.Errori.Add(messaggio);
        }

        protected void selezioneUtenzeMultiple_UtenzeSelezionate(object sender, IEnumerable<string> idUtenze)
        {
            this.DatiContribuente.MantieniUtenze(idUtenze);

            this._taresService.ImpostaContribuente(IdDomanda, this.DatiContribuente);

            MostraVistaDettaglio();
        }
	}
}