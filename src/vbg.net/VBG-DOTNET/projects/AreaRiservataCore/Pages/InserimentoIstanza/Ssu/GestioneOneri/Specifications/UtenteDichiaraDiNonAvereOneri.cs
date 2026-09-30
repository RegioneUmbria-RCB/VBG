using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Ssu.GestioneOneri.Specifications
{
    public class UtenteDichiaraDiNonAvereOneri : ISpecificationWithErrorMessage<IDomandaOnlineReadInterface>
    {
        public string ErrorMessage { get; private set; } = "";

        public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
        {
            if (item.Oneri.DichiaraDiNonAvereOneriDaPagare)
            {
                this.ErrorMessage = "";
                return true;
            }
            else
            {
                this.ErrorMessage = "Per poter proseguire è necessario dichiarare di non avere oneri";
                return false;
            }
        }
    }
}
