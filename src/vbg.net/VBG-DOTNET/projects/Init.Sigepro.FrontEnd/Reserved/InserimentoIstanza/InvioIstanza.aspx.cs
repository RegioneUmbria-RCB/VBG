using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.RiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Ninject;
using System;
using System.Configuration;
using System.Linq;

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
        public RiepilogoDomandaAllegatoLegacyService _riepilogoDomandaLegacyService { get; set; }
        [Inject]
        public RiepilogoDomandaAllegatoService _riepilogoDomandaService { get; set; }
        [Inject]
        public GenerazioneRiepilogoDomandaLegacyService _generazioneRiepilogoDomandaService { get; set; }

        [Inject]
        public InvioDomandaAreaRiservataService InvioDomandaService { get; set; }

        [Inject]
        public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        [Inject]
        public IIstanzaStcAdapter _stcAdapter { get; set; }

        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioDomandaStrategy { get; set; }
        [Inject]
        public IRedirectService _redirectService { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadService { get; set; }

        [Inject]
        public ValidazioneSoggettiRiepilogoDomandaService _validazioneSoggettiRiepilogoDomandaService { get; set; }

        #region Parametri letti dal file di workflow
        public bool UsaFirmaCidPin
        {
            get { var o = this.ViewState["UsaFirmaCidPin"]; return o == null ? false : (bool)o; }
            set { this.ViewState["UsaFirmaCidPin"] = value; }
        }

        public bool UsaFirmaGrafometrica
        {
            get { var o = this.ViewState["UsaFirmaGrafometrica"]; return o == null ? false : (bool)o; }
            set { this.ViewState["UsaFirmaGrafometrica"] = value; }
        }

        public bool UsaFirmaOnline
        {
            get { var o = this.ViewState["UsaFirmaOnline"]; return o == null ? true : (bool)o; }
            set { this.ViewState["UsaFirmaOnline"] = value; }
        }

        public string MessaggioConfermaFirmaDispositivoEsterno
        {
            get { var o = this.ViewState["MessaggioConfermaFirmaDispositivoEsterno"]; return o == null ? "Si è sicuri di voler firmare la domanda tramite un dispositivo esterno" : (string)o; }
            set { this.ViewState["MessaggioConfermaFirmaDispositivoEsterno"] = value; }
        }


        public string TitoloFaseInvio
        {
            get { var o = this.ViewState["TitoloFaseInvio"]; return o == null ? "Sottoscrizione e invio dell'istanza" : (string)o; }
            set { this.ViewState["TitoloFaseInvio"] = value; }
        }


        public string SottotitoloFaseInvio
        {
            get { return this.ltrSottotitoloInvio.Text; }
            set { this.ltrSottotitoloInvio.Text = value; }
        }

        public string DescrizioneFaseInvio
        {
            get { return this.ltrDescrizioneFaseInvio.Text; }
            set { this.ltrDescrizioneFaseInvio.Text = value; }

        }

        public string EtichettaFileDaScaricare
        {
            get { return this.ltrNomeFiledaScaricare.Text; }
            set { this.ltrNomeFiledaScaricare.Text = value; }
        }

        public string TitoloGrigliaSottoscrittori
        {
            get { return this.ltrIntestazioneSottoscrittori.Text; }
            set { this.ltrIntestazioneSottoscrittori.Text = value; }
        }

        public string TitoloGrigliaNonSottoscrittori
        {
            get { return this.ltrSoggetiNonSottoscrittori.Text; }
            set { this.ltrSoggetiNonSottoscrittori.Text = value; }
        }

        public bool AggiungiSchedeNonFirmateARiepilogoAllegati
        {
            get { var o = this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"]; return o == null ? true : (bool)o; }
            set { this.ViewState["AggiungiSchedeNonFirmateARiepilogoAllegati"] = value; }
        }

        public string TestoBottoneTrasferisciIstanza
        {
            get { return this.cmdInviaDomanda.Text; }
            set { this.cmdInviaDomanda.Text = value; }
        }

        public bool VerificaFirmaSuRiepilogo
        {
            get { var o = this.ViewState["VerificaFirmaSuRiepilogo"]; return o == null ? true : (bool)o; }
            set
            {
                this.ViewState["VerificaFirmaSuRiepilogo"] = value;

                // per compatibilità con alcune vecchie installazioni
                if (!value)
                {
                    this.RichiediFirmaAutografa = true;
                }
            }
        }

        public bool RichiediFirmaAutografa
        {
            get { var o = this.ViewState["RichiediFirmaAutografa"]; return o == null ? false : (bool)o; }
            set { this.ViewState["RichiediFirmaAutografa"] = value; }
        }


        public string TestoDichiarazione
        {
            get { var o = this.ViewState["TestoDichiarazione"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TestoDichiarazione"] = value; }
        }

        public string TestoCheckDichiarazione
        {
            get { var o = this.ViewState["TestoCheckDichiarazione"]; return o == null ? "Dichiaro di accettare" : (string)o; }
            set { this.ViewState["TestoCheckDichiarazione"] = value; }
        }

        public bool MostraGrigliaSottoscrittori
        {
            get { var o = this.ViewState["MostraGrigliaSottoscrittori"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraGrigliaSottoscrittori"] = value; }
        }

        public bool MostraGrigliaNonSottoscrittori
        {
            get { var o = this.ViewState["MostraGrigliaNonSottoscrittori"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraGrigliaNonSottoscrittori"] = value; }
        }

        public string IntestazioneBottoniFirma
        {
            get { return this.ltrIntestazioneBottoniFirma.Text; }
            set { this.ltrIntestazioneBottoniFirma.Text = value; }
        }



        public string UrlRedirectInvioRiuscito
        {
            get { var o = this.ViewState["UrlRedirectInvioRiuscito"]; return o == null ? "~/Reserved/InserimentoIstanza/CertificatoInvio.aspx" : (string)o; }
            set { this.ViewState["UrlRedirectInvioRiuscito"] = value; }
        }

        #endregion

        protected bool AllegaRiepilogo
        {
            get
            {
                var qs = this.Request.QueryString["AllegaRiepilogo"];

                if (String.IsNullOrEmpty(qs))
                    return false;

                return Boolean.Parse(qs);
            }
        }

        protected bool TestAdattatoreStc
        {
            get
            {
                var qs = this.Request.QueryString["TestAdattatoreStc"];

                if (String.IsNullOrEmpty(qs))
                    return false;

                return Boolean.Parse(qs);
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                if (this.TestAdattatoreStc)
                {
                    var stc = this._stcAdapter.Adatta(this._salvataggioDomandaStrategy.GetById(this.IdDomanda));
                }

                if (this.AllegaRiepilogo)
                {
                    this._riepilogoDomandaService.EliminaOggettoRiepilogoDomanda(this.IdDomanda);

                    if (!this.RichiediFirmaAutografa)
                    {
                        this.AllegaRiepilogoDomanda();
                    }
                }

                this.DataBind();
            }
        }

        private void AllegaRiepilogoDomanda()
        {
            this._riepilogoDomandaLegacyService.RigeneraRiepilogoDomanda(this.IdDomanda);
        }

        public override void DataBind()
        {
            this.Title = this.TitoloFaseInvio;

            // Soggetti che sottoscrivono l'istanza
            this.gvSoggettiFirmatari.DataSource = this.ReadFacade.Domanda.Anagrafiche.GetSoggettiSottoscrittori();
            this.gvSoggettiFirmatari.DataBind();

            // Soggetti non sottoscrittori
            var rows = this.ReadFacade.Domanda.Anagrafiche.GetSoggettiNonSottoscrittori();

            this.pnlSoggettiNonSottoscriventi.Visible = rows.Count() > 0;

            this.gvSoggettiNonSottoscriventi.DataSource = rows;
            this.gvSoggettiNonSottoscriventi.DataBind();

            // Vista dell'allegato
            var allegato = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente;

            if (allegato == null)
            {
                this.MostraVistaUploadFile();
            }
            else
            {
                this.MostraVistaFirmaOInviaFile();
            }
        }

        private void MostraVistaFirmaOInviaFile()
        {
            this.mvInvioIstanza.ActiveViewIndex = Constants.Viste.Firma;

            var allegato = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente;
            var firmatoDigitalmente = allegato.FirmatoDigitalmente || !this.VerificaFirmaSuRiepilogo;

            this.lblErroreRiepilogo.Visible = !firmatoDigitalmente;

            this.cmdFirnaConDispositivoEsterno.Visible = !firmatoDigitalmente;
            this.cmdInviaDomanda.Visible = firmatoDigitalmente;
            this.cmdAllegaAltroFile.Visible = firmatoDigitalmente;

            // Dichiarazione
            this.lblDichiarazione.Text = this.TestoDichiarazione;
            this.chkDichiarazione.Text = this.TestoCheckDichiarazione;
            this.pnlDichiarazione.Visible = firmatoDigitalmente && !String.IsNullOrEmpty(this.TestoDichiarazione);

            if (!String.IsNullOrEmpty(this.MessaggioConfermaFirmaDispositivoEsterno))
            {
                this.cmdFirnaConDispositivoEsterno.OnClientClick = "return confirm('" + this.MessaggioConfermaFirmaDispositivoEsterno.Replace("'", "\\'").Replace("\r", "").Replace("\n", "") + "')";
            }

            var url = this._urlDownloadService.GetUrlDownload(allegato.CodiceOggetto);

            this.hlRiepilogoDomanda.NavigateUrl = url;
            this.hlRiepilogoDomanda.Text = allegato.NomeFile;
        }

        /// <summary>
        /// Ottiene l'url da utilizzare per generare il riepilogo domanda
        /// </summary>
        /// <returns></returns>
        protected string GetUrlRiepilogoDomanda()
        {
            var url = UrlBuilder.Url("~/Reserved/InserimentoIstanza/DownloadRiepilogoDomanda.ashx", x =>
            {
                x.Add(new QsSoftware(this.Software));
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsIdDomandaOnline(this.IdDomanda));
                x.Add("PdfSchedeNf", this.AggiungiSchedeNonFirmateARiepilogoAllegati);
                x.Add("AsAttachment", "1");
            });

            return this.Page.ResolveUrl(url);
        }

        private void MostraVistaUploadFile()
        {
            this.mvInvioIstanza.ActiveViewIndex = Constants.Viste.AllegaFile;

            var rigaRiepilogo = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

            // Soggetti sottoscrittori
            this.hlModelloDomanda.NavigateUrl = this.GetUrlRiepilogoDomanda();
        }

        protected void cmdUploadDomanda_Click(Object sender, EventArgs e)
        {
            try
            {
                var file = new WebFormsBinaryFile(this.fuRiepilogo, this._validPostedFileSpecification);

                var esitoValidazioneSoggetti = this._validazioneSoggettiRiepilogoDomandaService.ValidaRiepilogoDomanda(this.IdDomanda, file);

                if (!esitoValidazioneSoggetti.Result)
                {
                    foreach (var errorMsg in esitoValidazioneSoggetti.ErroriValidazione)
                    {
                        this.Errori.Add(errorMsg);
                    }

                    return;
                }

                this._riepilogoDomandaService.SalvaOggettoRiepilogo(this.IdDomanda, file);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void cmdFirma_Click(object sender, EventArgs e)
        {
            var codiceOggetto = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente.CodiceOggetto;

            this._redirectService.ToFirmaDigitale(this.IdDomanda, codiceOggetto);
        }



        protected void cmdFirnaConDispositivoEsterno_Click(object sender, EventArgs e)
        {
            this._riepilogoDomandaService.EliminaOggettoRiepilogoDomanda(this.IdDomanda);

            this.DataBind();
        }

        protected void cmdInviaDomanda_Click(object sender, EventArgs e)
        {
            var codiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;
            var idIntervento = this.ReadFacade.Domanda.AltriDati.Intervento.Codice;
            var esitoVerifica = this.ReadFacade.Interventi.VerificaAccessoIntervento(idIntervento, codiceComune);

            if (!esitoVerifica.Is(TipoAccessibilitaIntervento.Accessibile))
            {
                this.Errori.Add($"{esitoVerifica.MessaggioErrore}. Selezionare un nuovo intervento.");

                return;
            }

            if (String.IsNullOrEmpty(ConfigurationManager.AppSettings["invio-istanza-finto"]))
            {
                var risultato = this.InvioDomandaService.Invia(this.IdDomanda, String.Empty);

                if (risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito || risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscitoNoBackend)
                {
                    this.Redirect(this.UrlRedirectInvioRiuscito, qs =>
                    {
                        qs.Add("Id", risultato.CodiceIstanza);
                        qs.Add("IdPresentazione", this.IdDomanda);
                    });
                }
            }
            // mostro la view vei messaggi e nascondo il paginatore
            this.mvInvioIstanza.ActiveViewIndex = Constants.Viste.Errore;
            this.Master.MostraPaginatoreSteps = false;
            this.lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore(this.IdDomanda);
        }
    }
}