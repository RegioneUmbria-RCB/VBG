using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Ssu.GestioneOneri.Specifications
{
    public class ImportoDaPagareUgualeAZero : ISpecificationWithErrorMessage<IDomandaOnlineReadInterface>
    {
        public string ErrorMessage { get; private set; } = "";

        public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
        {
            if (item.Oneri.TotalePagato <= 0.01m)
            {
                this.ErrorMessage = "";
                return true;
            }
            else
            {
                this.ErrorMessage = "L'importo da pagare è maggiore di zero";
                return false;
            }
        }
    }
}
