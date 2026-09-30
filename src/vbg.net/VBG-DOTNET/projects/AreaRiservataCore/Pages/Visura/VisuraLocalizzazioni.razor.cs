using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraLocalizzazioni
    {
        public class VisuraLocalizzazioniDataSource
        {
            public IEnumerable<IstanzeStradario> Stradario { get; set; } = Enumerable.Empty<IstanzeStradario>();
            public IEnumerable<IstanzeMappali> Mappali { get; set; } = Enumerable.Empty<IstanzeMappali>();

            public bool HasData => this.Stradario.Any() || this.Mappali.Any();
        }

        [Parameter]
        public VisuraLocalizzazioniDataSource? DataSource { get; set; }

        [Parameter]
        public bool MostraDatiCatastaliEstesi { get; set; } = false;

    }
}
