namespace AreaRiservataCore.Pages.Ssu.Shared
{
    public class ListaDomandeItem
    {
        public required int Id { get; init; }
        public required string IdentificativoDomanda { get; init; } = "";
        public required DateTime? DataUltimaModifica { get; init; }
        public required string Richiedente { get; init; } = "";
        public required string Intervento { get; init; } = "";
        public required string Oggetto { get; init; } = "";
    }

}
