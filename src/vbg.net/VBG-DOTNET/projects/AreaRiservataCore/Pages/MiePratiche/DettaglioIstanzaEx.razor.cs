using AreaRiservataCore.Shared;
using AreaRiservataCore.Utils;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.MiePratiche
{
    public partial class DettaglioIstanzaEx
    {
        [Parameter]
        public string? GoBackUrl { get; set; }

        public string? GoBackUrlQueryString { get; set; }

        [Parameter]
        public string? UuidIstanza { get; set; }

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        [Inject]
        protected IVisuraService _visuraService { get; set; } = default!;

        [Inject]
        protected DownloadDomandaZIPService _downloadDomandaZIPService { get; set; } = default!;

        [Inject]
        protected RiepilogoDomandaDaVisuraService _riepilogoDomandaDaVisuraService { get; set; } = default!;

        [Inject]
        public ISitCartograficoService _sitCartograficoService { get; set; } = default!;
        [Inject]
        protected FileDownloadHelper _fileDownloadHelper { get; set; } = default!;

        protected bool UsaPost { get; set; } = false;
        protected GeneraURLMappaResponse UrlMappa { get; set; } = new GeneraURLMappaResponse();

        protected LivelloAccessoVisura? _livelloAccesso;
        protected Istanze? _istanza;

        private bool _isBtnCloseVisible = true;
        private bool _isBtnSurveryVisible = true;
        private bool _isBtnLoginVisible = true;
        private bool _isBtnSummaryVisible = true;
        private bool _isBtnShowInMapVisible = true;
        private bool _isBtnDownloadZipVisible = true;

        protected override void OnInitialized()
        {
            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>()
            {
                new()
                {
                    Label = "Le mie pratiche",
                    Url = $"{baseUrl}/istanzepresentate",
                },
                new()
                {
                    Label = "Dettagli istanza",
                    Url = $"{baseUrl}/dettaglioistanzaex/{this.UuidIstanza}",
                    Active = true
                }
            };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);

            base.OnInitialized();
        }

        protected async override Task OnParametersSetAsync()
        {

            await base.OnParametersSetAsync();

            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                this.GoBackUrlQueryString = new Uri(this._navigationManager.Uri).Query;

                if (this._istanza?.UUID != this.UuidIstanza)
                {
                    this._istanza = this._visuraService.GetByUuid(this.UuidIstanza, true);
                    this._livelloAccesso = this.VerificaAccesso(this._istanza);

                    this._isBtnSummaryVisible = false;
                    this._isBtnDownloadZipVisible = false;

                    if (this._livelloAccesso == LivelloAccessoVisura.Completo)
                    {
                        this._isBtnSummaryVisible = this._riepilogoDomandaDaVisuraService.PermetteRigenerazioneRiepilogo(Convert.ToInt32(this._istanza.CODICEINTERVENTOPROC));

                        this._isBtnDownloadZipVisible = this._downloadDomandaZIPService.ServizioConfigurato();
                    }

                    this._isBtnLoginVisible = this.TokenAnonimo();
                    this._isBtnCloseVisible = !this.TokenAnonimo();

                    this._isBtnSurveryVisible = false;
                    // this.cmdQuestionario.Visible = livelloAccesso == LivelloAccessoVisura.Completo &&
                    //                                this._questionarioService.CompilazioneQuestionarioAttiva &&
                    //                                !this._questionarioService.QuestionarioCompilato(this.IdIstanza.Value);

                    try
                    {
                        var feats = await this._sitCartograficoService.GetFeaturesAsync();
                        this._isBtnShowInMapVisible = feats.MostraMappaDettaglioIstanza;
                    }
                    catch (Exception ex)
                    {
                        this.Logger.Error(ex.Message);
                        this.MessageContainer.AddWarning("Al momento non è possibile visualizzare la mappa. Riprovare più tardi");
                        this._isBtnShowInMapVisible = false;
                    }
                }
            });
        }

        private string getGoBackUrl()
        {
            return $"dettaglioistanzaex/{this.UuidIstanza}/{this.GoBackUrl}";
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

        private async Task OnBtnCloseAsync()
        {
            if (!string.IsNullOrEmpty(this.GoBackUrl))
                await this.GotoAsync($"{this.GoBackUrl}{this.GoBackUrlQueryString}");
            else
                await this.GotoAsync("istanzepresentate");
        }

        private void OnBtnSurvey() //NOTA: il pulsante è sempre nascosto
        {
            //var url = UrlBuilder.Url("~/reserved/questionario/compila.aspx", qs => {
            //    qs.Add(new QsAliasComune(IdComune));
            //    qs.Add(new QsSoftware(Software));
            //    qs.Add(IdIstanza);
            //});

            //Response.Redirect(url);
        }

        private void OnBtnLogin() //TODO: da sviluppare successivamente
        {
            //var url = UrlBuilder.Url(_parametriUrl.Parametri.VisuraAutenticata, qs =>
            //{
            //    qs.Add(new QsAliasComune(this.IdComune));
            //    qs.Add(new QsSoftware(this.Software));
            //    qs.Add(IdIstanza);
            //});

            //Response.Redirect(url);
        }

        private async Task OnBtnSummaryAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                try
                {
                    var riepilogo = this._riepilogoDomandaDaVisuraService.GeneraRiepilogoDomanda(this.UuidIstanza);
                    await this._fileDownloadHelper.DownloadFileAsync(riepilogo);
                }
                catch (Exception ex)
                {
                    this.Logger.Error(ex.Message);
                    this.MessageContainer.AddError("Si è verificato un errore durante il download. Riprovare più tardi");
                }
            });
        }

        private async Task OnBtnDownloadZipAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                try
                {
                    var zipFile = this._downloadDomandaZIPService.RecuperaZIPDaUUIDDomanda(this.UuidIstanza);
                    await this._fileDownloadHelper.DownloadFileAsync(zipFile);
                }
                catch (Exception ex)
                {
                    this.Logger.Error(ex.Message);
                    this.MessageContainer.AddError("Si è verificato un errore durante il download. Riprovare più tardi");
                }
            });
        }

        private async Task OnBtnShowInMapAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                try
                {
                    await this.RedirectAllaMappaAsync();
                }
                catch (Exception ex)
                {
                    this.Logger.Error(ex.Message);
                    this.MessageContainer.AddError("Si è verificato un errore cercando di aprire la mappa. Riprovare più tardi");
                }
            });
        }

        private async Task RedirectAllaMappaAsync()
        {
            //var baseUri = new Uri(_navigationManager.BaseUri);

            //var scheme = baseUri.Scheme;
            //var host = baseUri.Host;
            //var port = baseUri.IsDefaultPort ? string.Empty : $":{baseUri.Port}";
            //// Estrai i primi 3 segmenti del path come "appName"
            //var segments = new Uri(_navigationManager.Uri).AbsolutePath.Split('/', StringSplitOptions.RemoveEmptyEntries);
            //var appName = "/" + string.Join('/', segments.Take(3));
            //var token = this.UserAuthenticationResult.Token;

            var response = await this._sitCartograficoService.GeneraURLMappaListaPraticheAsync(new GeneraURLMappaListaPraticheRequest
            {
                CallbackUrl = this._navigationManager.Uri,
                //CallbackUrl = $"{scheme}://{host}{port}{appName}/dettaglio-istanza-return/{UuidIstanza}/{GoBackUrl}?1=1&Token={token}&AUTH_TYPE=AUTH_TYPE_LOGIN",
                CancelUrl = "",
                UuidIstanze = new List<String> { this.UuidIstanza }
            });

            if (!String.IsNullOrEmpty(response.Errore))
            {
                this.MessageContainer.AddError(response.Errore);
                return;
            }

            if (!response.UsaPost)
            {
                this.GotoExternalUrl(response.Url);
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
