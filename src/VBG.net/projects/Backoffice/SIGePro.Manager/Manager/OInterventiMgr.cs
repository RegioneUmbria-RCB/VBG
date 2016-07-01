using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions;
using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OInterventiMgr
    {
        private void VerificaRecordCollegati(OInterventi cls)
        {
            if (recordCount("O_TABELLAABC", "FK_OIN_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_OIN_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");

            if (recordCount("O_ICALCOLOCONTRIBT", "FK_OIN_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_OIN_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBT");


        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OInterventi> Find(string token, int? codice, string intervento, string interventiBase, string software, string sortExpression )
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            OInterventi filtro = new OInterventi();
            OInterventi filtroCompare = new OInterventi();

			filtro.Id = codice;
            filtro.Idcomune = authInfo.IdComune;
            filtro.Intervento = intervento;
            filtro.FkOccbtiId = interventiBase;

            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;
            filtroCompare.Intervento = "LIKE";

            List<OInterventi> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<OInterventi>();
            ListSortManager<OInterventi>.Sort(list, sortExpression);

            return list;
        }
    }
}
