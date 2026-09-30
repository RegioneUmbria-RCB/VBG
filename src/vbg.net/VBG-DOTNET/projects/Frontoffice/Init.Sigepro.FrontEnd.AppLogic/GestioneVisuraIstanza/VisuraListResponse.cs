using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public class VisuraListResponse
    {
        public IEnumerable<VisuraListItem> Pratiche { get; set; } = Enumerable.Empty<VisuraListItem>();
        public int RecordCount { get; set; } = 0;
        public int TotalPages { get; set; } = 0;
    }
}
