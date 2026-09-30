using Init.SIGePro.Manager.DTO.Mappature;
using Init.SIGePro.Manager.Manager;
using System.Collections.Generic;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Mappature
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsMappatureService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsMappatureService.svc or WsMappatureService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsMappatureService : WcfServiceBase, IWsMappatureService
    {
        public MappaturaDto[] GetMappature(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new MappatureMgr(db, authInfo.IdComune);

                var mappature = new List<MappaturaDto>(mgr.GetList(software));

                mappature.AddRange(mgr.GetList("TT"));

                return mappature.ToArray();
            }
        }
    }
}
