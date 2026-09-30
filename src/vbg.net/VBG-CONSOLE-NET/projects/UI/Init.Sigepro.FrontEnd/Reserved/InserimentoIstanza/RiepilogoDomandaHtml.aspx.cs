using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

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
        [Inject]
        public IInterventiRepository _interventiRepository { get; set; }

        #region Parametri letti dal file xml

        public string DescrizioneFaseRiepilogo
        {
            get { return this.ltrDescrizioneFaseRiepilogo.Text; }
            set { this.ltrDescrizioneFaseRiepilogo.Text = value; }
        }

        public string TitoloFaseInvio
        {
            get { var o = this.ViewState["TitoloFaseInvio"]; return o == null ? "Sottoscrizione e invio dell'istanza" : (string)o; }
            set { this.ViewState["TitoloFaseInvio"] = value; }
        }

        public bool AggiungiSchedeNonFirmateARiepilogoAllegati
        {
            get { var o = this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"]; return o == null ? true : (bool)o; }
            set { this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"] = value; }
        }

        public bool MostraRiepilogoDomanda
        {
            get { var o = this.ViewState["MostraRiepilogoDomanda"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraRiepilogoDomanda"] = value; }
        }

        public string UrlRedirectInvioRiuscito
        {
            get { var o = this.ViewState["UrlRedirectInvioRiuscito"]; return o == null ? "~/Reserved/InserimentoIstanza/CertificatoInvio.aspx" : (string)o; }
            set { this.ViewState["UrlRedirectInvioRiuscito"] = value; }
        }

        #endregion

        protected void Page_Load(object sender, EventArgs e)
        {
            // Il salvataggio viene effettuato dal service
            this.Master.IgnoraSalvataggioDati = true;
            this.Master.MostraBottoneInviaDomanda = true;

            this.Master.TestoBottoneInviaDomanda = this.RiepilogoRichiedeFirma() ? "Procedi" : "Invia domanda";
            this.Master.InviaDomanda += this.cmdProcedi_Click;

            if (!this.IsPostBack)
            {
                this._allegatiInterventoService.EliminaOggettoRiepilogoDomanda(this.IdDomanda);
                this.DataBind();
            }
        }

        public override void OnInitializeStep()
        {
            this._allegatiInterventoService.EliminaRiepiloghiDomandaInEccesso(this.IdDomanda);
        }

        public override void DataBind()
        {
            var codiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;
            var esitoVerificaAccesso = this._interventiRepository.VerificaAccessoIntervento(this.ReadFacade.Domanda.AltriDati.Intervento.Codice, this.UserAuthenticationResult.LivelloAutenticazione, this.UserAuthenticationResult.DatiUtente.UtenteTester, codiceComune);

            if (!esitoVerificaAccesso.Is(TipoAccessibilitaIntervento.Accessibile))
            {
                this.Errori.Add($"{esitoVerificaAccesso.MessaggioErrore}. Selezionare un nuovo intervento.");

                this.Master.MostraBottoneInviaDomanda = false;

                return;
            }


            try
            {
                var rigaRiepilogo = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        /// <summary>
        /// Ottiene l'url da utilizzare per generare il riepilogo domanda
        /// </summary>
        /// <returns></returns>
        protected string GetUrlRiepilogoDomanda()
        {

            var ub = new UrlBuilder();
            var urlDownloadPdf = this.Page.ResolveUrl(ub.Build("~/Reserved/InserimentoIstanza/DownloadRiepilogoDomanda.ashx", qs =>
            {
                qs.Add(new QsSoftware(this.Software));
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsIdDomandaOnline(this.IdDomanda));
                qs.Add("PdfSchedeNf", this.AggiungiSchedeNonFirmateARiepilogoAllegati);
            }));

            var viewerPath = this.ResolveClientUrl("~/js/lib/pdf.js/web/viewer.html?file=" + this.Server.UrlEncode(urlDownloadPdf));

            return viewerPath;
        }

        protected string GetUrlVersioneStampabile()
        {
            var rigaRiepilogo = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

            return this.BuildClientUrl("~/Reserved/InserimentoIstanza/DownloadOggettoCompilabile.ashx", qs =>
            {
                qs.Add("CodiceOggetto", rigaRiepilogo.CodiceOggettoModello);
                qs.Add("IdPresentazione", this.IdDomanda);
                qs.Add("Fmt", "PDF");
                qs.Add("PdfSchedeNf", this.AggiungiSchedeNonFirmateARiepilogoAllegati);
            });
        }


        protected void cmdProcedi_Click(object sender, EventArgs e)
        {
            this.Title = this.TitoloFaseInvio;

            if (this.RiepilogoRichiedeFirma())
            {
                var redirectFmtStr = String.Format("~/Reserved/InserimentoIstanza/InvioIstanza.aspx?{0}&AllegaRiepilogo=True", this.Request.QueryString);

                this.Response.Redirect(redirectFmtStr);

                //MostraVistaUploadDomanda();
            }
            else
            {
                this.InviaDomanda();
            }
        }

        protected bool RiepilogoRichiedeFirma()
        {
            return this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().RichiedeFirmaDigitale;
        }

        /// <summary>
        /// Effettua la trasmisisone della domanda al backoffice
        /// </summary>
        private void InviaDomanda()
        {
            this._allegatiInterventoService.RigeneraRiepilogoDomanda(this.IdDomanda);

            var risultato = this._invioDomandaService.Invia(this.IdDomanda, String.Empty);

            if (risultato.Esito == TipoEsitoInvio.InvioRiuscito || risultato.Esito == TipoEsitoInvio.InvioRiuscitoNoBackend)
            {
                this.Redirect(this.UrlRedirectInvioRiuscito, qs =>
                {
                    qs.Add("Id", risultato.CodiceIstanza);
                    qs.Add("IdPresentazione", this.IdDomanda);
                });
                return;
            }

            // mostro la view vei messaggi e nascondo il paginatore
            this.multiView.ActiveViewIndex = Constants.IdVistaErrore;
            this.lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore(this.IdDomanda);
            this.Master.MostraPaginatoreSteps = false;
        }
    }


}
