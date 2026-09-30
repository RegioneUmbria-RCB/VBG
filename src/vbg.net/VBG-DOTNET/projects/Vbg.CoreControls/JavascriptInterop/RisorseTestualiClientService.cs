using Microsoft.AspNetCore.Components;
using Microsoft.JSInterop;

namespace Vbg.CoreControls.JavascriptInterop
{
    internal class RisorseTestualiClientService : IAsyncDisposable
    {

        private readonly Lazy<Task<IJSObjectReference>> moduleTask;

        public RisorseTestualiClientService(IJSRuntime jsRuntime)
        {
            this.moduleTask = new(() => jsRuntime.InvokeAsync<IJSObjectReference>(
                "import", "./_content/Vbg.CoreControls/js/RisorseTestualiInterop.js").AsTask());
        }

        public async ValueTask SetInnerHtml(ElementReference elementReference, string nuovoTesto)
        {
            var module = await this.moduleTask.Value;
            await module.InvokeVoidAsync("setInnerHtml", elementReference, nuovoTesto);
        }

        public async Task<string> GetInnerHtml(ElementReference elementReference)
        {
            var module = await this.moduleTask.Value;
            return await module.InvokeAsync<string>("getInnerHtml", elementReference);
        }

        public async ValueTask DisposeAsync()
        {
            if (this.moduleTask.IsValueCreated)
            {
                try
                {
                    var module = await this.moduleTask.Value;
                    await module.DisposeAsync();
                }
                catch (JSDisconnectedException ex)
                {
                    // Ignore
                }
                
            }
        }

    }
}
