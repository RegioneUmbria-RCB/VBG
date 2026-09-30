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
    public partial class CCClassiSuperficiMgr
    {
        private void VerificaRecordCollegati(CCClassiSuperfici cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCCS_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_ITABELLA1", "FK_CCCS_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_ITABELLA1");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCClassiSuperfici> Find(string token, int? id, string classe, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCClassiSuperfici filtro = new CCClassiSuperfici();
            CCClassiSuperfici filtroCompare = new CCClassiSuperfici();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Classe = classe;
            filtro.Software = software;
            filtro.Id = id;

            filtroCompare.Classe = "LIKE";

            // gestione ordinamento
            List<CCClassiSuperfici> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CCClassiSuperfici>();
            ListSortManager<CCClassiSuperfici>.Sort(list, sortExpression);
            // fine gestione ordinamento

            return list;

        }
    }
}
