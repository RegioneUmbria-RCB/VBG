using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCITabella1Mgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCITabella1> Find(string token, int idCalcolo)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCITabella1Mgr mgr = new CCITabella1Mgr(authInfo.CreateDatabase());

            CCITabella1 filtro = new CCITabella1();

            filtro.Idcomune = authInfo.IdComune;
            filtro.FkCcicId = idCalcolo;
            filtro.OrderBy = "ID ASC";

            return mgr.GetList(filtro);
        }
    }
}
