using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.Utils.Sorting;
using PersonalLib2.Sql;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{

    [DataObject(true)]
    public partial class CanoniCategorieMgr
    {

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CanoniCategorie> Find(string token, string software, string descrizione, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CanoniCategorie filtro = new CanoniCategorie();
            CanoniCategorie filtroCompare = new CanoniCategorie();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Descrizione = "LIKE";

            // gestione ordinamento
            List<CanoniCategorie> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<CanoniCategorie>();
            ListSortManager<CanoniCategorie>.Sort(list, sortExpression);
            // fine gestione ordinamento
            return list;
        }

        private void VerificaRecordCollegati(CanoniCategorie cls)
        {
            if (recordCount("CANONI_COEFFICIENTI", "FK_CCID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CANONI_COEFFICIENTI");

            if (recordCount("PERTINENZE_COEFFICIENTI", "FK_CCID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("PERTINENZE_COEFFICIENTI");

            if (recordCount("ISTANZECALCOLOCANONI_D", "FK_CCID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("ISTANZECALCOLOCANONI_D");
        }
    }
}
