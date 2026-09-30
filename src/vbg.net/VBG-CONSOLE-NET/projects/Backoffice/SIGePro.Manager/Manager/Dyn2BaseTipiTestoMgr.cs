
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
    public partial class Dyn2BaseTipiTestoMgr
    {
		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<Dyn2BaseTipiTesto> Find(string token)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			Dyn2BaseTipiTestoMgr mgr = new Dyn2BaseTipiTestoMgr(authInfo.CreateDatabase());

			List<Dyn2BaseTipiTesto> list = mgr.GetList( new Dyn2BaseTipiTesto() );

			return list;
		}
	
	}
}
				