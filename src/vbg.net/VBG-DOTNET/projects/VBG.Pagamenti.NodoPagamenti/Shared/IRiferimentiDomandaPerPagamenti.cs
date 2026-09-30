namespace VBG.Pagamenti.NodoPagamenti.Shared
{
    public interface IRiferimentiDomandaPerPagamenti
    {
        string IdComune { get; }
        string Software { get; }
        int IdPresentazione { get; }
        string CodiceUnivocoDomanda { get; }
    }
}
