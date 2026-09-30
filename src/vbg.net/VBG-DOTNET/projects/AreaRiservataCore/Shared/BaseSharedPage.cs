using log4net;
using Microsoft.AspNetCore.Components;
using Microsoft.JSInterop;

namespace AreaRiservataCore.Shared
{
    public class BaseSharedPage : BaseClass, IDisposable
    {

        [Inject]
        protected IJSRuntime _jsRuntime { get; set; } = default!;


        protected ILog Logger { get; set; } = LogManager.GetLogger(typeof(BaseSharedPage));

        [CascadingParameter]
        public MessageContainer MessageContainer { get; set; } = default!;

        protected override void OnInitialized()
        {
            base.OnInitialized();

            this._navigationManager.LocationChanged += this.NavigationManager_LocationChanged;
        }

        // NOTA: è un event handler, l'unico caso in cui può essere restituito un async void
#pragma warning disable VSTHRD100 // Avoid async void methods
        private async void NavigationManager_LocationChanged(object? sender, Microsoft.AspNetCore.Components.Routing.LocationChangedEventArgs e)
#pragma warning restore VSTHRD100 // Avoid async void methods
        {
            //if (e.Location != this._currentLocation)
            //{
            //    this.errorContainerModel.ClearMessages();
            //    this.messageContainerModel.ClearMessages();
            //}

            try
            {
                await this._spinnerService.ShowSpinnerAsync(async () => await this.ScrollToFragmentAsync());
            }
            catch (TaskCanceledException ex)
            {
                this.Logger.Warn($"Ricevuta eccezione di tipo TaskCanceledException: {ex}");
            }
        }

        private async Task ScrollToFragmentAsync()
        {
            var uri = new Uri(this._navigationManager.Uri, UriKind.Absolute);
            var fragment = uri.Fragment;

            if (fragment.StartsWith('#'))
            {
                var elementId = fragment.Substring(1);
                var index = elementId.IndexOf(":~:", StringComparison.Ordinal);

                if (index > 0)
                {
                    elementId = elementId.Substring(0, index);
                }

                if (!string.IsNullOrEmpty(elementId))
                {
                    if (this._jsRuntime != null)
                        await this._jsRuntime.InvokeVoidAsync("scrollTo_withOffset", elementId, "header-nav-wrapper");
                }
            }
            else
            {
                await this.ScrollToTopAsync();
            }
        }

        protected async Task ScrollToTopAsync()
        {
            await this.ScrollToElementAsync("bodyTop");
        }

        protected async Task ScrollToElementAsync(string selector)
        {
            await this.ScrollToElementAsync(selector, true);
        }

        protected async Task ScrollToElementAsync(string selector, bool selectById)
        {
            if (this._jsRuntime != null)
                await this._jsRuntime.InvokeVoidAsync("scrollTo", selector, null, selectById);
        }

        protected async Task<bool> ConfirmAsync(string message)
        {
            return await this._jsRuntime.InvokeAsync<bool>("confirm", message);
        }

        protected async Task<object> WindowOpenAsync(string url, string name = "", string parameters = "")
        {
            return await this._jsRuntime.InvokeAsync<bool>("window.open", url, name, parameters);
        }

        public virtual void Dispose()
        {
            this._navigationManager.LocationChanged -= this.NavigationManager_LocationChanged;
        }
    }
}
