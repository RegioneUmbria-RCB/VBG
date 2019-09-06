using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCClassiSuperficiMgr
    {
        private void VerificaRecordCollegati(CCClassiSuperfici cls)
        {
            if (recordCount("CC_ITABELLA1", "FK_CCCS_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCCS_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ITABELLA1");


        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCClassiSuperfici> Find(string token, int? id, string classe, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCClassiSuperfici filtro = new CCClassiSuperfici();
            CCClassiSuperfici filtroCompare = new CCClassiSuperfici();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Classe = classe;
            filtro.Software = software;
            filtro.Id = id;

            filtroCompare.Classe = "LIKE";

            // gestione ordinamento
			List<CCClassiSuperfici> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<CCClassiSuperfici>();
            ListSortManager<CCClassiSuperfici>.Sort(list, sortExpression);
            // fine gestione ordinamento

            return list;

        }
    }
}
