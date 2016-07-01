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
    public partial class CCDettagliSuperficieMgr
    {
        private void VerificaRecordCollegati(CCDettagliSuperficie cls)
        {
            if (recordCount("CC_ICALCOLI_DETTAGLIOT", "FK_CCDS_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCDS_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLI_DETTAGLIOT");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCDettagliSuperficie> Find(string token, string fkcctsid, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCDettagliSuperficie filtro = new CCDettagliSuperficie();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;
            if (string.IsNullOrEmpty(fkcctsid))
            {
                filtro.FkCcTsId = null;
            }
            else
            {
                filtro.FkCcTsId = Convert.ToInt32(fkcctsid);
            }
            filtro.UseForeign = useForeignEnum.Yes;

            List<CCDettagliSuperficie> list = authInfo.CreateDatabase().GetClassList(filtro, false, true).ToList<CCDettagliSuperficie>();

            ListSortManager<CCDettagliSuperficie>.Sort(list, sortExpression);

            return list;
			
        }
    }
}
