namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneEndo
{


    public class SubendoBindingItem : IEndoBindingItem
    {
        public int Id { get; init; }
        public int? IdPadre => this.Endo.Id;
        public EndoBindingItem Endo { get; init; }
        public string Descrizione { get; init; } = "";

        public bool Richiesto { get; init; } = false;
        public bool Selezionato
        {
            get => this._selezionato;
            set
            {
                var changed = this._selezionato != value;
                this._selezionato = value;

                if (changed && value)
                {
                    this.Endo.Selezionato = true;
                }

                if (changed && !value && this.Richiesto)
                {
                    this.Endo.Selezionato = false;
                }

            }
        }

        private bool _selezionato = false;

        public SubendoBindingItem(EndoBindingItem endo)
        {
            this.Endo = endo;
        }



    }
    public class EndoBindingItem : IEndoBindingItem
    {
        public int Id { get; init; }
        public int? IdPadre => (int?)null;
        public string Descrizione { get; init; } = "";

        public bool Selezionato
        {
            get => this._selezionato;
            set
            {
                var changed = this._selezionato != value;
                this._selezionato = value;

                if (changed)
                {
                    if (value)
                    {
                        this.SubEndo.Where(x => x.Richiesto).ToList().ForEach(x => x.Selezionato = true);
                    }
                    else
                    {
                        this.SubEndo.ToList().ForEach(x => x.Selezionato = false);
                    }
                }
            }
        }

        private bool _selezionato = false;

        public IEnumerable<SubendoBindingItem> SubEndo { get; set; } = Enumerable.Empty<SubendoBindingItem>();
    }

    public interface IEndoBindingItem
    {
        public int Id { get; }
        public int? IdPadre { get; } // Richiesto dalla vecchia logica di sincronizzazione endo
        public string Descrizione { get; }
        public bool Selezionato { get; }
    }


    public class TipoEndoBindingItem
    {
        public bool Espanso { get; set; } = false;
        public string Descrizione { get; init; } = "";
        public IEnumerable<EndoBindingItem> Endo { get; set; } = Enumerable.Empty<EndoBindingItem>();

        public bool Toggle() => this.Espanso = !this.Espanso;

        public IEnumerable<int> IdEndoSelezionati
        {
            get
            {
                var endoSelezionati = this.Endo.Where(x => x.Selezionato);
                var subendo = endoSelezionati.SelectMany(endo =>
                                                            endo.SubEndo.Where(sub => sub.Selezionato))
                                                                        .Select(sub => sub.Id);

                return endoSelezionati.Select(endo => endo.Id).Union(subendo);
            }
        }
    }

    public class FamigliaEndoBindingItem
    {
        public bool Espansa { get; set; } = false;
        public string Descrizione { get; init; } = "";
        public IEnumerable<TipoEndoBindingItem> Tipi { get; set; } = Enumerable.Empty<TipoEndoBindingItem>();

        public bool Toggle() => this.Espansa = !this.Espansa;

        public IEnumerable<int> IdEndoSelezionati => this.Tipi.SelectMany(y => y.IdEndoSelezionati).Distinct();
    }
}
