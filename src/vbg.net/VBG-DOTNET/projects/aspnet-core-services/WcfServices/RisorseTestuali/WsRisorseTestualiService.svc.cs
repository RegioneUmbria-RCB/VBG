using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.DTO.RisorseTestuali;
using Init.SIGePro.Manager.Logic.GestioneRisorseTestuali;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.RisorseTestuali
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsRisorseTestualiService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsRisorseTestualiService.svc or WsRisorseTestualiService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsRisorseTestualiService : WcfServiceBase, IWsRisorseTestualiService
    {
        public WsRisorseTestualiService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public RisorsaTestualeDto[] GetRisorseTestuali(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new RisorseTestualiService(db, authInfo.IdComune);

                return mgr.GetList(software, RisorseTestualiService.PrefissiRisorse.RisorsaAreaRiservata).Select(x => new RisorsaTestualeDto
                {
                    Chiave = x.Chiave,
                    Valore = x.Valore,
                }).ToArray();
            }
        }

        public void AggiornaRisorsaTestuale(string token, string software, string chiave, string valore, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new RisorseTestualiService(db, authInfo.IdComune);
                var auditService = new LayoutTestiAuditService(db, authInfo.IdComune, codiceAnagrafe);

                mgr.AggiornaRisorsa(software, chiave, valore, auditService);
            }
        }
    }
}
