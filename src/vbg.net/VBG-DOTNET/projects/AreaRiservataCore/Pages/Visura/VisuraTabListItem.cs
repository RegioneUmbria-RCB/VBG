namespace AreaRiservataCore.Pages.Visura
{
    public class VisuraTabListItem
    {
        public bool IsActive { get; set; }
        public string Descrizione { get; set; }
        public string Id { get; set; }
        public bool VisibileDaArchivio { get; set; }
        public bool HasBadge { get; set; }
        public string ValoreBadge { get; set; }
        public int Badge { get; set; } = 0;
    }
}
