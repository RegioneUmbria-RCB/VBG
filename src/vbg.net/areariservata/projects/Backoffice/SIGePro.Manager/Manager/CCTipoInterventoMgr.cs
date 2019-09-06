using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTipoInterventoMgr
    {
        private void VerificaRecordCollegati(CCTipoIntervento cls)
        {
            if (recordCount("CC_ICALCOLO_DCONTRIBUTO", "FK_CCTI_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCTI_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLO_DCONTRIBUTO");

            if (recordCount("CC_COEFFCONTRIBUTO", "FK_CCTI_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCTI_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIBUTO");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTipoIntervento> Find(string token, int? id, string intervento, string interventoBase, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCTipoIntervento filtro = new CCTipoIntervento();
            CCTipoIntervento filtroCompare = new CCTipoIntervento();

			filtro.Id = id;
            filtro.Idcomune = authInfo.IdComune;
            filtro.Intervento = intervento;
            filtro.FkOccbtiId = interventoBase;
            filtro.Software = software;

            filtroCompare.Intervento = "LIKE";

			return authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList < CCTipoIntervento>();
        }

    }
}
