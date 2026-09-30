using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.CoreServices.GestioneTitoloPagina;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages
{
    public class BasePage : BaseSharedPage
    {

        [Inject]
        public VbgPageTitleService? _pageTitleService { get; set; } = default!;

        [Parameter]
        public string IdComune { get; set; }

        [Parameter]
        public string Software { get; set; }

    }

}
