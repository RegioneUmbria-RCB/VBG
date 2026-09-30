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
    public partial class CCDettagliSuperficieMgr
    {
        private void VerificaRecordCollegati(CCDettagliSuperficie cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCDS_ID", cls.Id.ToString())
            };

            if (this.recordCount("CC_ICALCOLI_DETTAGLIOT", "FK_CCDS_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLI_DETTAGLIOT");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCDettagliSuperficie> Find(string token, string fkcctsid, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

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

            List<CCDettagliSuperficie> list = authInfo.CreateDatabase().GetClassList(filtro, false).ToList<CCDettagliSuperficie>();

            ListSortManager<CCDettagliSuperficie>.Sort(list, sortExpression);

            return list;

        }
    }
}
