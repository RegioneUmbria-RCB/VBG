using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class CCITabella2Mgr
	{
		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<CCITabella2> Find(string token, int idCalcolo)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			CCITabella2Mgr mgr = new CCITabella2Mgr(authInfo.CreateDatabase());

			CCITabella2 filtro = new CCITabella2();

			filtro.Idcomune = authInfo.IdComune;
			filtro.FkCcicId = idCalcolo;
			filtro.OrderBy = "ID ASC";

			return mgr.GetList(filtro);
		}
	}
}
