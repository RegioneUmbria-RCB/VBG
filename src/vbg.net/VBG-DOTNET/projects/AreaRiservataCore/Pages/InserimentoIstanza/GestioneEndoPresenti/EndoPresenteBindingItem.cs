using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneEndoPresenti
{
    public class EndoPresenteBindingItem
    {
        private DropDownItem _selectedTipoTitoloItem;

        public int CodiceInventario { get; set; }
        public string Descrizione { get; set; } = "";
        public bool Presente { get; set; }
        public bool TipoTitoloObbligatorio { get; set; }
        public IEnumerable<DropDownItem>? TipiTitolo { get; init; }

        public DropDownItem SelectedTipoTitoloItem
        {
            get => this._selectedTipoTitoloItem;
            set
            {
                this._selectedTipoTitoloItem = value;

                IdTipoTitoloSelezionato = int.TryParse(this._selectedTipoTitoloItem?.Value, out var result) ? result : null;
            }
        }

        //[Required(ErrorMessage = "Campo obbligatorio")]
        public int? IdTipoTitoloSelezionato { get; private set; }
        //[Required(ErrorMessage = "Campo obbligatorio")] 
        public string NumeroAtto { get; set; } = "";
        //[Required(ErrorMessage = "Campo obbligatorio")]
        public DateTime? DataAtto { get; set; } = null;
        //[Required(ErrorMessage = "Campo obbligatorio")]
        public string RilasciatoDa { get; set; } = "";

        public int? CodiceOggetto { get; set; } = null;
        public string Note { get; set; } = "";
        public string StileCss { get; set; } = "";

        public TipoTitoloFlags? SelectedTipoTitoloItemFlags
        {
            get
            {
                if (SelectedTipoTitoloItem is null)
                    return null;

                return SelectedTipoTitoloItem.ToTipoTitoloFlags();
            }
        }

        public bool IsValid
        {
            get
            {
                if (!this.Presente)
                {
                    return true;
                }

                if (this.Presente && !this.IdTipoTitoloSelezionato.HasValue)
                {
                    return false;
                }

                var flags = this.SelectedTipoTitoloItemFlags;
                if (flags.Numero && String.IsNullOrEmpty(this.NumeroAtto))
                {
                    return false;
                }

                if (flags.Data && !this.DataAtto.HasValue)
                {
                    return false;
                }

                if (flags.RilasciatoDa && String.IsNullOrEmpty(this.RilasciatoDa))
                {
                    return false;
                }

                if (flags.AllegatoObbligatorio && !this.CodiceOggetto.HasValue)
                {
                    return false;
                }

                return true;

            }
        }

        public EndoPresenteBindingItem(IEnumerable<DropDownItem> tipoTitoliSupportati, int? tipoTitoloSelezionato)
        {
            this.TipiTitolo = tipoTitoliSupportati;

            if (tipoTitoloSelezionato is not null)
                this.SelectedTipoTitoloItem = tipoTitoliSupportati.FirstOrDefault(v => v.Value == tipoTitoloSelezionato.ToString());
        }
    }
}
