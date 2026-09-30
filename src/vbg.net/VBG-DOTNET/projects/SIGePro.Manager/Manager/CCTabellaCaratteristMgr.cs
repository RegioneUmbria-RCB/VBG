using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTabellaCaratteristMgr
    {
        private void VerificaRecordCollegati(CCTabellaCaratterist cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCTC_ID", cls.Id.ToString())
            };

            if (this.recordCount("CC_ITABELLA4", "FK_CCTC_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_ITABELLA4");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTabellaCaratterist> Find(string token, int? id, string descrizione, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCTabellaCaratterist filtro = new CCTabellaCaratterist();
            CCTabellaCaratterist filtroCompare = new CCTabellaCaratterist();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.Id = id;

            filtroCompare.Descrizione = "LIKE";

            List<CCTabellaCaratterist> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CCTabellaCaratterist>();

            ListSortManager<CCTabellaCaratterist>.Sort(list, sortExpression);

            return list;
        }
    }
}
