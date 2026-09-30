using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OIndiciTerritorialiMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OIndiciTerritoriali> Find(string token, string software)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            OIndiciTerritoriali filtro = new OIndiciTerritoriali();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;

            return authInfo.CreateDatabase().GetClassList(filtro).ToList<OIndiciTerritoriali>();
        }

        private void VerificaRecordCollegati(OIndiciTerritoriali cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_OIT_ID", cls.Id.ToString())
            };

            if (this.recordCount("O_TABELLAABC", "FK_OIT_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");
        }
    }
}
