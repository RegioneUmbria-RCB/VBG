using Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari;
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
        public ConfigurazioneSoggettiFirmatariDto GetSoggettiFirmatariDaIdDocumenti(string token, RichiestaSoggettiFirmatariDaIdDocumenti idDocumenti)
        {
            var authInfo = CheckToken(token);
            var svc = new SoggettiFirmatariService(authInfo);

            return svc.GetSoggettiFirmatariDaIdDocumenti(idDocumenti);
        }
    }
}