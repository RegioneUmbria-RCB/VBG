using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica
{
    public interface IEsitoVerificaPosizioni
    {
        string CodiceFiscaleEnteCreditore { get; }
        StatoPagamentoEnum StatoGlobale { get; }
        IEnumerable<IStatoPagamentoOnere> StatoOneri { get; }
    }
}
