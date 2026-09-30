using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

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
        public IConfigurazione<ParametriAspetto> _parametriConfigurazione { get; set; }

        [Inject]
        public IRedirectFineDomandaService _redirectFineDomandaService { get; set; }

        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioService { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        [Inject]
        public IQuestionarioSoddisfazioneService _questionarioSoddisfazioneService { get; set; }

        [Inject]
        public IVisuraService _visuraService { get; set; }


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

            var codiceOggetto = this._certificatoDiInvioService.GetCodiceOggettoCertificatoDiInvioDaIdDomandaBackoffice(Convert.ToInt32(Id));

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

            this.divQuestionarioGradimento.Visible = this._questionarioSoddisfazioneService.CompilazioneQuestionarioAttiva;
        }

        public string UrlDownloadRiepilogo
        {
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

            return Page.ResolveUrl(_urlDownloadOggettiService.GetUrlDownload(codiceOggetto.Value));
        }

        protected void cmdDettagli_Click(object sender, EventArgs e)
        {
            Redirect("~/Reserved/DettaglioIstanzaEx.aspx", qs => qs.Add("Id", Id));
        }

        protected void cmdRedirectProcedi_Click(object sender, EventArgs e)
        {
            Response.Redirect(this._redirectFineDomandaService.GeneraUrlRedirect(this.IdDomandaFrontoffice.Value));
        }

        protected void cmdCompilaQuestionario_Click(object sender, EventArgs e)
        {
            var uuid = _visuraService.GetUUIDDaCodiceIstanza(Convert.ToInt32(this.Id));

            var url = UrlBuilder.Url("~/reserved/questionario/compila.aspx", qs => {
                qs.Add(new QsAliasComune(IdComune));
                qs.Add(new QsSoftware(Software));
                qs.Add(new QsUuidIstanza(uuid));
            });

            Response.Redirect(url);
        }
    }
}
