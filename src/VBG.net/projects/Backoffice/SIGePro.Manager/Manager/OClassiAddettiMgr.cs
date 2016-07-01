using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OClassiAddettiMgr
    {
        private void VerificaRecordCollegati(OClassiAddetti cls)
        {
            if (recordCount("O_TABELLAD", "FK_OCA_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_OCA_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            if (recordCount("O_ICALCOLOCONTRIBT", "FK_OCLA_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_OCLA_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBT");

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OClassiAddetti> Find(string token, int? codice , string classe, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            OClassiAddetti filtro = new OClassiAddetti();
            OClassiAddetti filtroCompare = new OClassiAddetti();

            filtro.Idcomune = authInfo.IdComune;
			filtro.Id = codice;
            filtro.Classe = classe;
            filtro.Software = software;

            filtroCompare.Classe = "Like";

            List<OClassiAddetti> list = authInfo.CreateDatabase().GetClassList(filtro).ToList<OClassiAddetti>();

            ListSortManager<OClassiAddetti>.Sort(list, sortExpression);

            return list;
        }
    }
}
