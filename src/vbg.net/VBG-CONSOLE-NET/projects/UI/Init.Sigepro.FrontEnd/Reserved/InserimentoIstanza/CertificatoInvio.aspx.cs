using System;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class CertificatoInvio : ReservedBasePage
	{
		private static class Constants
		{
			public const int IdViewRiepilogo = 0;
			public const int IdViewnoriepilogo = 1;
		}


		[Inject]
		public CertificatoDiInvioService _certificatoDiInvioService { get; set; }
		[Inject]
		public IConfigurazione<ParametriVbg> _parametriConfigurazione { get; set; }
        [Inject]
        public IRedirectFineDomandaService _redirectFineDomandaService { get; set; }

        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioService { get; set; }


		/// <summary>
		/// Id univoco dell'istanza nel backend
		/// </summary>
		string Id
		{
			get { return Request.QueryString["Id"]; }
		}


		/// <summary>
		/// Testo da visualizzare in testa al certificato di invio
		/// </summary>
		public string TestoFineSottoscrizione
		{
			get { object o = this.ViewState["CertificatoFineSottoscrizione"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["CertificatoFineSottoscrizione"] = value; }
		}

        public int CodiceOggettoRiepilogo
        {
            get { object o = this.ViewState["CodiceOggettoRiepilogo"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceOggettoRiepilogo"] = value; }
        }

        public QsIdDomandaOnline IdDomandaFrontoffice
        {
            get
            {
                return new QsIdDomandaOnline(Request.QueryString);
            }
        }


    	protected void Page_Load(object sender, EventArgs e)
		{
			this.Title = "Certificato di invio";

			if (!IsPostBack)
				DataBind();
		}


        public override void DataBind()
        {
            ltrDescrizione.Text = this._parametriConfigurazione.Parametri.IntestazioneCertificatoInvio;

            var codiceOggetto = this._certificatoDiInvioService.GetCodiceOggettoCertificatoDiInvioDaIdDomandaBackoffice(Id);

            if (codiceOggetto.GetValueOrDefault(-1) == -1)
            {
                Redirect("~/Reserved/DettaglioIstanzaV2.aspx", qs => qs.Add("Id", Id));
                return;
            }

            this.CodiceOggettoRiepilogo = codiceOggetto.Value;


            // Gestione del redirect a fine domanda
            var domanda = this._salvataggioService.GetById(IdDomandaFrontoffice.Value);
            var idIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;

            this.divRedirect.Visible = this._redirectFineDomandaService.RedirectAFineDomandaAttivo(idIntervento);

            if (this.divRedirect.Visible)
            {
                var testi = this._redirectFineDomandaService.GetTestiBox();

                if (testi == null)
                {
                    this.ltrRedirectTitolo.Text = "Redirect a fine domanda";
                    this.ltrRedirectMessaggio.Text = "Attenzione! Impossibile trovare il file di risorse specificato in configurazione";
                    this.cmdRedirectProcedi.Text = "Procedi";
                }
                else
                {
                    this.ltrRedirectTitolo.Text = testi.Titolo;
                    this.ltrRedirectMessaggio.Text = testi.Messaggio;
                    this.cmdRedirectProcedi.Text = testi.TestoBottone;
                }


            }
		}

        public string UrlDownloadRiepilogo {
            get
            {
                return GeneraUrlDownloadRiepilogo(this.CodiceOggettoRiepilogo);
            }
        }
        public string UrlVisualizzazioneRiepilogo
        {
            get
            {
                var urlDownloadPdf = Server.UrlEncode(this.UrlDownloadRiepilogo);
                var viewerPath = ResolveClientUrl("~/js/lib/pdf.js/web/viewer.html?file=" + urlDownloadPdf);

                return viewerPath;
            }
        }

		public string GeneraUrlDownloadRiepilogo(int? codiceOggetto)
		{
			if (!codiceOggetto.HasValue)
				return String.Empty;

            var ub = new UrlBuilder();
            var url = Page.ResolveUrl( ub.Build("~/MostraOggetto.ashx", pb =>
            {
                pb.Add(new QsAliasComune(IdComune));
                pb.Add(new QsSoftware(Software));
                pb.Add("CodiceOggetto", codiceOggetto.Value);
                pb.Add("STC", "1");
            }));

            return url;
		}

		protected void cmdDettagli_Click(object sender, EventArgs e)
		{
			Redirect("~/Reserved/DettaglioIstanzaEx.aspx", qs => qs.Add("Id", Id));
		}

        protected void cmdRedirectProcedi_Click(object sender, EventArgs e)
        {
            Response.Redirect(this._redirectFineDomandaService.GeneraUrlRedirect(this.IdDomandaFrontoffice.Value));
        }
    }
}
