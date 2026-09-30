namespace AreaRiservataCore.Pages.Ssu.Shared
{
    public class ListaDomandeFilter
    {
        public DateTime? StartDate { get; set; }
        public DateTime? EndDate { get; set; }
        public string? Richiedente { get; set; }

        public bool FiltroPerDataImpostato()
        {
            return this.StartDate.HasValue || this.EndDate.HasValue;
        }

        public bool FiltroPerRichiedenteImpostato()
        {
            return !string.IsNullOrEmpty(this.Richiedente);
        }
    }

    public enum TipoFiltroDomandeSsu
    {
        Data,
        Richiedente
    }
}
