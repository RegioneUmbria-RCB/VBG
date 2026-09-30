
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
    public partial class InterventiDyn2ModelliTMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<InterventiDyn2ModelliT> Find(string token, int codiceIntervento, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

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
				