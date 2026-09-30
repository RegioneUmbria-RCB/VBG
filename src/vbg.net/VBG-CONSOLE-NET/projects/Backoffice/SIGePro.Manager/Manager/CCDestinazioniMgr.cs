using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCDestinazioniMgr
    {
        private void VerificaRecordCollegati(CCDestinazioni cls)
        {
            if (this.recordCount("CC_COEFFCONTRIBUTO", "FK_CCDE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCDE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIBUTO");

            if (this.recordCount("CC_COEFFCONTRIB_ATTIVITA", "FK_CCDE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCDE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIB_ATTIVITA");

            if (this.recordCount("CC_ICALCOLO_TCONTRIBUTO", "FK_CCDE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCDE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLO_TCONTRIBUTO");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCDestinazioni> Find(string token, int? id, string destinazione, string destinazioneBase, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCDestinazioni filtro = new CCDestinazioni();
            CCDestinazioni filtroCompare = new CCDestinazioni();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Destinazione = destinazione;
            filtro.FkOccbdeId = destinazioneBase;
            filtro.Software = software;
            filtro.Id = id;
            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Destinazione = "LIKE";

            List<CCDestinazioni> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false);

            ListSortManager<CCDestinazioni>.Sort(list, sortExpression);

            return list;
        }

        public List<OCCBaseDestinazioni> GetBaseDestinazioniList(string idcomune)
        {
            OCCBaseDestinazioni c = new OCCBaseDestinazioni();
            c.SelectColumns = "Distinct OCC_BASEDESTINAZIONI.ID, OCC_BASEDESTINAZIONI.DESTINAZIONE";
            c.OthersTables.Add("CC_DESTINAZIONI");
            c.OthersWhereClause.Add("OCC_BASEDESTINAZIONI.ID=CC_DESTINAZIONI.FK_OCCBDE_ID");
            c.OthersWhereClause.Add("CC_DESTINAZIONI.IDCOMUNE='" + idcomune + "'");
            c.OrderBy = "OCC_BASEDESTINAZIONI.Destinazione asc";

            return this.db.GetClassList(c);
        }

    }
}
