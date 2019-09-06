using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OIndiciTerritorialiMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OIndiciTerritoriali> Find(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            OIndiciTerritoriali filtro = new OIndiciTerritoriali();
            
            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;

			return authInfo.CreateDatabase().GetClassList(filtro).ToList < OIndiciTerritoriali>();
        }

        private void VerificaRecordCollegati(OIndiciTerritoriali cls)
        {
            if (recordCount("O_TABELLAABC", "FK_OIT_ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' AND FK_OIT_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");
        }
    }
}
