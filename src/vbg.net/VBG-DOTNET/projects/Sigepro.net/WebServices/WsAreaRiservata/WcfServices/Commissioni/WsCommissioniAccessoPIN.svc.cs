using Init.SIGePro.Manager.Logic.GestioneCommissioni.AccessoPIN;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsCommissioniAccessoPIN" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsCommissioniAccessoPIN.svc or WsCommissioniAccessoPIN.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsCommissioniAccessoPIN : WcfServiceBase, IWsCommissioniAccessoPIN
    {
        public int AssociaUtenteACommissioneByPIN(string token, int codiceAnagrafe, string pin)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var auditing = new DatabaseCommissioniAuditingService(db, authInfo.IdComune);
                var dao = new CommissioniAccessoPINDao(db, authInfo.IdComune);
                var service = new CommissioniAccessoPINService(dao, auditing);

                return service.AssociaUtenteACommissioneByPIN(codiceAnagrafe, pin);
            }
        }

        public bool VerificaValiditaPIN(string token, string pin)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var auditing = new DatabaseCommissioniAuditingService(db, authInfo.IdComune);
                var dao = new CommissioniAccessoPINDao(db, authInfo.IdComune);
                var service = new CommissioniAccessoPINService(dao, auditing);

                return service.VerificaValiditaPIN(pin);
            }
        }

    }
}
