using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTipiSuperficieMgr
    {
        private void VerificaRecordCollegati(CCTipiSuperficie cls)
        {
            if (this.recordCount("CC_CONFIGURAZIONE", "TAB1_FK_TS_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and TAB1_FK_TS_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            if (this.recordCount("CC_CONFIGURAZIONE", "TAB2_FK_TS_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and TAB2_FK_TS_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            if (this.recordCount("CC_CONFIGURAZIONE", "ART9SU_FK_TS_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and ART9SU_FK_TS_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            if (this.recordCount("CC_CONFIGURAZIONE", "ART9SA_FK_TS_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and ART9SA_FK_TS_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            if (this.recordCount("CC_ICALCOLI_DETTAGLIOT", "FK_CCTS_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCTS_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLI_DETTAGLIOT");

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTipiSuperficie> Find(string token, int? id, string descrizione, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCTipiSuperficie filtro = new CCTipiSuperficie();
            CCTipiSuperficie filtroCompare = new CCTipiSuperficie();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.Id = id;

            filtroCompare.Descrizione = "LIKE";

            List<CCTipiSuperficie> list = authInfo.CreateDatabase().GetClassList(filtro, false);
            ListSortManager<CCTipiSuperficie>.Sort(list, sortExpression);

            return list;

        }

        private void EffettuaCancellazioneACascata(CCTipiSuperficie cls)
        {

            #region Tabella CC_DETTAGLISUPERFICIE
            CCDettagliSuperficie ccds = new CCDettagliSuperficie();
            CCDettagliSuperficieMgr mgr = new CCDettagliSuperficieMgr(this.db);

            ccds.Idcomune = cls.Idcomune;
            ccds.FkCcTsId = cls.Id;

            List<CCDettagliSuperficie> lCcds = mgr.GetList(ccds);

            foreach (CCDettagliSuperficie dett in lCcds)
            {
                mgr.Delete(dett);
            }
            #endregion
        }
    }
}
