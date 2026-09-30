using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.CoreServices.GestioneUrl;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Spinner;

namespace AreaRiservataCore.Shared
{
    public class BaseClass : ComponentBase
    {
        [Inject]
        public NavigationManager _navigationManager { get; set; } = default!;

        [Inject]
        protected IAuthenticationDataResolver _authenticationDataResolver { get; set; } = default!;

        [Inject]
        public IConfigurazione<ParametriAreaRiservata> _config { get; set; } = default!;

        [Inject]
        public IUrlBuilder _urlBuilder { get; set; } = default!;

        [Inject]
        protected SpinnerService _spinnerService { get; set; } = default!;

        [Parameter]
        public string SvgFilePath { get; set; } = "_content/VBG.BlazorComponentsLibrary/lib/bootstrap-italia/svg/sprites.svg";



        public UserAuthenticationResult UserAuthenticationResult
        {
            get { return this._authenticationDataResolver.DatiAutenticazione; }
        }

        protected bool UtenteTester
        {
            get
            {
                if (this._authenticationDataResolver.IsAuthenticated)
                {
                    return this.UserAuthenticationResult.DatiUtente.UtenteTester;
                }

                return false;
            }
        }

        protected string GetNavigationPathTo(params object[] pagePaths)
        {
            if (pagePaths == null || pagePaths.Length == 0)
                return "";

            if (pagePaths.Length == 1 &&
                pagePaths[0] is string singlePath &&
                (string.Equals(singlePath, "home", StringComparison.OrdinalIgnoreCase) ||
                 string.Equals(singlePath, "#", StringComparison.OrdinalIgnoreCase)))
            {
                return this._urlBuilder.Build(this._config.Parametri.PaginaIniziale);
            }

            return this._urlBuilder.Build(pagePaths);
        }

        protected Task GotoAsync(params object[] pagePaths)
        {
            string path = this.GetNavigationPathTo(pagePaths);

            //if (path != this._navigationManager.Uri.Replace(this._navigationManager.BaseUri, ""))
            //    await _spinnerService.ShowAsync();

            this._navigationManager.NavigateTo(path);

            return Task.CompletedTask;
        }

        protected Task GotoAsync(bool forceReload, params object[] pagePaths)
        {
            string path = this.GetNavigationPathTo(pagePaths);

            //if (path != this._navigationManager.Uri.Replace(this._navigationManager.BaseUri, ""))
            //    await _spinnerService.ShowAsync();

            this._navigationManager.NavigateTo(path, forceReload);

            return Task.CompletedTask;
        }

        protected void GotoExternalUrl(string url)
        {
            this._navigationManager.NavigateTo(url, true);
        }
    }
}
