
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
    public partial class AlberoProcDyn2ModelliTMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<AlberoProcDyn2ModelliT> Find(string token, int fkScId, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            AlberoProcDyn2ModelliTMgr mgr = new AlberoProcDyn2ModelliTMgr(authInfo.CreateDatabase());

            AlberoProcDyn2ModelliT filtro = new AlberoProcDyn2ModelliT();

            filtro.Idcomune = authInfo.IdComune;
            filtro.FkScId = fkScId;
            filtro.UseForeign = useForeignEnum.Yes;

            List<AlberoProcDyn2ModelliT> list = mgr.GetList(filtro);

			if (!String.IsNullOrEmpty(sortExpression))
				ListSortManager<AlberoProcDyn2ModelliT>.Sort(list, sortExpression);

            return list;
        }	        	
	}
}
				