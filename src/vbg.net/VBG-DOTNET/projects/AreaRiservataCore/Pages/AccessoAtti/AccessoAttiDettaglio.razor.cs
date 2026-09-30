using AreaRiservataCore.Shared;
using AreaRiservataCore.Utils;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg.Zip;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.AccessoAtti
{
    public partial class AccessoAttiDettaglio
    {
        [Parameter]
        public string UuidIstanza { get; set; } = "";

        [Parameter]
        public int IdAccessoAtti { get; set; }

        [Inject]
        public IVbgAccessoAttiService _accessoAttiService { get; set; } = default!;

        [Inject]
        public IZipAccessoAttiService _zipAccessoAttiService { get; set; } = default!;
        [Inject]
        protected FileDownloadHelper _fileDownloadHelper { get; set; } = default!;

        [Inject]
        public IConfigurazione<ParametriAccessoAtti> _configurazione { get; set; } = default!;

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        private int _livelloAccessoDocumenti = 0;
        private bool _permetteDownloadFileZip = false;

        protected override async Task OnInitializedAsync()
        {
            this.MessageContainer.ClearAll();

            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>()
            {
                new()
                {
                    Label = "Accesso agli atti",
                    Url = $"{baseUrl}/accessoattilista",
                },
                new()
                {
                    Label = "Dettagli pratica",
                    Url = $"{baseUrl}/accessoattidettaglio/{this.IdAccessoAtti}/{this.UuidIstanza}",
                    Active = true
                }
            };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);

            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this.DataBind();
            });
        }

        private bool CallbackValidazioneAllegati(IDocumentoIstanzaOggettoDiVerifica doc)
        {
            return doc.IsAllegatoValido(this._livelloAccessoDocumenti);
        }


        private string GetGoBackUrl()
        {
            return $"accessoattidettaglio/{this.IdAccessoAtti}/{this.UuidIstanza}";
        }

        private LivelloAccessoVisura GetLivelloAccessoVisura()
        {
            return LivelloAccessoVisura.DaValoreFlag(
                LivelloAccessoVisura.Constants.DatiGenerali |
                LivelloAccessoVisura.Constants.Schede |
                LivelloAccessoVisura.Constants.Documenti |
                LivelloAccessoVisura.Constants.Endoprocedimenti |
                (this._configurazione.Parametri.MostraDatiMovimenti ? LivelloAccessoVisura.Constants.MovimentiEffettuati : 0)
                );
        }

        private void DataBind()
        {
            // Loggo l'accesso
            var codiceAnagrafe = this._authenticationDataResolver!.DatiAutenticazione?.DatiUtente?.Codiceanagrafe;

            if (codiceAnagrafe is null)
            {
                throw new Exception("Dati autenticazione non inizializzati");
            }

            this._accessoAttiService.LogAccessoPratica(codiceAnagrafe.Value, this.IdAccessoAtti, this.UuidIstanza);

            this._livelloAccessoDocumenti = this._accessoAttiService.GetLivelloAccessoDocumenti(this.IdAccessoAtti, this.UuidIstanza);
            this._permetteDownloadFileZip = this._accessoAttiService.GetCodiciOggettoScaricabiliComeZip(this.IdAccessoAtti, this.UuidIstanza).Any();
        }

        private async Task OnBtnCloseAsync()
        {
            await this.GotoAsync("accessoattilista");
        }

        private async Task OnBtnDownloadDocZipAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                try
                {
                    var zipFile = await this._zipAccessoAttiService.GetZipFileDocumentiAsync(this.IdAccessoAtti, this.UuidIstanza);
                    await this._fileDownloadHelper.DownloadFileAsync(zipFile);
                }
                catch (Exception ex)
                {
                    this.Logger.Error(ex.Message);
                    this.MessageContainer.AddError("Si è verificato un errore durante il download. Riprovare più tardi");
                }
            });
        }
    }
}