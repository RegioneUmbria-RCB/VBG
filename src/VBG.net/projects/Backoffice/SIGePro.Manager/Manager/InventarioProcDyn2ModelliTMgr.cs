
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class InventarioProcDyn2ModelliTMgr
    {

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<InventarioProcDyn2ModelliT> Find(string token, int Codiceinventario, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            InventarioProcDyn2ModelliTMgr mgr = new InventarioProcDyn2ModelliTMgr(authInfo.CreateDatabase());

            InventarioProcDyn2ModelliT filtro = new InventarioProcDyn2ModelliT();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Codiceinventario = Codiceinventario;
            filtro.UseForeign = useForeignEnum.Yes;

            List<InventarioProcDyn2ModelliT> list = mgr.GetList(filtro);
            ListSortManager<InventarioProcDyn2ModelliT>.Sort(list, sortExpression);

            return list;
        }

    }
}
