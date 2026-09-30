using AreaRiservataCore.Shared;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.ModuliFvg
{
    public class FVGComponentBase : ComponentBase
    {
        [Parameter]
        public string Alias { get; set; } = default!;

        [Parameter]
        public string Software { get; set; } = default!;
        [Parameter]
        public long IdDomandaFEG { get; set; }

        [Parameter]
        public string IdQuadro { get; set; } = default!;
        [CascadingParameter]
        public MessageContainer MessageContainer { get; set; } = default!;
    }
}
