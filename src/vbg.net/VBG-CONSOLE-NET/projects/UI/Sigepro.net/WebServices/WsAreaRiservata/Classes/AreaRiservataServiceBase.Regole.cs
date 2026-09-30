using Init.SIGePro.Manager.DTO.Mappature;
using Init.SIGePro.Manager.Manager;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
    public partial class AreaRiservataServiceBase
    {
        [WebMethod]
        public MappaturaDto[] GetMappature(string token, string software)
        {
            var authInfo = CheckToken(token);

            using(var db = authInfo.CreateDatabase())
            {
                var mgr = new MappatureMgr(db, authInfo.IdComune);

                var mappature = new List<MappaturaDto>(mgr.GetList(software));

                mappature.AddRange(mgr.GetList("TT"));

                return mappature.ToArray();
            }
        }
    }
}