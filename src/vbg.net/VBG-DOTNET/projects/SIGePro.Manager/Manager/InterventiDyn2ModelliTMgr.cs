using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class InterventiDyn2ModelliTMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<InterventiDyn2ModelliT> Find(string token, int codiceIntervento, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            InterventiDyn2ModelliTMgr mgr = new InterventiDyn2ModelliTMgr(authInfo.CreateDatabase());

            InterventiDyn2ModelliT filtro = new InterventiDyn2ModelliT();

            filtro.Idcomune = authInfo.IdComune;
            filtro.CodiceIntervento = codiceIntervento;
            filtro.UseForeign = useForeignEnum.Yes;

            List<InterventiDyn2ModelliT> list = mgr.GetList(filtro);
            ListSortManager<InterventiDyn2ModelliT>.Sort(list, sortExpression);

            return list;
        }
    }
}
