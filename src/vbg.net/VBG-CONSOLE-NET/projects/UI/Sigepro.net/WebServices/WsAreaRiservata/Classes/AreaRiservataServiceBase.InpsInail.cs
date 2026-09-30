using Init.SIGePro.Manager.DTO;
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
        public BaseDto<string,string>[] GetElencoSediInps(string token)
        {
            var auth = CheckToken(token);

            return new InpsInailMgr(auth.CreateDatabase(), auth.IdComune).GetElencoSediInps().ToArray();
        }

        [WebMethod]
        public BaseDto<string, string>[] GetElencoSediInail(string token)
        {
            var auth = CheckToken(token);

            return new InpsInailMgr(auth.CreateDatabase(), auth.IdComune).GetElencoSediInail().ToArray();
        }
    }
}