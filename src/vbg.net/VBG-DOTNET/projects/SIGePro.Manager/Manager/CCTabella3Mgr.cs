using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTabella3Mgr
    {
        private void VerificaRecordCollegati(CCTabella3 cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCT3_ID", cls.Id.ToString())
            };

            if (this.recordCount("CC_ITABELLA3", "FK_CCT3_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_ITABELLA3");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTabella3> Find(string token, string id, string descrizione, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCTabella3 filtro = new CCTabella3();
            CCTabella3 filtroCompare = new CCTabella3();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;

            if (!String.IsNullOrEmpty(id))
            {
                int iId = 0;

                if (int.TryParse(id, out iId))
                    filtro.Id = iId;
            }

            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Descrizione = "LIKE";

            List<CCTabella3> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CCTabella3>();
            ListSortManager<CCTabella3>.Sort(list, sortExpression);

            return list;
        }
    }
}
