using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
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
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CanoniCategorie filtro = new CanoniCategorie();
            CanoniCategorie filtroCompare = new CanoniCategorie();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Descrizione = "LIKE";

            // gestione ordinamento
            List<CanoniCategorie> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CanoniCategorie>();
            ListSortManager<CanoniCategorie>.Sort(list, sortExpression);
            // fine gestione ordinamento
            return list;
        }

        private void VerificaRecordCollegati(CanoniCategorie cls)
        {
            var coeffConditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCID", cls.Id.ToString())
            };
            if (this.recordCount("CANONI_COEFFICIENTI", "FK_CCID", coeffConditions) > 0)
                throw new ReferentialIntegrityException("CANONI_COEFFICIENTI");

            if (this.recordCount("PERTINENZE_COEFFICIENTI", "FK_CCID", coeffConditions) > 0)
                throw new ReferentialIntegrityException("PERTINENZE_COEFFICIENTI");

            if (this.recordCount("ISTANZECALCOLOCANONI_D", "FK_CCID", coeffConditions) > 0)
                throw new ReferentialIntegrityException("ISTANZECALCOLOCANONI_D");
        }
    }
}
