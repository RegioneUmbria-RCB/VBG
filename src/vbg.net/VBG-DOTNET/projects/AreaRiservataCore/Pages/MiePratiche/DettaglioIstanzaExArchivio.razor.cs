using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.MiePratiche
{
    public partial class DettaglioIstanzaExArchivio
    {
        [Parameter]
        public string GoBackUrl { get; set; }

        public string GoBackUrlQueryString { get; set; }

        [Parameter]
        public string UuidIstanza { get; set; }

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        protected LivelloAccessoVisura _livelloAccesso = LivelloAccessoVisura.AccessoAnonimo;

        protected override async Task OnInitializedAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
                var breadcrumbItems = new List<BreadcrumbsItem>()
                {
                    new()
                    {
                        Label = "Archivio pratiche",
                        Url = $"{baseUrl}/archiviopratiche"
                    },
                    new()
                    {
                        Label = "Dettagli istanza",
                        Url = $"{baseUrl}/dettaglioistanzaexarchivio",
                        Active = true
                    }
                };

                this.Layout.SetBreadcrumbItems(breadcrumbItems);

                this.GoBackUrlQueryString = new Uri(this._navigationManager.Uri).Query;

            });
        }

        private async Task OnBtnCloseAsync()
        {
            if (!string.IsNullOrEmpty(this.GoBackUrl))
                await this.GotoAsync($"{this.GoBackUrl}{this.GoBackUrlQueryString}");
        }
    }
}
