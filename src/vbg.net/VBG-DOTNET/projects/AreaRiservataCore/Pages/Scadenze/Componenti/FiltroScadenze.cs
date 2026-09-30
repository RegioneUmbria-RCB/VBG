namespace AreaRiservataCore.Pages.Scadenze.Componenti
{
    public class FiltroScadenze
    {
        public string? DescrMovimentoDaFare { get; set; } = "";
        public string? DescrStatoIstanza { get; set; } = "";
        public string? DatiRichiedente { get; set; } = "";
        public DateTime? DataInizio { get; set; }
        public DateTime? DataFine { get; set; }

    }

    public enum TipoFiltroScadenze
    {
        Richiedente,
        StatoIstanza,
        DataInizio,
        DataFine

    }

}

