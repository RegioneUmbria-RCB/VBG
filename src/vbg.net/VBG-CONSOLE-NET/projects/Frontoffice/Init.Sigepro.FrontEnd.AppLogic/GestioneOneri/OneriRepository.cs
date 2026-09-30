// -----------------------------------------------------------------------
// <copyright file="OneriRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Conti;
    using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
    using System;
    using System.Collections.Generic;
    using System.Linq;

    internal class OneriRepository : IOneriRepository
    {
        private readonly AreaRiservataServiceCreator _arServiceCreator;
        private readonly OneriServiceCreator _oneriServiceCreator;
        private readonly IAliasResolver _aliasResolver;

        public OneriRepository(AreaRiservataServiceCreator arServiceCreator, OneriServiceCreator serviceCreator, IAliasResolver aliasResolver)
        {
            this._arServiceCreator = arServiceCreator;
            this._oneriServiceCreator = serviceCreator ?? throw new ArgumentNullException(nameof(serviceCreator));
            this._aliasResolver = aliasResolver ?? throw new ArgumentNullException(nameof(aliasResolver));
        }

        public IEnumerable<Onere> GetByIdInterventoIdEndo(int codiceIntervento, List<int> listaIdEndo, string codiceComune)
        {
            using (var ws = this._oneriServiceCreator.CreateClient())
            {
                var rVal = ws.Service.GetListaOneriDaIdInterventoECodiciEndo(ws.Token, codiceIntervento, listaIdEndo.ToArray(), codiceComune);

                if (rVal == null)
                {
                    return Enumerable.Empty<Onere>();
                }

                return rVal.Select(x => new Onere(x));
            }
        }

        public IEnumerable<TipoPagamento> GetModalitaPagamento()
        {
            using (var ws = this._arServiceCreator.CreateClient())
            {
                return ws.Service.GetModalitaPagamento(ws.Token).Select(x => new TipoPagamento(x.Codice, x.Descrizione));
            }
        }

        public TipoPagamento GetModalitaPagamentoById(string modalitaPagamentoId)
        {
            return this.GetModalitaPagamento().Where(x => x.Codice == modalitaPagamentoId).FirstOrDefault();
        }
    }
}
