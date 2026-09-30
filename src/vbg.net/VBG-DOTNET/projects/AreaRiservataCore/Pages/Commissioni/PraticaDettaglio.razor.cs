using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni;
using Init.SIGePro.Manager.DTO.Commissioni;
using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.Commissioni
{
    public partial class PraticaDettaglio
    {
        [Parameter]
        public int idCommissione { get; set; }

        [Parameter]
        public string idPratica { get; set; }

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        [Inject]
        protected ICommissioniService _commissioniService { get; set; } = default!;

        [Inject]
        public IVotazioniCommissioniService _votazioniCommissioniService { get; set; } = default!;

        protected PraticaCommissioneBreveDto DataSource { get; set; }
        protected IEnumerable<LocalizzazionePraticaCommissioneDto> LocalizzazioniDataSource { get; set; }
        protected VotoPraticaCommissioneDto ParereDataSource { get; set; }
        protected IEnumerable<DocumentoPraticaCommissioneDto> DocumentiDataSource { get; set; }

        private bool isBtnParereVisible { get; set; } = true;

        protected override async Task OnInitializedAsync()
        {
            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>()
            {
                new()
                {
                    Label = "Commissioni e conferenze",
                    Url = $"{baseUrl}/commissionilista",
                },
                new()
                {
                    Label = "Dettagli commissione",
                    Url = $"{baseUrl}/commissionidettaglio/{this.idCommissione}"
                },
                 new()
                {
                    Label = "Dettagli pratica",
                    Url = $"{baseUrl}/praticadettaglio/{this.idCommissione}/{this.idPratica}",
                    Active = true
                }
            };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);

            await this.LoadDataAsync();
        }

        private async Task LoadDataAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                var pratica = this._commissioniService.GetDettaglioPraticaPerUtenteCorrente(this.idCommissione, this.idPratica);

                if (pratica == null)
                {
                    await this.ErroreAccessoAsync();
                    return;
                }

                this.DataSource = pratica.DatiPratica;

                this.LocalizzazioniDataSource = pratica.Localizzazioni;

                // Visualizzazione del div del voto espresso
                // bool parereEspresso = false;

                var parere = this._votazioniCommissioniService.GetVotoUtenteLoggato(this.idCommissione, this.idPratica);

                if (parere != null && parere.Voto != null)
                {
                    this.ParereDataSource = parere.Voto;

                    // parereEspresso = true;
                    this.isBtnParereVisible = false;

                    this.MessageContainer.AddAlert("Hai già espresso un parere per questa pratica");
                }
                else
                {
                    this.isBtnParereVisible = this._votazioniCommissioniService.UtenteLoggatoPuoEsprimereVoto(this.idCommissione, this.idPratica);

                    this.MessageContainer.ClearAlerts();
                }

                var documenti = pratica.Documenti.Istanza
                                    .Union(pratica.Documenti.Endoprocedimenti)
                                    .Union(pratica.Documenti.Movimenti)
                                    .OrderBy(x => x.Categoria)
                                    .ThenBy(x => x.Descrizione);

                this.DocumentiDataSource = documenti;

                this.StateHasChanged();
            });
        }

        protected Func<bool> onVerificaAccesso(int codiceOggetto)
        {
            return () => this._commissioniService.VerificaAccessoAFilePerUtenteCorrente(this.idCommissione, this.idPratica, codiceOggetto);
        }

        private async Task OnBtnCloseAsync()
        {
            await this.GotoAsync($"commissionidettaglio/{this.idCommissione}");
        }

        private async Task OnBtnEsprimiParereAsync()
        {
            await this.GotoAsync($"parerepratica/{this.idCommissione}/{this.idPratica}");
        }
    }
}
