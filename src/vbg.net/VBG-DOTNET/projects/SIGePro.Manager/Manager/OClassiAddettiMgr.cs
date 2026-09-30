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
    public partial class OClassiAddettiMgr
    {
        private void VerificaRecordCollegati(OClassiAddetti cls)
        {
            var condizioniTabellad = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_OCA_ID", cls.Id.ToString())
            };

            if (this.recordCount("O_TABELLAD", "FK_OCA_ID", condizioniTabellad) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            var condizioniCalcoloContribt = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_OCLA_ID", cls.Id.ToString())
            };

            if (this.recordCount("O_ICALCOLOCONTRIBT", "FK_OCLA_ID", condizioniCalcoloContribt) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBT");

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OClassiAddetti> Find(string token, int? codice, string classe, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

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
