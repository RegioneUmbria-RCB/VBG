namespace AreaRiservataCore.Pages.IstanzeInSospeso.v2.Componenti
{
    public class DomandeListItem
    {
        public int Id { get; init; }
        public string IdentificativoDomanda { get; init; } = "";
        public DateTime? DataUltimaModifica { get; init; }
        public string Richiedente { get; init; } = "";
        public string Intervento { get; init; } = "";
        public string Oggetto { get; init; } = "";
        public bool PagamentoAvviato { get; init; } = false;
        public bool PagamentoCompletato { get; init; } = false;
        public bool Selected { get; set; } = false;
        public string Url { get; init; } = "";
        public bool HasError = false;
    }
}
