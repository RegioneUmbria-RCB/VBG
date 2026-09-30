using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTipoInterventoMgr
    {
        private void VerificaRecordCollegati(CCTipoIntervento cls)
        {
            var condizioni = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCTI_ID", cls.Id.ToString())
            };

            if (this.db.LegacyRecordCount("CC_ICALCOLO_DCONTRIBUTO", "FK_CCTI_ID", condizioni) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLO_DCONTRIBUTO");

            if (this.db.LegacyRecordCount("CC_COEFFCONTRIBUTO", "FK_CCTI_ID", condizioni) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIBUTO");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTipoIntervento> Find(string token, int? id, string intervento, string interventoBase, string software)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCTipoIntervento filtro = new CCTipoIntervento();
            CCTipoIntervento filtroCompare = new CCTipoIntervento();

            filtro.Id = id;
            filtro.Idcomune = authInfo.IdComune;
            filtro.Intervento = intervento;
            filtro.FkOccbtiId = interventoBase;
            filtro.Software = software;

            filtroCompare.Intervento = "LIKE";

            return authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CCTipoIntervento>();
        }


        public IEnumerable<CCTipoIntervento> GetListByTipoInterventoBase(string idComune, string idTipoInterventoBase)
        {
            FormattableString sql = $@"SELECT * FROM cc_tipointervento WHERE IDCOMUNE = {idComune} AND FK_OCCBTI_ID = {idTipoInterventoBase} order by intervento asc";

            return this.db.GetClassList<CCTipoIntervento>(sql);
        }
    }
}
