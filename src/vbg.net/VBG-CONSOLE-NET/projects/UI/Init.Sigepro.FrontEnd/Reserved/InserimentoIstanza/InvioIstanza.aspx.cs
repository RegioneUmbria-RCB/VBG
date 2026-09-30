using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
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
        public AllegatiInterventoService AllegatiInterventoService { get; set; }
        [Inject]
        public IInterventiRepository _interventiRepository { get; set; }

        [Inject]
        public InvioDomandaService InvioDomandaService { get; set; }

        [Inject]
        public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        [Inject]
        public IIstanzaStcAdapter _stcAdapter { get; set; }

        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioDomandaStrategy { get; set; }

        [Inject]
        public RedirectService _redirectService { get; set; }

        [Inject]
        public IConfigurazione<ParametriStcConsole> _configurazioneStc { get; set; }

        [Inject]
        public IConfigurazione<ParametriARRedirect> _configurazioneRedirect { get; set; }



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

        public string DescrizioneFaseInvioBackendEntiTerzi
        {
            get { var o = this.ViewState["DescrizioneFaseInvioBackendEntiTerzi"]; return o == null ? this.DescrizioneFaseInvio : (string)o; }
            set { this.ViewState["DescrizioneFaseInvioBackendEntiTerzi"] = value; }
        }

        public string IstruzioniInvio
        {
            get => this.ltrIstruzioniInvio.Text;
            set => this.ltrIstruzioniInvio.Text = value;
        }

        public string IstruzioniInvioEntiTerzi
        {
            get { var o = this.ViewState["IstruzioniInvioEntiTerzi"]; return o == null ? this.IstruzioniInvio : (string)o; }
            set { this.ViewState["IstruzioniInvioEntiTerzi"] = value; }
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


        public string TestoBottoneTrasferisciIstanzaEntiTerzi
        {
            get { var o = this.ViewState["TestoBottoneTrasferisciIstanzaEntiTerzi"]; return o == null ? this.TestoBottoneTrasferisciIstanza : (string)o; }
            set { this.ViewState["TestoBottoneTrasferisciIstanzaEntiTerzi"] = value; }
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
                    this.AllegatiInterventoService.EliminaOggettoRiepilogoDomanda(this.IdDomanda);

                    if (!this.RichiediFirmaAutografa)
                    {
                        this.AllegaRiepilogoDomanda();
                    }
                }

                this.ImpostaTesti();

                this.DataBind();
            }
        }

        private void ImpostaTesti()
        {
            this.Title = this.TitoloFaseInvio;

            if (this.InvioDomandaService.InviaABackendEntiTerzi())
            {
                this.DescrizioneFaseInvio = this.DescrizioneFaseInvioBackendEntiTerzi;
                this.TestoBottoneTrasferisciIstanza = this.TestoBottoneTrasferisciIstanzaEntiTerzi;
                this.IstruzioniInvio = this.IstruzioniInvioEntiTerzi;
            }
        }

        private void AllegaRiepilogoDomanda()
        {
            this.AllegatiInterventoService.RigeneraRiepilogoDomanda(this.IdDomanda);
        }

        public override void DataBind()
        {
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

            var url = UrlBuilder.Url("~/Reserved/MostraOggettoFo.ashx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(new QsCodiceOggetto(allegato.CodiceOggetto));
                x.Add(new QsIdDomandaOnline(this.IdDomanda));
            });

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
                var file = new BinaryFile(this.fuRiepilogo, this._validPostedFileSpecification);
                this.AllegatiInterventoService.SalvaOggettoRiepilogo(this.IdDomanda, file);

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

        protected void cmdFirmaCidPin_Click(object sender, EventArgs e)
        {
            var codiceOggetto = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente.CodiceOggetto;

            this._redirectService.ToFirmaCidPin(this.IdDomanda, codiceOggetto);
        }


        protected void cmdFirmaGrafometrica_Click(object sender, EventArgs e)
        {
            var codiceOggetto = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda().AllegatoDellUtente.CodiceOggetto;

            this._redirectService.ToFirmaGrafometrica(this.IdDomanda, codiceOggetto);
        }


        protected void cmdFirnaConDispositivoEsterno_Click(object sender, EventArgs e)
        {
            this.AllegatiInterventoService.EliminaOggettoRiepilogoDomanda(this.IdDomanda);

            this.DataBind();
        }

        protected void cmdInviaDomanda_Click(object sender, EventArgs e)
        {
            var idIntervento = this.ReadFacade.Domanda.AltriDati.Intervento.Codice;
            var livelloAutenticazione = this.UserAuthenticationResult.LivelloAutenticazione;
            var utenteTester = this.UserAuthenticationResult.DatiUtente.UtenteTester;
            var codiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;

            var esitoVerifica = this._interventiRepository.VerificaAccessoIntervento(idIntervento, livelloAutenticazione, utenteTester, codiceComune);

            if (!esitoVerifica.Is(TipoAccessibilitaIntervento.Accessibile))
            {
                this.Errori.Add($"{esitoVerifica.MessaggioErrore}. Selezionare un nuovo intervento.");

                return;
            }

            if (String.IsNullOrEmpty(ConfigurationManager.AppSettings["invio-istanza-finto"]))
            {
                var risultato = this.InvioDomandaService.Invia(this.IdDomanda, String.Empty);

                if (risultato.Esito == TipoEsitoInvio.InvioRiuscito || risultato.Esito == TipoEsitoInvio.InvioRiuscitoNoBackend)
                {

                    if (this.InvioDomandaService.InviaABackendEntiTerzi())
                    {
                        this.Redirect(this._configurazioneRedirect.Parametri.UrlPaginaVisuraIstanza, qs =>
                        {
                            qs.Add(new QsAliasComune(this._configurazioneStc.Parametri.SportelloDestinatario.IdEnte));
                            qs.Add(new QsSoftware(this.Software));
                            qs.Add(new QsUuidIstanza(risultato.UuId));
                        }, false);
                    }
                    else
                    {
                        this.Redirect(this.UrlRedirectInvioRiuscito, qs =>
                        {
                            qs.Add("Id", risultato.CodiceIstanza);
                            qs.Add("IdPresentazione", this.IdDomanda);
                        });
                    }
                }
            }
            // mostro la view vei messaggi e nascondo il paginatore
            this.mvInvioIstanza.ActiveViewIndex = Constants.Viste.Errore;
            this.Master.MostraPaginatoreSteps = false;
            this.lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore(this.IdDomanda);
        }
    }
}