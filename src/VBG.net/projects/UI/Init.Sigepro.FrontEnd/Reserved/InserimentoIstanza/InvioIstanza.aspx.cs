using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class InvioIstanza : IstanzeStepPage
	{
		private static class Constants
		{
			public static class Viste
			{
				public const int AllegaFile = 0;
				public const int Firma = 1;
				public const int Errore = 2;
			}
		}

		[Inject]
		public AllegatiInterventoService AllegatiInterventoService { get; set; }
		
		[Inject]
		public InvioDomandaService InvioDomandaService { get; set; }

		[Inject]
		public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		#region Parametri letti dal file di workflow
        public bool UsaFirmaCidPin
        {
            get { object o = this.ViewState["UsaFirmaCidPin"]; return o == null ? false : (bool)o; }
            set { this.ViewState["UsaFirmaCidPin"] = value; }
        }

        public bool UsaFirmaGrafometrica
        {
            get { object o = this.ViewState["UsaFirmaGrafometrica"]; return o == null ? false : (bool)o; }
            set { this.ViewState["UsaFirmaGrafometrica"] = value; }
        }

        public bool UsaFirmaOnline
        {
            get { object o = this.ViewState["UsaFirmaOnline"]; return o == null ? true : (bool)o; }
            set { this.ViewState["UsaFirmaOnline"] = value; }
        }

        public string MessaggioConfermaFirmaDispositivoEsterno
        {
            get { object o = this.ViewState["MessaggioConfermaFirmaDispositivoEsterno"]; return o == null ? "Si è sicuri di voler firmare la domanda tramite un dispositivo esterno" : (string)o; }
            set { this.ViewState["MessaggioConfermaFirmaDispositivoEsterno"] = value; }
        }
        

		public string TitoloFaseInvio
		{
			get { object o = this.ViewState["TitoloFaseInvio"]; return o == null ? "Sottoscrizione e invio dell'istanza" : (string)o; }
			set { this.ViewState["TitoloFaseInvio"] = value; }
		}		


		public string SottotitoloFaseInvio
		{
			get { return ltrSottotitoloInvio.Text; }
			set { ltrSottotitoloInvio.Text = value; }
		}

		public string DescrizioneFaseInvio
		{
			get { return ltrDescrizioneFaseInvio.Text; }
			set { ltrDescrizioneFaseInvio.Text = value; }

		}

		public string EtichettaFileDaScaricare
		{
			get { return ltrNomeFiledaScaricare.Text; }
			set { ltrNomeFiledaScaricare.Text = value; }
		}

		public string TitoloGrigliaSottoscrittori
		{
			get { return ltrIntestazioneSottoscrittori.Text; }
			set { ltrIntestazioneSottoscrittori.Text = value; }
		}

		public string TitoloGrigliaNonSottoscrittori
		{
			get { return ltrSoggetiNonSottoscrittori.Text; }
			set { ltrSoggetiNonSottoscrittori.Text = value; }
		}

		public bool AggiungiSchedeNonFirmateARiepilogoAllegati
		{
			get { object o = this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"]; return o == null ? true : (bool)o; }
			set { this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"] = value; }
		}

		public string TestoBottoneTrasferisciIstanza
		{
			get { return cmdInviaDomanda.Text; }
			set { cmdInviaDomanda.Text = value; }
		}

        public bool VerificaFirmaSuRiepilogo
        {
            get { object o = this.ViewState["VerificaFirmaSuRiepilogo"]; return o == null ? true : (bool)o; }
            set 
            { 
                this.ViewState["VerificaFirmaSuRiepilogo"] = value;

                // per compatibilità con alcune vecchie installazioni
                if (!value)
                {
                    RichiediFirmaAutografa = true;
                }
            }
        }

        public bool RichiediFirmaAutografa
        {
            get { object o = this.ViewState["RichiediFirmaAutografa"]; return o == null ? false : (bool)o; }
            set { this.ViewState["RichiediFirmaAutografa"] = value; }
        }


        public string TestoDichiarazione
        {
            get { object o = this.ViewState["TestoDichiarazione"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TestoDichiarazione"] = value; }
        }

        public string TestoCheckDichiarazione
        {
            get { object o = this.ViewState["TestoCheckDichiarazione"]; return o == null ? "Dichiaro di accettare" : (string)o; }
            set { this.ViewState["TestoCheckDichiarazione"] = value; }
        }

        public bool MostraGrigliaSottoscrittori
        {
            get { object o = this.ViewState["MostraGrigliaSottoscrittori"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraGrigliaSottoscrittori"] = value; }
        }

		#endregion

		protected bool AllegaRiepilogo
		{
			get 
			{
				var qs = Request.QueryString["AllegaRiepilogo"];

				if (String.IsNullOrEmpty(qs))
					return false;

				return Boolean.Parse(qs);
			}
		}


		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
                if (AllegaRiepilogo)
                {
                    AllegatiInterventoService.EliminaOggettoRiepilogoDomanda(IdDomanda);

                    if (!RichiediFirmaAutografa)
                    {
                        AllegaRiepilogoDomanda();
                    }
                }

				DataBind();
			}
		}

		private void AllegaRiepilogoDomanda()
		{
			AllegatiInterventoService.RigeneraRiepilogoDomanda(IdDomanda);
		}

		public override void DataBind()
		{
			this.Title = TitoloFaseInvio;

			// Soggetti che sottoscrivono l'istanza
			gvSoggettiFirmatari.DataSource = ReadFacade.Domanda.Anagrafiche.GetSoggettiSottoscrittori();
			gvSoggettiFirmatari.DataBind();

			// Soggetti non sottoscrittori
			var rows = ReadFacade.Domanda.Anagrafiche.GetSoggettiNonSottoscrittori();

			pnlSoggettiNonSottoscriventi.Visible = rows.Count() > 0;

			gvSoggettiNonSottoscriventi.DataSource = rows;
			gvSoggettiNonSottoscriventi.DataBind();
            
			// Vista dell'allegato
			var allegato = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente;

            if (allegato == null)
            {
                MostraVistaUploadFile();
            }
            else
            {
                MostraVistaFirmaOInviaFile();
            }
		}

		private void MostraVistaFirmaOInviaFile()
		{
			mvInvioIstanza.ActiveViewIndex = Constants.Viste.Firma;

			var allegato = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente;
            var firmatoDigitalmente = allegato.FirmatoDigitalmente || !VerificaFirmaSuRiepilogo;

            lblErroreRiepilogo.Visible              = !firmatoDigitalmente;
            cmdFirma.Visible                        = !firmatoDigitalmente && UsaFirmaOnline;
            cmdFirmaCidPin.Visible                  = !firmatoDigitalmente && UsaFirmaCidPin;
            cmdFirmaGrafometrica.Visible            = !firmatoDigitalmente && UsaFirmaGrafometrica;
            cmdFirnaConDispositivoEsterno.Visible   = !firmatoDigitalmente;
            cmdInviaDomanda.Visible                 = firmatoDigitalmente;
            cmdAllegaAltroFile.Visible              = firmatoDigitalmente;

            // Dichiarazione
            lblDichiarazione.Text = TestoDichiarazione;
            chkDichiarazione.Text = TestoCheckDichiarazione;
            pnlDichiarazione.Visible = firmatoDigitalmente && !String.IsNullOrEmpty(TestoDichiarazione);

            if (!String.IsNullOrEmpty(MessaggioConfermaFirmaDispositivoEsterno)) {
                cmdFirnaConDispositivoEsterno.OnClientClick = "return confirm('" + MessaggioConfermaFirmaDispositivoEsterno.Replace("'", "\\'").Replace("\r", "").Replace("\n", "") + "')";
            }
            
			var url = "~/Reserved/MostraOggettoFo.ashx?IdComune={0}&Software={1}&CodiceOggetto={2}&Token={3}&IdPresentazione={4}";

			hlRiepilogoDomanda.NavigateUrl = String.Format(url, IdComune, Software , allegato.CodiceOggetto, UserAuthenticationResult.Token , IdDomanda);
			hlRiepilogoDomanda.Text = allegato.NomeFile;
		}

		private void MostraVistaUploadFile()
		{
			mvInvioIstanza.ActiveViewIndex = Constants.Viste.AllegaFile;

			var rigaRiepilogo = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

			// Soggetti sottoscrittori
			hlModelloDomanda.NavigateUrl = String.Format("~/Reserved/InserimentoIstanza/DownloadOggettoCompilabile.ashx?IdComune={0}&Token={1}&Software={2}&CodiceOggetto={3}&IdPresentazione={4}&Fmt={5}&md5={6}&PdfSchedeNf={7}",
														IdComune,
														UserAuthenticationResult.Token,
														Software,
														rigaRiepilogo.CodiceOggettoModello,
														IdDomanda,
														"PDF",
														rigaRiepilogo.Id,
														AggiungiSchedeNonFirmateARiepilogoAllegati);
        }

		protected void cmdUploadDomanda_Click(Object sender, EventArgs e)
		{
			try
			{
				var file = new BinaryFile(fuRiepilogo, this._validPostedFileSpecification);
				AllegatiInterventoService.SalvaOggettoRiepilogo(IdDomanda, file);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		protected void cmdFirma_Click(object sender, EventArgs e)
		{
			var codiceOggetto = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente.CodiceOggetto;

			Master.Redirect.ToFirmaDigitale(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, codiceOggetto);
		}

        protected void cmdFirmaCidPin_Click(object sender, EventArgs e)
		{
			var codiceOggetto = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente.CodiceOggetto;

			Master.Redirect.ToFirmaCidPin(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, codiceOggetto);
		}


        protected void cmdFirmaGrafometrica_Click(object sender, EventArgs e)
        {
            var codiceOggetto = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente.CodiceOggetto;

            Master.Redirect.ToFirmaGrafometrica(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, codiceOggetto);
        }


        protected void cmdFirnaConDispositivoEsterno_Click(object sender, EventArgs e)
		{
			AllegatiInterventoService.EliminaOggettoRiepilogoDomanda(IdDomanda);

			DataBind();
		}

		protected void cmdInviaDomanda_Click(object sender, EventArgs e)
		{
            if (!ReadFacade.Interventi.InterventoAttivo(ReadFacade.Domanda.AltriDati.Intervento.Codice, UserAuthenticationResult.LivelloAutenticazione, UserAuthenticationResult.DatiUtente.UtenteTester))
			{
				this.Errori.Add("L'intervento selezionato non è attivabile tramite domanda online. Selezionare un nuovo intervento.");

				return;
			}

			var risultato = InvioDomandaService.Invia(IdDomanda, String.Empty);

			if (risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito || risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscitoNoBackend)
			{
				Redirect("~/Reserved/InserimentoIstanza/CertificatoInvio.aspx", qs => {
					qs.Add("Id" , risultato.CodiceIstanza);
					qs.Add("IdPresentazione=", this.IdDomanda);
				});
			}

			// mostro la view vei messaggi e nascondo il paginatore
			mvInvioIstanza.ActiveViewIndex = Constants.Viste.Errore;
			Master.MostraPaginatoreSteps = false;
			lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore(IdDomanda);
		}
	}
}