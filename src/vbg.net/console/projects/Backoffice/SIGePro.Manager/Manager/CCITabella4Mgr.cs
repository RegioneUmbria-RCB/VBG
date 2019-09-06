using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using System.ComponentModel;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class CCITabella4Mgr
	{
		[DataObjectMethod( DataObjectMethodType.Select)]
		public static List<CCITabella4> Find(string token, int idCalcolo)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			CCITabella4Mgr mgr = new CCITabella4Mgr(authInfo.CreateDatabase());

			CCITabella4 filtro = new CCITabella4();
			filtro.Idcomune = authInfo.IdComune;
			filtro.FkCcicId = idCalcolo;

            return mgr.GetList(filtro);
		}
	}
}
