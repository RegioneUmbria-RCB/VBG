using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Shared
{
    public partial class Header : BaseClass
    {
        private string? Denominazione;
        private string? Descrizione;
        private string? LogoComuneDataUrl;
        private string? LogoRegioneDataUrl;
        private string? Regione;
        private string? Comune;
        private string? Provincia;
        private MenuModel? MainMenu;
        private string? SottotitoloPagina;

        [Inject]
        IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; } = default!;
        [Inject]
        IHttpClientFactory _httpClientFactory { get; set; } = default!;
        [Inject]
        ISoftwareResolver _softwareResolver { get; set; } = default!;

        [Inject]
        LoghiAreaRiservataService _loghiAreaRiservataService { get; set; } = default!;

        [Inject]
        IMenuService _menuService { get; set; } = default!;

        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            this.MainMenu = this._menuService.LoadMenu();
            this.SottotitoloPagina = this._configurazioneVbgRepository.LeggiConfigurazioneComune(this._softwareResolver.Software).DENOMINAZIONE;

            var conf = this._configurazioneVbgRepository.LeggiConfigurazioneComune(this._softwareResolver.Software);
            this.Denominazione = conf.DENOMINAZIONE;
            this.Descrizione = conf.DESCRIZIONE;
            this.Regione = conf.SCRITTAREGIONE;
            this.Comune = conf.COMUNE;
            this.Provincia = conf.PROVINCIA;

            var logoComune = await this._loghiAreaRiservataService.GetLogoAreaRiservataAsync(async (url) =>
            {
                using (var wc = _httpClientFactory.CreateClient())
                {
                    var response = await wc.GetAsync(url);

                    response.EnsureSuccessStatusCode();

                    var bytes = await response.Content.ReadAsByteArrayAsync();

                    return BinaryFile.FromFileData("logo.png", "image/png", bytes);
                }
            });
            this.LogoComuneDataUrl = logoComune.ToDataUrl();
            var logoRegione = this._loghiAreaRiservataService.GetLogoRegione();
            this.LogoRegioneDataUrl = logoRegione.ToDataUrl();
        }

        private async Task LogOutAsync(string url)
        {
            var uri = new Uri(url);
            //var queryParams = HttpUtility.ParseQueryString(uri?.Query);
            //string redirectUrl = queryParams?.Get("f");
            //string token = queryParams?.Get("token");

            //while (redirectUrl.StartsWith("/"))
            //{
            //    redirectUrl = redirectUrl.Substring(1, redirectUrl.Length - 1);
            //}

            if (uri != null && !string.IsNullOrEmpty(uri.Query))
                await GotoAsync("logout", uri.Query);
            else
                await GotoAsync("logout");
        }
    }
}