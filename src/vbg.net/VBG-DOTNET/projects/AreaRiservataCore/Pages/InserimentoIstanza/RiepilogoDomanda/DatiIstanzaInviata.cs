namespace AreaRiservataCore.Pages.InserimentoIstanza.RiepilogoDomanda
{
    public record class DatiIstanzaInviata(int IdDomandaOnline, string CodiceIstanza, string NumeroIstanza)
    {
        public string? NumeroProtocollo { get; init; }
        public DateTime? DataProtocollo { get; init; }
    }
}
