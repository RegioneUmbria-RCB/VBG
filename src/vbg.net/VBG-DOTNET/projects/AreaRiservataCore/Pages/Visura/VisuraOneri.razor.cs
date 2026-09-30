using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraOneri
    {
        public class VisuraOneriListItem
        {
            public string Causale { get; set; }
            public float Importo { get; set; }
            public DateTime? DataScadenza { get; set; }
            public DateTime? DataPagamento { get; set; }
        }

        [Parameter]
        public IEnumerable<VisuraOneriListItem> DataSource { get; set; }
    }
}
