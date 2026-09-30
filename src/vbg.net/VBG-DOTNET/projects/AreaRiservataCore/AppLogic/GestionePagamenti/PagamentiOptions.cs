namespace AreaRiservataCore.AppLogic.GestionePagamenti
{
    public class ParametryRetryDocumentoPagamenti
    {
        public int MaxTentativi { get; set; } = 10;
        public int IntervalloTentativi { get; set; } = 3000;
    }

    public class PagamentiOptions
    {
        public const string SectionName = "Pagamenti";
        public ParametryRetryDocumentoPagamenti VerificaPagamento { get; set; } = new();
        public ParametryRetryDocumentoPagamenti DownloadRicevuta { get; set; } = new();
        public ParametryRetryDocumentoPagamenti DownloadAvviso { get; set; } = new();
    }
}
