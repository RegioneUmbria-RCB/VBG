using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.SIGePro.Manager.DTO.Comuni;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class CittadinanzeServiceFake : ICittadinanzeService
    {
        public CittadinanzaCompatto GetCittadinanzaDaId(int codiceCittadinanza)
        {
            return new CittadinanzaCompatto
            {
                Codice = codiceCittadinanza,
                Descrizione = "ITALIA"
            };
        }

        public IEnumerable<CittadinanzaCompatto> GetListaCittadinanze(bool italiaAlTop)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<CittadinanzaCompatto> GetListaCittadinanze()
        {
            throw new NotImplementedException();
        }

        public bool IsCittadinanzaExtracomunitaria(int codiceCittadinanza)
        {
            throw new NotImplementedException();
        }
    }
}
