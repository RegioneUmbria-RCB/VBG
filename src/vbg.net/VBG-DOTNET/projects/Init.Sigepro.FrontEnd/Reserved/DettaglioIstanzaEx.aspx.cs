using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class DettaglioIstanzaEx : ReservedBasePage
    {
        [Inject]
        protected RiepilogoDomandaDaVisuraService _riepilogoDomandaDaVisuraService { get; set; }
        [Inject]
        protected IVisuraService _visuraService { get; set; }
        [Inject]
        protected IConfigurazione<ParametriUrlAreaRiservata> _parametriUrl { get; set; }
        [Inject]
        protected DownloadDomandaZIPService _downloadDomandaZIPService { get; set; }
        [Inject]
        protected ILocalizzazioniSICSyncService _localizzazioniSICService { get; set; }

        protected bool UsaPost { get; set; } = false;
        protected GeneraURLMappaResponse UrlMappa { get; set; } = new GeneraURLMappaResponse();

        protected QsUuidIstanza IdIstanza
        {
            get { return new QsUuidIstanza(this.Request.QueryString); }
        }

        protected QsReturnTo ReturnTo
        {
            get
            {
                return new QsReturnTo(this.Request.QueryString);
            }
        }

        protected string ReturnToArgs
        {
            get
            {
                return this.Request.QueryString["ReturnToArgs"];
            }
        }

        protected bool FocusSuMovimenti { get => !String.IsNullOrEmpty(this.IdMovimentoFocus); }
        protected string IdMovimentoFocus { get => this.Request.QueryString["movimento"]?.ToString() ?? ""; }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.VisuraExCtrl1.ScadenzaSelezionata += new VisuraExCtrl.ScadenzaSelezionataDelegate(this.visuraCtrl_ScadenzaSelezionata);

            if (!this.IsPostBack)
            {
                var istanza = this._visuraService.GetByUuid(this.IdIstanza.Value, true);

                var livelloAccesso = this.VerificaAccesso(istanza);

                this.VisuraExCtrl1.LivelloAccesso = livelloAccesso;
                this.VisuraExCtrl1.EffettuaVisuraIstanza(istanza);

                this.cmdGeneraRiepilogo.Visible = false;
                this.cmdScaricaZIP.Visible = false;

                if (livelloAccesso == LivelloAccessoVisura.Completo)
                {
                    this.cmdGeneraRiepilogo.Visible = this._riepilogoDomandaDaVisuraService.PermetteRigenerazioneRiepilogo(Convert.ToInt32(istanza.CODICEINTERVENTOPROC));

                    this.cmdScaricaZIP.Visible = this._downloadDomandaZIPService.ServizioConfigurato();
                }

                this.cmdAccedi.Visible = this.TokenAnonimo();
                this.cmdClose.Visible = !this.TokenAnonimo();

                this.cmdQuestionario.Visible = false;
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            var feats = this._localizzazioniSICService.GetFeatures();

            this.cmdMostraInMappa.Visible = feats.MostraMappaDettaglioIstanza;
        }

        private LivelloAccessoVisura VerificaAccesso(Istanze istanza)
        {
            if (this.TokenAnonimo())
            {
                return LivelloAccessoVisura.AccessoAnonimo;
            }

            return istanza.GetLivelloAccesso(this.UserAuthenticationResult.DatiUtente.Codicefiscale);
        }

        private bool TokenAnonimo()
        {
            return this.UserAuthenticationResult.LivelloAutenticazione == LivelloAutenticazioneEnum.Anonimo;
        }

        private void visuraCtrl_ScadenzaSelezionata(object sender, string idScadenza)
        {
            this.Redirect("~/Reserved/Gestionemovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
        }

        protected void cmdClose_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/Reserved/IstanzePresentate.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
            });

            if (this.ReturnTo.HasValue)
            {
                url = this.ReturnTo.Value;
            }

            this.Response.Redirect(url);
        }

        protected void cmdGeneraRiepilogo_Click(object sender, EventArgs e)
        {
            var riepilogo = this._riepilogoDomandaDaVisuraService.GeneraRiepilogoDomanda(this.IdIstanza.Value);

            this.Response.Clear();
            this.Response.ContentType = riepilogo.MimeType;
            this.Response.AddHeader("content-disposition", $"attachment;filename=\"{riepilogo.FileName}\"");
            this.Response.BinaryWrite(riepilogo.FileContent);
            this.Response.End();
        }

        protected void cmdAccedi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url(this._parametriUrl.Parametri.VisuraAutenticata, qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add(this.IdIstanza);
            });

            this.Response.Redirect(url);
        }

        protected void cmdScaricaZIP_Click(object sender, EventArgs e)
        {
            var zipFile = this._downloadDomandaZIPService.RecuperaZIPDaUUIDDomanda(this.IdIstanza.Value);

            this.Response.Clear();
            this.Response.ContentType = zipFile.MimeType;
            this.Response.AddHeader("content-disposition", $"attachment;filename=\"{zipFile.FileName}\"");
            this.Response.BinaryWrite(zipFile.FileContent);
            this.Response.End();
        }

        protected void cmdQuestionario_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/questionario/compila.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add(this.IdIstanza);
            });

            this.Response.Redirect(url);
        }

        protected void cmdMostraInMappa_Click(object sender, EventArgs e)
        {
            this.RedirectAllaMappa();
        }

        private void RedirectAllaMappa()
        {
            var host = this.Request.Url.Host;
            var port = this.Request.Url.Port;
            var scheme = this.Request.Url.Scheme;
            var appName = this.Request.ApplicationPath;
            var token = this.UserAuthenticationResult.Token;
            var idComune = this._aliasSoftwareResolver.AliasComune;
            var software = this._aliasSoftwareResolver.Software;
            var uuidPratica = this.IdIstanza.Value;
            var returnToBase = $"{scheme}://{host}:{port}{appName}/sit-return-dettaglio-pratica/{token}/{idComune}/{software}/{uuidPratica}";

            var response = this._localizzazioniSICService.GeneraURLMappaListaPratiche(new GeneraURLMappaListaPraticheRequest
            {
                CallbackUrl = UrlBuilder.Url(returnToBase, qs => qs.Add(this.ReturnTo)),
                CancelUrl = "",
                UuidIstanze = new List<String> { uuidPratica },
            });

            if (!String.IsNullOrEmpty(response.Errore))
            {
                this.Errori.Add(response.Errore);
                return;
            }

            if (!response.UsaPost)
            {
                this.Response.Redirect(response.Url);
                return;
            }

            this.UsaPost = true;
            this.UrlMappa = response;
            //imposta usaPost = true
            //imposta i parametri ( magari in una struttura JSON )
            //imposta la url
            //conviene salvare l'intera response?

        }
    }
}
