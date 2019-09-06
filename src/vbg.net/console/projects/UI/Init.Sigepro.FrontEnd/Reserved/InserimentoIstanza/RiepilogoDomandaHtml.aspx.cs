using System;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class RiepilogoDomandaHtml : IstanzeStepPage
	{
		private static class Constants
		{
			public const int IdVistaRiepilogo = 0;
			public const int IdVistaErrore = 1;
		}

		[Inject]
		public AllegatiInterventoService _allegatiInterventoService { get; set; }
		[Inject]
		public InvioDomandaService _invioDomandaService { get; set; }
		[Inject]
		public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

 		#region Parametri letti dal file xml
		
		public string DescrizioneFaseRiepilogo
		{
			get { return ltrDescrizioneFaseRiepilogo.Text; }
			set { ltrDescrizioneFaseRiepilogo.Text = value; }
		}

		public string TitoloFaseInvio
		{
			get { object o = this.ViewState["TitoloFaseInvio"]; return o == null ? "Sottoscrizione e invio dell'istanza" : (string)o; }
			set { this.ViewState["TitoloFaseInvio"] = value; }
		}		

		public bool AggiungiSchedeNonFirmateARiepilogoAllegati
		{
			get { object o = this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"]; return o == null ? true : (bool)o; }
			set { this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"] = value; }
		}

        public bool MostraRiepilogoDomanda
        {
            get { object o = this.ViewState["MostraRiepilogoDomanda"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraRiepilogoDomanda"] = value; }
        }

        public string UrlRedirectInvioRiuscito
        {
            get { object o = this.ViewState["UrlRedirectInvioRiuscito"]; return o == null ? "~/Reserved/InserimentoIstanza/CertificatoInvio.aspx" : (string)o; }
            set { this.ViewState["UrlRedirectInvioRiuscito"] = value; }
        }

		#endregion

		protected void Page_Load(object sender, EventArgs e)
		{
			// Il salvataggio viene effettuato dal service
			Master.IgnoraSalvataggioDati = true;
            Master.MostraBottoneInviaDomanda = true;

            Master.TestoBottoneInviaDomanda = RiepilogoRichiedeFirma() ? "Procedi" : "Invia domanda";
            Master.InviaDomanda += cmdProcedi_Click;

			if (!IsPostBack)
			{
				_allegatiInterventoService.EliminaOggettoRiepilogoDomanda(IdDomanda);
				DataBind();
			}
		}

		public override void OnInitializeStep()
		{
			_allegatiInterventoService.EliminaRiepiloghiDomandaInEccesso(IdDomanda);
		}

		public override void DataBind()
		{
            var esitoVerificaAccesso = ReadFacade.Interventi.VerificaAccessoIntervento(ReadFacade.Domanda.AltriDati.Intervento.Codice, UserAuthenticationResult.LivelloAutenticazione, UserAuthenticationResult.DatiUtente.UtenteTester);

            if (esitoVerificaAccesso != AppLogic.AreaRiservataService.RisultatoVerificaAccessoIntervento.Accessibile)
			{
				this.Errori.Add("L'intervento selezionato non è attivabile tramite domanda online. Selezionare un nuovo intervento.");

				Master.MostraBottoneInviaDomanda = false;

				return;
			}


			try
			{
				var rigaRiepilogo = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		/// <summary>
		/// Ottiene l'url da utilizzare per generare il riepilogo domanda
		/// </summary>
		/// <returns></returns>
		protected string GetUrlRiepilogoDomanda()
		{

            var ub = new UrlBuilder();
            var urlDownloadPdf = Page.ResolveUrl( ub.Build("~/Reserved/InserimentoIstanza/DownloadRiepilogoDomanda.ashx", qs =>
            {
                qs.Add(new QsSoftware(Software));
                qs.Add(new QsAliasComune(IdComune));
                qs.Add(new QsIdDomandaOnline(IdDomanda));
                qs.Add("PdfSchedeNf", AggiungiSchedeNonFirmateARiepilogoAllegati);
            }));

            var viewerPath = ResolveClientUrl("~/js/lib/pdf.js/web/viewer.html?file=" + Server.UrlEncode(urlDownloadPdf));

            return viewerPath;
		}

        protected string GetUrlVersioneStampabile()
        {
            var rigaRiepilogo = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

            return BuildClientUrl("~/Reserved/InserimentoIstanza/DownloadOggettoCompilabile.ashx", qs => {
                qs.Add("CodiceOggetto", rigaRiepilogo.CodiceOggettoModello);
                qs.Add("IdPresentazione", IdDomanda);
                qs.Add("Fmt", "PDF");
                qs.Add("PdfSchedeNf", AggiungiSchedeNonFirmateARiepilogoAllegati);
            });
        }


		protected void cmdProcedi_Click(object sender, EventArgs e)
		{
			this.Title = TitoloFaseInvio;

			if (RiepilogoRichiedeFirma())
			{
				var redirectFmtStr = String.Format("~/Reserved/InserimentoIstanza/InvioIstanza.aspx?{0}&AllegaRiepilogo=True", Request.QueryString);

				Response.Redirect(redirectFmtStr);

				//MostraVistaUploadDomanda();
			}
			else
			{
				InviaDomanda();
			}
		}

		protected bool RiepilogoRichiedeFirma()
		{
			return ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().RichiedeFirmaDigitale;
		}

		/// <summary>
		/// Effettua la trasmisisone della domanda al backoffice
		/// </summary>
		private void InviaDomanda()
		{
			_allegatiInterventoService.RigeneraRiepilogoDomanda( IdDomanda );

			var risultato = _invioDomandaService.Invia(IdDomanda, String.Empty);

			if ( risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito || risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscitoNoBackend)
			{
				Redirect(this.UrlRedirectInvioRiuscito, qs =>
				{
					qs.Add("Id", risultato.CodiceIstanza);
					qs.Add("IdPresentazione", this.IdDomanda);
				});
				return;
			}

			// mostro la view vei messaggi e nascondo il paginatore
			multiView.ActiveViewIndex = Constants.IdVistaErrore;
			lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore( IdDomanda );
            this.Master.MostraPaginatoreSteps = false;
		}
	}


}
