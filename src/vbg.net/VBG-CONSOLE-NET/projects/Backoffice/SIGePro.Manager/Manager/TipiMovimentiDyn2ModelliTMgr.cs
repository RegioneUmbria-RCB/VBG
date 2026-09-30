
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
    public partial class TipiMovimentiDyn2ModelliTMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<TipiMovimentiDyn2ModelliT> Find(string token, string tipoMovimento, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            TipiMovimentiDyn2ModelliTMgr mgr = new TipiMovimentiDyn2ModelliTMgr(authInfo.CreateDatabase());

            TipiMovimentiDyn2ModelliT filtro = new TipiMovimentiDyn2ModelliT();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Tipomovimento = tipoMovimento;
            filtro.UseForeign = useForeignEnum.Yes;

            List<TipiMovimentiDyn2ModelliT> list = mgr.GetList(filtro);
            //ListSortManager<TipiMovimentiDyn2ModelliT>.Sort(list, sortExpression);

            return list;
        }	
	}
}
				