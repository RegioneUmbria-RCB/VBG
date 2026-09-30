using AreaRiservataCore.Pages.Scadenze.Componenti;
using AreaRiservataCore.Shared;
using AreaRiservataCore.Shared.enums;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.SIGePro.Manager.DTO.Scadenzario;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.Scadenze
{
    public partial class Scadenzario
    {
        [Inject]
        public IScadenzeService _scadenzeService { get; set; } = default!;

        [Inject]
        protected IIdMovimentoResolver _idMovimentoResolver { get; set; } = default!;

        [Parameter]
        public int? Pagina { get; set; } = 1;

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        private IEnumerable<ElementoListaScadenzeDto> gridDataSource { get; set; } = default!;

        private IEnumerable<ElementoListaScadenzeDto> listaScadenzeFiltrata { get; set; } = default!;

        private bool _isLoading = true;
        const int _elementiPerPagina = 10;
        private int _totalePagine;
        private List<string> _listaRichiedenti = new List<string>();
        private List<string> _listaStati = new List<string>();

        private IEnumerable<ElementoListaScadenzeDto> _scadenzePaginate { get; set; } = default!;

        protected override void OnParametersSet()
        {
            Pagina ??= 1;
            base.OnParametersSet();
        }

        protected override void OnInitialized()
        {
            var baseUrl = $"{_navigationManager.BaseUri}{_authenticationDataResolver.DatiAutenticazione.Alias}/{Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>()
            {
                new BreadcrumbsItem()
                {
                    Label = "Le mie scadenze",
                    Url = $"{baseUrl}/scadenzario",
                    Active = true
                }
            };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);

            base.OnInitialized();
        }

        protected override async Task OnAfterRenderAsync(bool firstRender)
        {
            //await _spinnerService.ShowSpinnerAsync(async () =>
            //{
            if (firstRender)
            {
                _isLoading = true;
                this.gridDataSource = this._scadenzeService.GetListaScadenzeByCodiceFiscale(this.Software, this.UserAuthenticationResult.DatiUtente.Codicefiscale);
                listaScadenzeFiltrata = this.gridDataSource;
                GetListaScadenzePaginata(Pagina.Value);
                GetListaRichiedenti();
                GetListaStati();
                GetTotalePagine();
                _isLoading = false;
                this.StateHasChanged();
            }


            await base.OnAfterRenderAsync(firstRender);
            //});
        }

        //private async Task GoToEffettuaMovimento(int idMovimento)
        //{
        //    _idMovimentoResolver.SetIdMovimento(idMovimento);
        //    await Goto($"effettuamovimento/{idMovimento}/0/scadenzario");
        //}

        private void GetListaScadenzePaginata(int page)
        {
            Pagina = page;
            _scadenzePaginate = listaScadenzeFiltrata.Skip((Pagina.Value - 1) * _elementiPerPagina).Take(_elementiPerPagina);
            GetTotalePagine();
            StateHasChanged();
        }

        private void OrdinaListaScadenze(OrderByDate ordine)
        {
            if (ordine == OrderByDate.DataAscendente)
            {
                listaScadenzeFiltrata = listaScadenzeFiltrata.OrderBy(x => x.DataScadenza);
            }
            else
            {
                listaScadenzeFiltrata = listaScadenzeFiltrata.OrderByDescending(x => x.DataScadenza);
            }

            GetListaScadenzePaginata(1);
        }

        private void FiltraScadenze(FiltroScadenze filtri)
        {
            listaScadenzeFiltrata = gridDataSource;

            if (!string.IsNullOrEmpty(filtri.DatiRichiedente))
            {
                listaScadenzeFiltrata = listaScadenzeFiltrata.Where(x => x.DatiRichiedente.Contains(filtri.DatiRichiedente));
            }

            if (!string.IsNullOrEmpty(filtri.DescrMovimentoDaFare))
            {
                listaScadenzeFiltrata = listaScadenzeFiltrata.Where(x => x.DescrMovimentoDaFare.ToLower().Contains(filtri.DescrMovimentoDaFare.ToLower()));
            }

            if (!string.IsNullOrEmpty(filtri.DescrStatoIstanza))
            {
                listaScadenzeFiltrata = listaScadenzeFiltrata.Where(x => x.DescrStatoIstanza.Contains(filtri.DescrStatoIstanza));
            }

            if (filtri.DataInizio.HasValue)
            {
                listaScadenzeFiltrata = listaScadenzeFiltrata.Where(x => x.DataScadenza >= filtri.DataInizio);
            }

            if (filtri.DataFine.HasValue)
            {
                listaScadenzeFiltrata = listaScadenzeFiltrata.Where(x => x.DataScadenza <= filtri.DataFine);
            }

            GetListaScadenzePaginata(1);
        }

        private void GetListaRichiedenti()
        {
            _listaRichiedenti = gridDataSource.Select(x => x.DatiRichiedente).Distinct().ToList();
        }

        private void GetListaStati()
        {
            _listaStati = gridDataSource.Select(x => x.DescrStatoIstanza).Distinct().ToList();
        }

        private void GetTotalePagine()
        {
            _totalePagine = listaScadenzeFiltrata.Count() % _elementiPerPagina > 0 ? (listaScadenzeFiltrata.Count() / _elementiPerPagina) + 1 : listaScadenzeFiltrata.Count() / _elementiPerPagina;
        }

    }

}
