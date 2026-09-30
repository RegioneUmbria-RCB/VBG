using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager.Logic.GestioneOneri.GestioneConti;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Conti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsContiService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsContiService.svc or WsContiService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsContiService : WcfServiceBase, IWsContiService
    {
        public WsContiService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public ContoDto GetContoDaIdCausaleOnere(string token, int idCausale)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                return new ContiService(db, ai.IdComune).GetContoDaIdCausaleOnere(idCausale);
            }
        }
    }
}
