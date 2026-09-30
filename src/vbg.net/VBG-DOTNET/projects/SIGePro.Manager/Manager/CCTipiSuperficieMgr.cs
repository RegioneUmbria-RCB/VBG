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
    public partial class CCTipiSuperficieMgr
    {
        private void VerificaRecordCollegati(CCTipiSuperficie cls)
        {
            var conditionsTab1 = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("TAB1_FK_TS_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_CONFIGURAZIONE", "TAB1_FK_TS_ID", conditionsTab1) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            var conditionsTab2 = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("TAB2_FK_TS_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_CONFIGURAZIONE", "TAB2_FK_TS_ID", conditionsTab2) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            var conditionsArt9su = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("ART9SU_FK_TS_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_CONFIGURAZIONE", "ART9SU_FK_TS_ID", conditionsArt9su) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            var conditionsArt9sa = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("ART9SA_FK_TS_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_CONFIGURAZIONE", "ART9SA_FK_TS_ID", conditionsArt9sa) > 0)
                throw new ReferentialIntegrityException("CC_CONFIGURAZIONE");

            var conditionsDettaglio = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCTS_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_ICALCOLI_DETTAGLIOT", "FK_CCTS_ID", conditionsDettaglio) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLI_DETTAGLIOT");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTipiSuperficie> Find(string token, int? id, string descrizione, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCTipiSuperficie filtro = new CCTipiSuperficie();
            CCTipiSuperficie filtroCompare = new CCTipiSuperficie();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.Id = id;

            filtroCompare.Descrizione = "LIKE";

            List<CCTipiSuperficie> list = authInfo.CreateDatabase().GetClassList(filtro, false).ToList<CCTipiSuperficie>();
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
