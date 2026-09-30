using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.QuickGrid;

namespace AreaRiservataCore.Pages.EntiTerzi
{
    public partial class ListaPratiche
    {
        private static class Constants
        {
            public const int ViewIdRicerca = 0;
            public const int ViewIdLista = 1;
            public const string SessionKeyUltimaRicerca = "et_lista_pratiche.ultimaRicerca";
            public const string QuerystringRestore = "restore";
        }

        public class Elaborata
        {
            public string Value { get; set; }
            public string Text { get; set; }
        }

        public class Model
        {
            public DateTime? FromDate { get; set; }
            public DateTime? ToDate { get; set; }
            public string? NumeroProtocollo { get; set; }
            public string? NumeroPratica { get; set; }
            public ETSoftwareConPratiche? Competenza { get; set; }
            public Elaborata? Elaborata { get; set; }
            public bool ModalitaInserimento { get; set; } = true;
        }

        [Inject]
        public IScrivaniaEntiTerziService _service { get; set; } = default!;

        [Inject]
        private ETListaPraticheService _listaPraticheService { get; set; } = default!;

        private int currentStep = 0;
        private bool showLoading = false;
        private IQueryable<ETPratica>? gridDataSource;
        private Model model = new();

        private List<ETSoftwareConPratiche> CompetenzeDataItems { get; set; }
        private List<Elaborata> ElaborataDataItems { get; set; }

        private readonly PaginationState _pagination = new PaginationState { ItemsPerPage = 10 };

        public override void Dispose()
        {
            base.Dispose();

            this._listaPraticheService.OnChange -= this.StateHasChanged;
        }

        protected override async Task OnInitializedAsync()
        {
            this._listaPraticheService.PageName = new Uri(this._navigationManager.Uri).LocalPath;
            this._listaPraticheService.OnChange += this.StateHasChanged;

            this.DataBindCombo();

            if (this._listaPraticheService.DataHasChanged)
                await this.LoadResultGridAsync();
        }

        private void DataBindCombo()
        {
            var software = this._service.GetListaSoftwareConPratiche(new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value));

            this.CompetenzeDataItems = software.ToList();

            this.ElaborataDataItems = new List<Elaborata>()
            {
                new Elaborata() { Text = "Elaborata", Value = "1"},
                new Elaborata() { Text = "Non elaborata", Value = "0"}
            };
        }

        private void OnValidSubmit()
        {

        }

        private void OnError()
        {

        }

        private async Task LoadResultGridAsync()
        {
            this.showLoading = true;
            this.currentStep = Constants.ViewIdLista;
            this.gridDataSource = this._service.GetPraticheDiCompetenza(new ETCodiceAnagrafe(this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value), this._listaPraticheService.Filtro)?.AsQueryable();

            await this.GotoAsync("entiterzilistapratiche?showResults=true");
            this.showLoading = false;
        }

        private async Task OnBtnFindAsync()
        {
            var filtri = new ETFiltriRicerca
            {
                DallaData = this.model.FromDate == null ? DateTime.MinValue : this.model.FromDate.Value,
                AllaData = this.model.ToDate == null ? DateTime.MaxValue : this.model.ToDate.Value,
                Elaborata = this.model.Elaborata == null ? (bool?)null : this.model.Elaborata.Value == "1",
                NumeroIstanza = this.model.NumeroPratica,
                NumeroProtocollo = this.model.NumeroProtocollo,
                Software = this.model.Competenza == null ? String.Empty : this.model.Competenza.Codice
            };

            this._listaPraticheService.Filtro = filtri;

            await this.LoadResultGridAsync();
        }

        private async Task OnBtnCloseAsync()
        {
            this._listaPraticheService.ClearAll();
            //this._redirectService.RedirectToHomeAreaRiservata();
            await this.GotoAsync("home");
        }

        private async Task OnBtnBackAsync()
        {
            this._listaPraticheService.ClearAll();
            this.model = new();
            this.currentStep = Constants.ViewIdRicerca;
            await this.ScrollToTopAsync();
            await this.GotoAsync("entiterzilistapratiche");
        }
    }
}
