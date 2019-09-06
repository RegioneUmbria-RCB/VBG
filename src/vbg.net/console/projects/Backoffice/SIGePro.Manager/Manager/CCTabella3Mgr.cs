using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTabella3Mgr
    {
        private void VerificaRecordCollegati(CCTabella3 cls)
        {
            if (recordCount("CC_ITABELLA3", "FK_CCT3_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCT3_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ITABELLA3");

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTabella3> Find(string token, string id, string descrizione, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCTabella3 filtro = new CCTabella3();
            CCTabella3 filtroCompare = new CCTabella3();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;

			if (!String.IsNullOrEmpty(id))
			{
				int iId = 0;

				if ( int.TryParse( id , out iId ) )
					filtro.Id = iId;
			}

			filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Descrizione = "LIKE";

			List<CCTabella3> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<CCTabella3>();
			ListSortManager<CCTabella3>.Sort(list, sortExpression);

			return list;
        }
    }
}
