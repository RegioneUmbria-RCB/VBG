using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTabellaCaratteristMgr
    {
        private void VerificaRecordCollegati(CCTabellaCaratterist cls)
        {
            if (this.recordCount("CC_ITABELLA4", "FK_CCTC_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCTC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ITABELLA4");

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTabellaCaratterist> Find(string token, int? id, string descrizione, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCTabellaCaratterist filtro = new CCTabellaCaratterist();
            CCTabellaCaratterist filtroCompare = new CCTabellaCaratterist();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.Id = id;

            filtroCompare.Descrizione = "LIKE";

            List<CCTabellaCaratterist> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false);

            ListSortManager<CCTabellaCaratterist>.Sort(list, sortExpression);

            return list;
        }
    }
}
