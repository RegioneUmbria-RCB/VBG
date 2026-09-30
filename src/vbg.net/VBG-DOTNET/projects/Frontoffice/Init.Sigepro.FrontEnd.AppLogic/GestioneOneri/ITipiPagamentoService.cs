using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    public interface ITipiPagamentoService
    {
        IEnumerable<TipoPagamento> GetListaTipiPagamento();
        TipoPagamento GetTipoPagamentoById(string idTipoPagamento);
    }
}
