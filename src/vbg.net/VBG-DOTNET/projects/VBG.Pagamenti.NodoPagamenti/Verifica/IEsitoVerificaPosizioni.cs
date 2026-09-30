using System.Collections.Generic;

namespace VBG.Pagamenti.NodoPagamenti.Verifica
{
    public interface IEsitoVerificaPosizioni
    {
        string CodiceFiscaleEnteCreditore { get; }
        StatoPagamentoEnum StatoGlobale { get; }
        IEnumerable<IStatoPagamentoOnere> StatoOneri { get; }
    }
}
