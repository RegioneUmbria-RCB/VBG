using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.AccessoPIN;
using Init.SIGePro.Manager.DTO.Commissioni;
using log4net;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;
using VBG.BlazorComponentsLibrary.DesignComuni.Modals;


namespace AreaRiservataCore.Pages.Commissioni
{
    public partial class CommissioniLista
    {
        public class FormModel
        {
            public string Pin { get; set; }
        }

        [Inject]
        protected ICommissioniService _commissioniService { get; set; } = default!;

        [Inject]
        protected IAccessoPINService _accessoPINService { get; set; } = default!;

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        new private readonly ILog _log = LogManager.GetLogger(typeof(CommissioniLista));

        private BasicModal modalPin { get; set; }
        private FormModel _model;
        private bool _isLoading;

        protected IEnumerable<CommissioneTestataDto> DataSource { get; set; }

        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();
            await this.LoadDataAsync();

            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>()
            {
                new()
                {
                    Label = "Commissioni e conferenze",
                    Url = $"{baseUrl}/commissionilista",
                    Active = true
                }
            };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);
        }

        private async Task LoadDataAsync()
        {
            this._isLoading = true;
            this.DataSource = await Task<IEnumerable<CommissioneTestataDto>>.Run(() => this._commissioniService.GetCommissioniApertePerUtenteCorrente());
            this._isLoading = false;
        }

        public async Task OnBtnCloseAsync()
        {
            await this.GotoAsync("home");
        }

        protected void OnBtnAccediConPin()
        {
            this._model = new();

            this.modalPin.Show();
        }

        protected async Task CheckPinAsync()
        {
            if (string.IsNullOrEmpty(this._model.Pin) || !this._accessoPINService.VerificaValiditaPIN(this._model.Pin))
            {
                this._log.Error($"L'utente {this._authenticationDataResolver.DatiAutenticazione.DatiUtente.ToString()} " +
                                $"(codice anagrafe {this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe}) " +
                                $"ha cercato di accedere ad una commissione con un PIN non valido: {this._model.Pin}");

                this.MessageContainer.AddError("Il pin immesso non è valido o un altro utente è già stato associato alla commissione");
                this._model.Pin = "";
                return;
            }

            try
            {
                this.MessageContainer.ClearErrors();
                var idCommissione = this._accessoPINService.AssociaUtenteCorrenteACommissioneByPIN(this._model.Pin.ToUpper());

                await this.GotoAsync($"commissionidettaglio/{idCommissione}");
            }
            catch (Exception ex)
            {
                var id = Guid.NewGuid().ToString();
                this._log.Error($"Errore durante l'associazione di un utente ad una commissione tramite pin. Riferimento errore => {id}: {ex}");
                var msg = "Si è verificato un problema durante l'accesso tramite pin.<br/> " +
                          "Riprovare tra qualche minuto, se il problema persiste contattare l'assistenza " +
                         $"fornendo il seguente riferimento errore:<br/>" +
                         $"<span class=\"fw-bold\">{id}</span>";

                this.MessageContainer.AddError(msg);
            }
        }
    }
}
