using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Spinner;

namespace AreaRiservataCore.Shared
{
    public class ComponentWithSpinner : ComponentBase
    {
        [Inject]
        public SpinnerService _spinnerService { get; set; } = default!;

        // private SpinnerSession? _spinnerSession;


        protected override async Task OnParametersSetAsync()
        {
            await base.OnParametersSetAsync();

            // this._spinnerSession = await this._spinnerService.StartSession();
            // await using var spinner = await this._spinnerService.StartSession();
        }
    }
}
