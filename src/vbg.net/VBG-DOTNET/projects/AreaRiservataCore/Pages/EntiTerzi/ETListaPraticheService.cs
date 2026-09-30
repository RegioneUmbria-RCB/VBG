using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;

namespace AreaRiservataCore.Pages.EntiTerzi
{
    public class ETListaPraticheService
    {
        private string _pageName;
        public string PageName
        {
            get { return this._pageName; }
            set
            {
                if (this._pageName != value)
                {
                    this._pageName = value;
                    this.ClearAll();
                }
            }
        }

        private ETFiltriRicerca? _filtro;

        public ETFiltriRicerca? Filtro
        {
            get { return this._filtro; }
            set
            {
                this._filtro = value;
                this.NotifyStateChanged();
            }
        }

        public bool DataHasChanged { get; set; }

        public void ClearAll()
        {
            this.DataHasChanged = false;

            this._filtro = null;
        }


        public event Action? OnChange;

        private void NotifyStateChanged() { this.DataHasChanged = true; OnChange?.Invoke(); }
    }
}
