using Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.SoggettiFirmatari
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsSoggettiFirmatariService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsSoggettiFirmatariService.svc or WsSoggettiFirmatariService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsSoggettiFirmatariService : WcfServiceBase, IWsSoggettiFirmatariService
    {
        public ConfigurazioneSoggettiFirmatariDto GetSoggettiFirmatariDaIdDocumenti(string token, RichiestaSoggettiFirmatariDaIdDocumenti idDocumenti)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var svc = new SoggettiFirmatariService(db, authInfo.IdComune);

                return svc.GetSoggettiFirmatariDaIdDocumenti(idDocumenti);
            }
        }

        public VerificaSoggettiFirmatariRiepilogoDomandaDto GetSoggettiFirmatariRiepilogoDomanda(string token, int idDocumento)
        {
            var authInfo = this.CheckToken(token);
            using (var db = authInfo.CreateDatabase())
            {
                var svc = new SoggettiFirmatariService(db, authInfo.IdComune);
                return svc.GetSoggettiFirmatariRiepilogoDomanda(idDocumento);
            }
        }
    }
}
