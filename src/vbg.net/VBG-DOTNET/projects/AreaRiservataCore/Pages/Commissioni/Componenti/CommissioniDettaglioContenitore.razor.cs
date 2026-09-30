using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni;
using Init.SIGePro.Manager.DTO.Commissioni;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;
using VBG.BlazorComponentsLibrary.DesignComuni.Timeline;

namespace AreaRiservataCore.Pages.Commissioni.Componenti
{
    public partial class CommissioniDettaglioContenitore
    {
        [Inject]
        protected ICommissioniService _commissioniService { get; set; } = default!;

        [Inject]
        protected IAliasSoftwareResolver _aliasSoftwareResolver { get; set; } = default!;

        [Parameter]
        public int idCommissione { get; set; }

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        protected DettaglioCommissioneDto DataSource { get; set; }
        protected IEnumerable<TimelineItem> ConvocazioniDataSource { get; set; }
        protected IEnumerable<PraticaCommissioneBreveDto> PraticheDataSource { get; set; }
        protected IEnumerable<AllegatoCommissioneDao> DocumentiDataSource { get; set; }

        protected override async Task OnInitializedAsync()
        {
            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this._aliasSoftwareResolver.Software}";
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
                    Url = $"{baseUrl}/commissionidettaglio/{this.idCommissione}",
                    Active = true
                }
            };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);

            await this.LoadDataAsync();
        }

        private async Task LoadDataAsync()
        {
            var commissione = await Task<DettaglioCommissioneDto>.Run(() => this._commissioniService.GetDettaglioCommissionePerUtenteCorrente(this.idCommissione));

            if (commissione == null)
            {
                await this.ErroreAccessoAsync();
                return;
            }

            this.DataSource = commissione;

            var i = 1;
            this.ConvocazioniDataSource = commissione.Convocazioni.Select(x => new TimelineItem
            {
                Title = $"{i++}A Convocazione",
                Description = x.Ora,
                Parts = new DateTimelineItemParts(
                            DateTime.ParseExact(x.Data, "dd/MM/yyyy", null)
                        ),
                BadgeText = x.Id == commissione.Testata.Convocazione?.Id ? "Attiva" : ""
            }).ToList();

            this.PraticheDataSource = commissione.Pratiche;
            this.DocumentiDataSource = commissione.Documenti;
        }
    }
}