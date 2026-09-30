using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.Infrastructure;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications
{
    public class DomandaContieneAlmenoUnOnereSpecification : ISpecification<IDomandaOnlineReadInterface>
    {
        public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
        {
            return item.Oneri.Oneri.Count() > 0;
        }
    }
}