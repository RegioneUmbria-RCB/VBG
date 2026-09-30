
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CcCausaliRiduzioniTMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CcCausaliRiduzioniT> Find(string token, string software, string descrizione, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CcCausaliRiduzioniTMgr mgr = new CcCausaliRiduzioniTMgr(authInfo.CreateDatabase());

            CcCausaliRiduzioniT filtro = new CcCausaliRiduzioniT();
            CcCausaliRiduzioniT filtroCompare = new CcCausaliRiduzioniT();

            filtro.Software = software;
            filtro.Idcomune = authInfo.IdComune;

            if (!String.IsNullOrEmpty(descrizione))
                filtro.Descrizione = descrizione;

            filtroCompare.Descrizione = "like";

            List<CcCausaliRiduzioniT> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CcCausaliRiduzioniT>();

            ListSortManager<CcCausaliRiduzioniT>.Sort(list, sortExpression);

            return list;
        }

        public List<CcCausaliRiduzioniT> GetListByIdcomuneSoftware(string idComune, string software)
        {
            CcCausaliRiduzioniT filtro = new CcCausaliRiduzioniT();
            filtro.Idcomune = idComune;
            filtro.Software = software;
            filtro.OrderBy = "Descrizione asc";

            return this.GetList(filtro);
        }

        private void EffettuaCancellazioneACascata(CcCausaliRiduzioniT cls)
        {
            bool iniziataTransazione = false;

            if (this.db.Transaction == null)
            {
                iniziataTransazione = true;
                this.db.BeginTransaction();
            }

            try
            {
                CcCausaliRiduzioniRMgr rMgr = new CcCausaliRiduzioniRMgr(this.db);
                List<CcCausaliRiduzioniR> causali = rMgr.GetListByIdCausaleT(cls.Idcomune, cls.Id.Value);

                foreach (CcCausaliRiduzioniR r in causali)
                    rMgr.Delete(r);

                if (iniziataTransazione)
                    this.db.CommitTransaction();
            }
            catch (Exception ex)
            {
                if (iniziataTransazione)
                    this.db.RollbackTransaction();

                throw;
            }
        }
    }
}
