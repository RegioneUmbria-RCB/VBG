namespace AreaRiservataCore.Pages.IstanzeInSospeso.v2.Componenti
{
    public class FiltroIstanzeInSospeso
    {
        public DateTime? startDate { get; set; }
        public DateTime? endDate { get; set; }
        public string? richiedente { get; set; }

        public bool FiltroPerDataImpostato()
        {
            return startDate != null || endDate != null;
        }

        public bool FiltroPerRichiedenteImpostato()
        {
            return !string.IsNullOrEmpty(richiedente);
        }
    }

    public enum TipoFiltroIstanzeInSospeso
    {
        Data,
        Richiedente
    }
}
