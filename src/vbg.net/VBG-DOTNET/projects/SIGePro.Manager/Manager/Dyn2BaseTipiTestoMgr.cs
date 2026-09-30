using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2BaseTipiTestoMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<Dyn2BaseTipiTesto> Find(string token)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            Dyn2BaseTipiTestoMgr mgr = new Dyn2BaseTipiTestoMgr(authInfo.CreateDatabase());

            List<Dyn2BaseTipiTesto> list = mgr.GetList(new Dyn2BaseTipiTesto());

            return list;
        }

    }
}
