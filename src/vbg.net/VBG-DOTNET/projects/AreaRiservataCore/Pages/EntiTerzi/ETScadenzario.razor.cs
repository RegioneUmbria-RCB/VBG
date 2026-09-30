using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.SIGePro.Manager.DTO.Scadenzario;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.QuickGrid;

namespace AreaRiservataCore.Pages.EntiTerzi
{
    public partial class ETScadenzario
    {
        [Inject]
        public IScadenzeService _scadenzeService { get; set; } = default!;

        [Inject]
        public IScrivaniaEntiTerziService _etService { get; set; } = default!;

        private IQueryable<ElementoListaScadenzeDto> DataSource { get; set; } = Enumerable.Empty<ElementoListaScadenzeDto>().AsQueryable();

        private readonly PaginationState _pagination = new PaginationState { ItemsPerPage = 10 };

        protected override void OnInitialized()
        {
            base.OnInitialized();

            var codiceAnagrafe = new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

            var listaScadenze = Enumerable.Empty<ElementoListaScadenzeDto>();

            if (this._etService.PuoEffettuareMovimenti(codiceAnagrafe))
            {
                var amministrazione = this._etService.GetDatiAmministrazioneCollegata(codiceAnagrafe);
                listaScadenze = this._scadenzeService.GetListaScadenzeEntiTerzi(amministrazione.PartitaIva);
            }

            this.DataSource = listaScadenze.AsQueryable();
        }
    }
}