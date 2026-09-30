using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.AccessoAtti
{
    public partial class AccessoAttiList
    {
        [Inject]
        public IVbgAccessoAttiService _service { get; set; } = default!;

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        protected Dictionary<int, BindingItem> DataSource { get; set; } = new Dictionary<int, BindingItem>();
        private bool _isLoading;

        public class BindingItem
        {
            public int Key { get; set; }
            public string Descrizione { get; set; } = "";
            public List<PraticaAccessoAtti> Pratiche { get; set; } = new List<PraticaAccessoAtti>();
        }

        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>()
            {
                 new()
                {
                    Label = "Accesso agli atti",
                    Url = $"{baseUrl}/accessoattilista",
                    Active = true
                }
            };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);


            this.LoadData();
        }

        private void LoadData()
        {
            this._isLoading = true;
            var listaPratiche = this._service.GetListaPratiche(this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value);
            
            if (!listaPratiche.Any())
            {
                this._isLoading = false;
            }
            else
            {
                foreach (var pratica in listaPratiche)
                {
                    if (!this.DataSource.TryGetValue(pratica.IdAccessoAtti, out BindingItem? value))
                    {
                        value = new BindingItem
                        {
                            Key = pratica.IdAccessoAtti,
                            Descrizione = $"{pratica.CodiceIstanzaAccessoAtti} del {pratica.DataIstanzaAccessoAtti?.ToString("dd/MM/yyyy")}  - {pratica.DescrizioneAccessoAtti}"
                        };

                        this.DataSource.Add(pratica.IdAccessoAtti, value);
                    }

                    value.Pratiche.Add(pratica);
                    this._isLoading = false;
                }
            }            
        }

        private EventCallback UrlAccessoPratica(int idAccessoAtti, string uuidIstanza)
        {
            return EventCallback.Factory.Create(this, () => this.GotoAsync("accessoattidettaglio", idAccessoAtti, uuidIstanza));
        }

        public async Task OnBtnCloseAsync()
        {
            await this.GotoAsync("home");
        }
    }
}
