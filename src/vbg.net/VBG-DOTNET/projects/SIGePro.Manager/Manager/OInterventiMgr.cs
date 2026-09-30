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
    public partial class OInterventiMgr
    {
        private void VerificaRecordCollegati(OInterventi cls)
        {
            var condizioni = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_OIN_ID", cls.Id.ToString())
            };

            if (this.recordCount("O_TABELLAABC", "FK_OIN_ID", condizioni) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");

            if (this.recordCount("O_ICALCOLOCONTRIBT", "FK_OIN_ID", condizioni) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBT");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OInterventi> Find(string token, int? codice, string intervento, string interventiBase, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            OInterventi filtro = new OInterventi();
            OInterventi filtroCompare = new OInterventi();

            filtro.Id = codice;
            filtro.Idcomune = authInfo.IdComune;
            filtro.Intervento = intervento;
            filtro.FkOccbtiId = interventiBase;

            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;
            filtroCompare.Intervento = "LIKE";

            List<OInterventi> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<OInterventi>();
            ListSortManager<OInterventi>.Sort(list, sortExpression);

            return list;
        }
    }
}
