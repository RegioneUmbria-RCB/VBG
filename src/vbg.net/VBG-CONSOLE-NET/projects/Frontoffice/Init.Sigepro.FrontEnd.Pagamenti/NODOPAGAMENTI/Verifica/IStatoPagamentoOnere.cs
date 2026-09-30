using System;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica
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
