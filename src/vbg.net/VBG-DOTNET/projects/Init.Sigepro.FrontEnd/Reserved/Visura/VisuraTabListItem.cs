using System.Web.UI;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public class VisuraTabListItem
    {
        public bool IsActive { get; set; }
        public string Descrizione { get; set; }
        public string Id { get; set; }
        public bool VisibileDaArchivio { get; set; }
        public UserControl Control { get; set; }
        public bool HasBadge { get; set; }
        public string ValoreBadge { get; set; }
    }
}