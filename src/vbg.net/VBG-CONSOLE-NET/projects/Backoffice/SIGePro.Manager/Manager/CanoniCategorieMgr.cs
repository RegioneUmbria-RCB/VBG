using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

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
            List<CanoniCategorie> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false);
            ListSortManager<CanoniCategorie>.Sort(list, sortExpression);
            // fine gestione ordinamento
            return list;
        }

        private void VerificaRecordCollegati(CanoniCategorie cls)
        {
            if (this.recordCount("CANONI_COEFFICIENTI", "FK_CCID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CANONI_COEFFICIENTI");

            if (this.recordCount("PERTINENZE_COEFFICIENTI", "FK_CCID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("PERTINENZE_COEFFICIENTI");

            if (this.recordCount("ISTANZECALCOLOCANONI_D", "FK_CCID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("ISTANZECALCOLOCANONI_D");
        }
    }
}
