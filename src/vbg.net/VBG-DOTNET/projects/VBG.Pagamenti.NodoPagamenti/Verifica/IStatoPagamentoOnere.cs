using System;

namespace VBG.Pagamenti.NodoPagamenti.Verifica
{
    public interface IStatoPagamentoOnere
    {
        StatoPagamentoEnum Stato { get; }
        string StatoPagamentoNativo { get; }
        DateTime? DataOraPagamento { get; }
        string IdPosizione { get; }
        string[] RiferimentiClient { get; }
    }
}
