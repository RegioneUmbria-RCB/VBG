using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraAutorizzazioni
    {
        public class VisuraAutorizzazioniListItem
        {
            public required string Descrizione { get; set; }
            public required DateTime? Data { get; set; }
            public required string Note { get; set; }
            public required string Numero { get; set; }
            public required DateTime? DataScadenza { get; set; }
            public required DateTime? DataCessazione { get; set; }
            public required bool Attiva { get; set; }
            public string Stato => this.Attiva ? "Attiva" : "Cessata";
        }

        [Parameter]
        public IEnumerable<VisuraAutorizzazioniListItem> DataSource { get; set; } = Enumerable.Empty<VisuraAutorizzazioniListItem>();
    }
}
