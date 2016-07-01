
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
    public partial class DomandeFrontEndoMgr
    {
		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<DomandeFrontEndo> Find(string token, int idDomanda)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			DomandeFrontMgr mgr = new DomandeFrontMgr(authInfo.CreateDatabase());

			DomandeFrontEndo filtro = new DomandeFrontEndo();

			filtro.Idcomune = authInfo.IdComune;
			filtro.Codicedomanda = idDomanda;
			filtro.UseForeign = useForeignEnum.Yes;

			List<DomandeFrontEndo> list = authInfo.CreateDatabase().GetClassList(filtro, false, true).ToList<DomandeFrontEndo>();

			return list;
		}


		public List<DomandeFrontEndo> GetByIdDomanda(string idComune, int idDomanda)
		{
			DomandeFrontEndo filtro = new DomandeFrontEndo();
			filtro.Idcomune = idComune;
			filtro.Codicedomanda = idDomanda;

			return GetList(filtro);
		}
	}
}
				