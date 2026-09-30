using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    internal class OneriRepository : IOneriRepository
    {
        private readonly OneriServiceCreator _oneriServiceCreator;
        private readonly TabelleDiBaseServiceCreator _tabelleDiBaseServiceCreator;

        public OneriRepository(OneriServiceCreator oneriServiceCreator, TabelleDiBaseServiceCreator tabelleDiBaseServiceCreator)
        {
            this._oneriServiceCreator = oneriServiceCreator ?? throw new System.ArgumentNullException(nameof(oneriServiceCreator));
            this._tabelleDiBaseServiceCreator = tabelleDiBaseServiceCreator ?? throw new System.ArgumentNullException(nameof(tabelleDiBaseServiceCreator));
        }

        public IEnumerable<Onere> GetByIdInterventoIdEndo(int codiceIntervento, List<int> listaIdEndo)
        {
            return this._oneriServiceCreator.Call(ws =>
            {
                var rVal = ws.Service.GetListaOneriDaIdInterventoECodiciEndo(ws.Token, codiceIntervento, listaIdEndo.ToArray());

                if (rVal == null)
                {
                    return Enumerable.Empty<Onere>();
                }

                return rVal.Select(x => new Onere(x));
            });
        }

        public IEnumerable<TipoPagamento> GetTipiPagamento()
        {
            return this._tabelleDiBaseServiceCreator.Call(ws =>
            {
                return ws.Service.GetModalitaPagamento(ws.Token).Select(x => new TipoPagamento(x.Codice, x.Descrizione));
            });
        }

        public TipoPagamento GetModalitaPagamentoById(string modalitaPagamentoId)
        {
            return this.GetTipiPagamento().Where(x => x.Codice == modalitaPagamentoId).FirstOrDefault();
        }

        public string GetCodiceCausaleOnereTraslazione(int idCausale)
        {
            return this._oneriServiceCreator.Call(ws =>
            {
                return ws.Service.GetCodiceCausaleOnereTraslazione(ws.Token, idCausale);
            });
        }
    }
}
