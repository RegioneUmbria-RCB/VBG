using System;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using log4net;
using Ninject;

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
        

		#endregion

		protected void Page_Load(object sender, EventArgs e)
		{
			// Il salvataggio viene effettuato dal service
			Master.IgnoraSalvataggioDati = true;

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
            if (!ReadFacade.Interventi.InterventoAttivo(ReadFacade.Domanda.AltriDati.Intervento.Codice, UserAuthenticationResult.LivelloAutenticazione, UserAuthenticationResult.DatiUtente.UtenteTester))
			{
				this.Errori.Add("L'intervento selezionato non è attivabile tramite domanda online. Selezionare un nuovo intervento.");

				cmdProcedi.Visible = false;

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
			var rigaRiepilogo = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();
			
			var fmtStr = "{0}?Token={1}&IdComune={2}&Software={3}&IdPresentazione={4}&Fmt={5}&CodiceOggetto={6}&asAttachment={7}&PdfSchedeNf={8}";

			return String.Format( fmtStr , ResolveClientUrl( "~/Reserved/InserimentoIstanza/DownloadOggettoCompilabile.ashx" ),
											UserAuthenticationResult.Token,
											IdComune,
											Software,
											IdDomanda,
											"HTML",
											rigaRiepilogo.CodiceOggettoModello,
											"false",
											AggiungiSchedeNonFirmateARiepilogoAllegati);
		}

        protected string GetUrlVersioneStampabile()
        {
            var rigaRiepilogo = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();
			
            return ResolveClientUrl(String.Format("~/Reserved/InserimentoIstanza/DownloadOggettoCompilabile.ashx?IdComune={0}&Token={1}&Software={2}&CodiceOggetto={3}&IdPresentazione={4}&Fmt={5}&PdfSchedeNf={6}",
                                        IdComune,
                                        UserAuthenticationResult.Token,
                                        Software,
                                        rigaRiepilogo.CodiceOggettoModello,
                                        IdDomanda,
                                        "PDF",
                                        AggiungiSchedeNonFirmateARiepilogoAllegati));
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
				Redirect("~/Reserved/InserimentoIstanza/CertificatoInvio.aspx", qs =>
				{
					qs.Add("Id", risultato.CodiceIstanza);
					qs.Add("IdPresentazione", this.IdDomanda);
				});
				return;
			}

			// mostro la view vei messaggi e nascondo il paginatore
			multiView.ActiveViewIndex = Constants.IdVistaErrore;
			Master.MostraPaginatoreSteps = false;
			lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore( IdDomanda );
		}
	}


}
